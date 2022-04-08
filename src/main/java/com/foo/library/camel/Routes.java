package com.foo.library.camel;

import com.foo.library.model.Book;
import com.foo.library.model.Books;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;
import org.springframework.stereotype.Component;


@Component
public class Routes extends RouteBuilder {

	JacksonDataFormat bookDataFormat = new JacksonDataFormat() {{
		setUnmarshalType(Book.class);
	}};

	JacksonDataFormat booksDataFormat = new JacksonDataFormat() {{
		setUnmarshalType(Book[].class);
	}};

	JacksonXMLDataFormat xmlBookDataFormat = new JacksonXMLDataFormat() {{
		setUnmarshalType(Book.class);
//		setUseList("books");
	}};

	@Override
	public void configure() throws Exception {
		restConfiguration()
			.contextPath("/camel")
			.port(8080)
			.component("servlet")
			;

		configureJsonApi();
		configureXmlApi();
		configureRabbitRoutes();
	}

	private void configureJsonApi() {
		rest("/json")
			.get("list")
			.consumes("application/json")
			.to("direct:rmq-list")
			.produces("application/json")
			;

		rest("/json")
			.id("json-add")
			.post("add")
			.consumes("application/json")
			.to("direct:rmq-add")
			.produces("application/json")
			;
	}

	private void configureRabbitRoutes() {
		from("direct:rmq-list")
			.id("direct-list")
			.log("request -> ${headers} -- ${body}")
			.to(ExchangePattern.InOut, "spring-rabbitmq:library?routingKey=list")
			.log("response -> ${headers} -- ${body}")
			;

		from("direct:rmq-list-typed")
			.to("direct:rmq-list")
			.unmarshal(booksDataFormat)
			.convertBodyTo(Books.class)
			;

		from("direct:rmq-add")
			.id("direct-add")
//			apparently this log statement randomly consumes the body where other log statements do not
			.log("request -> ${headers} -- ${body}")
			.to(ExchangePattern.InOut, "spring-rabbitmq:library?routingKey=add")
			.log("response -> ${headers} -- ${body}")
			;

		from("direct:rmq-add-typed")
			.to("direct:rmq-add")
			// otherwise ID passed as raw bytes for long parameter to get(long) (eg. "8" -> 56L)
			.convertBodyTo(String.class)
			.bean("bookService", "get")
			;

		from("direct:rmq-xml-add-typed")
			.id("rmq-xml-add-typed")
			.unmarshal(xmlBookDataFormat)
			.marshal(bookDataFormat)
			.to("direct:rmq-add-typed")
			;

		from("spring-rabbitmq:library?queues=rpc-list&routingKey=list&autoDeclare=true")
			.id("rmq-list")
			.log("input -> ${headers} -- ${body}")
			.bean("bookService", "list(${header.author}, ${header.genre})")
			.removeHeader("author")
			.removeHeader("genre")
			.marshal(booksDataFormat)
			.log("output -> ${headers} -- ${body}")
			;

		from("spring-rabbitmq:library?queues=rpc-add&routingKey=add&autoDeclare=true")
			.id("rmq-add")
			.log("input -> ${headers} -- ${body}")
			.unmarshal(bookDataFormat)
			.bean("bookService", "add")
			.marshal(bookDataFormat)
			.log("output -> ${headers} -- ${body}")
			;
	}

	private void configureXmlApi() {
		rest("/xml")
			.id("xml-list")
			.get("list")
			.to("direct:rmq-list-typed")
			.produces("application/xml")
			;

		rest("/xml")
			.id("xml-add")
			.post("add")
			.consumes("application/xml")
			.to("direct:rmq-xml-add-typed")
			.produces("application/xml")
			;

	}
}

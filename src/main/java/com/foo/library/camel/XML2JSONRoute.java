package com.foo.library.camel;

import com.foo.library.model.Books;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class XML2JSONRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration()
			.contextPath("/camel")
			.port(8080)
			.component("servlet")
			;

		rest("/xml")
			.get("list")
			.to("direct:camel-xml-list")
			.produces("application/xml")
			;

		rest("/xml")
			.post("add").consumes("application/xml")
			.to("direct:camel-xml-add")
			.produces("application/xml");

		from("direct:camel-xml-add")
			.bean("helloController", "add")
			.bean("helloController", "get")
			;

		from("direct:camel-xml-list")
			.bean("helloController", "list(${header.author}, ${header.genre})")
			.convertBodyTo(Books.class)
			;
	}
}

package com.foo.library.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class XML2JSONRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("timer:hello?period={{timer.period}}").routeId("hello")
			.transform().method("myBean", "saySomething")
			.filter(simple("${body} contains 'foo'"))
			.to("log:foo")
			.end()
			.to("stream:out");

//		from("direct:marshalBookxml2json")
//			.to("log:?level=INFO&showBody=true")
//			.marshal().xmljson()
//			.to("log:?level=INFO&showBody=true");
//
//
//		final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
//		xmlJsonFormat.setRootName("Book");
//
//		from("direct:unMarshalBookjson2xml")
//			//.unmarshal().xmljson()
//			.unmarshal(xmlJsonFormat)
//			.to("log:?level=INFO&showBody=true");

	}
}

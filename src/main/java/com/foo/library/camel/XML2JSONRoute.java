package com.foo.library.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.springframework.stereotype.Component;


@Component
public class XML2JSONRoute extends RouteBuilder {

	public void configure() throws Exception {

		from("direct:marshalBookxml2json")
			.to("log:?level=INFO&showBody=true")
			.marshal().xmljson()
			.to("log:?level=INFO&showBody=true");


		final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
		xmlJsonFormat.setRootName("Book");

		from("direct:unMarshalBookjson2xml")
			//.unmarshal().xmljson()
			.unmarshal(xmlJsonFormat)
			.to("log:?level=INFO&showBody=true");

	}
}

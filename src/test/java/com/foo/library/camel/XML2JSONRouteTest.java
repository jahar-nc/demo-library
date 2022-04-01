package com.foo.library.camel;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@CamelSpringBootTest
public class XML2JSONRouteTest {

	@Autowired
	ProducerTemplate producerTemplate;

//	@Test
//	public void marshalEmployeeJSON2XML() {
//
//		String expected = "{\"id\":\"123 \",\"name\":\"ABC\",\"type\":\"senior\"}";
//		String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
//			"<Employee><id>123 </id><name>ABC</name><type>senior</type></Employee>\r\n";
//
//		String response = producerTemplate.requestBody("direct:marshalBookxml2json", request, String.class);
//		System.out.println("response is : " + response);
//
//		Assertions.assertEquals(expected, response);
//
//	}
//
//	@Test
//	public void unMarshalEmployeeJSON2XML() {
//
//		final String request = "{\"name\":\"ABC\",\"id\":\"123 \",\"type\":\"senior\"}";
//
//		final String response = producerTemplate.requestBody("direct:unMarshalBookjson2xml", request, String.class);
//		System.out.println("response is : " + response);
//		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
//			"<Book><id>123 </id><name>ABC</name><type>senior</type></Employee>\r\n";
//		Assertions.assertEquals(expected, response);
//
//	}

	@Test
	public void testFoo() throws Exception {
		String body = "<book><id>1</id><genre>genre</genre><author>author</author><title>title</title></book>";
		String result = producerTemplate.requestBody("direct:blargh", body, String.class);
		assertThat(result).isEqualTo("");
	}
}

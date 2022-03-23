package com.foo.library.camel;

//import org.apache.camel.ProducerTemplate;
//import org.apache.camel.RoutesBuilder;
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.test.junit4.CamelTestSupport;
//import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
////import org.apache.camel.test.spring.CamelSpringTestSupport;
////import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.AbstractApplicationContext;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
//@CamelSpringBootTest
public class XML2JSONRouteTest {
//
//	@Autowired
//	ProducerTemplate producerTemplate;
//
//	@Configuration
//	static class TestConfig {
//
//		@Bean
//		RoutesBuilder route() {
////			return new RouteBuilder() {
////				@Override
////				public void configure() throws Exception {
////					from("direct:test").to("mock:test");
////				}
////			};
//			return new XML2JSONRoute();
//		}
//	}
//
//	@Test
//	public void marshalEmployeeJSON2XML() {
//
//		String expected = "{\"id\":\"123 \",\"name\":\"ABC\",\"type\":\"senior\"}";
//		String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
//			"<Employee><id>123 </id><name>ABC</name><type>senior</type></Employee>\r\n";
//
//		String response = template.requestBody("direct:marshalBookxml2json", request, String.class);
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
//		final String response = template.requestBody("direct:unMarshalBookjson2xml", request, String.class);
//		System.out.println("response is : " + response);
//		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
//			"<Book><id>123 </id><name>ABC</name><type>senior</type></Employee>\r\n";
//		Assertions.assertEquals(expected, response);
//
//	}
}

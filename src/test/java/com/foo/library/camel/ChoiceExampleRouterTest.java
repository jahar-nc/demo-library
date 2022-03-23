package com.foo.library.camel;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
public class ChoiceExampleRouterTest {

	@Autowired
	private ProducerTemplate template;
	@EndpointInject("mock:a")
	protected MockEndpoint endpointA;
	@EndpointInject("mock:b")
	protected MockEndpoint endpointB;
	@EndpointInject("mock:c")
	protected MockEndpoint endpointC;
	@Test
	void whenSendBodyWithHeaderBar_routeAIsChosen() throws Exception {

		String expectedBody = "Route A";
		endpointA.expectedBodiesReceived(expectedBody);
		template.sendBodyAndHeader("direct:choice", null, "foo", "bar");
		endpointA.assertIsSatisfied();
	}

	@Test
	void whenSendBodyWithHeaderChesse_routeBIsChosen() throws Exception {

		String expectedBody = "Route B";
		endpointB.expectedBodiesReceived(expectedBody);
		template.sendBodyAndHeader("direct:choice", null, "foo", "cheese");
		endpointB.assertIsSatisfied();
	}

	@Test
	void whenSendBodyWithUnknownHeader_routeCIsChosen() throws Exception {

		String expectedBody = "Route C";
		endpointC.expectedBodiesReceived(expectedBody);
		template.sendBodyAndHeader("direct:choice", null, "foo", "unknown");
		endpointC.assertIsSatisfied();
	}
}

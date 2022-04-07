package com.foo.library.rmq;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfig {
	@Bean
	public static ConnectionFactory createRabbitConnectionFactory(
		@Value("${rmq.host:localhost}") String host,
		@Value("${rmq.port:5672}") int port,
		@Value("${rmq.username:guest}") String username,
		@Value("${rmq.password:guest}") String password
	) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		return factory;
	}
}

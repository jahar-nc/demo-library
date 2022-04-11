package com.foo.library.rmq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;


@Configuration
public class ConnectionConfig {
	@Bean
	public static ConnectionFactory rabbitConnectionFactory(
		@Value("${rmq.dsn:amqp://}") String dsn
	) throws Exception {
		return new CachingConnectionFactory(new URI(dsn));
	}
}

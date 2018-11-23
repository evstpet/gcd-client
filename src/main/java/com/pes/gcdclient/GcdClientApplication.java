package com.pes.gcdclient;

import com.pes.gcdclient.domain.event.dto.CalculationResult;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableRabbit
public class GcdClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(GcdClientApplication.class, args);
	}

	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
		jsonConverter.setClassMapper(classMapper());
		return jsonConverter;
	}

	@Bean
	public DefaultClassMapper classMapper() {
		DefaultClassMapper classMapper = new DefaultClassMapper();
		Map<String, Class<?>> idClassMapping = new HashMap<>();
		idClassMapping.put("calculationResult", CalculationResult.class);
		classMapper.setIdClassMapping(idClassMapping);
		classMapper.setTrustedPackages("com.pes.*");
		return classMapper;
	}

	@Bean
	public TopicExchange calculatorExchange() {
		return new TopicExchange("calculator.topic");
	}
}

package com.pes.gcdclient;

import com.pes.gcdclient.application.event.dto.CalculationRequestEvent;
import com.pes.gcdclient.application.event.dto.CalculationResultEvent;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.client.RestTemplate;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.springframework.http.MediaType.TEXT_PLAIN;

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
        idClassMapping.put("calculationResult", CalculationResultEvent.class);
        idClassMapping.put("calculationRequest", CalculationRequestEvent.class);
        classMapper.setIdClassMapping(idClassMapping);
        classMapper.setTrustedPackages("*");
        return classMapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(longHttpMessageConverter());
        return restTemplate;
    }

    @Bean
    public HttpMessageConverter<Long> longHttpMessageConverter() {
        return new HttpMessageConverter<Long>() {
            @Override
            public boolean canRead(Class<?> clazz, MediaType mediaType) {
                return clazz == Long.class;

            }

            @Override
            public boolean canWrite(Class<?> clazz, MediaType mediaType) {
                return clazz == Long.class;

            }

            @Override
            public List<MediaType> getSupportedMediaTypes() {
                return singletonList(TEXT_PLAIN);
            }

            @Override
            public Long read(Class<? extends Long> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                Long result;

                try (DataInputStream dis = new DataInputStream(inputMessage.getBody())) {
                    result = dis.readLong();
                }

                return result;
            }

            @Override
            public void write(Long aLong, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                try (PrintStream ps = new PrintStream(outputMessage.getBody())) {
                    ps.print(aLong);
                }
            }
        };
    }
}

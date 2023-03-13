package com.example.demo;

import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	  @Bean
	  JacksonJsonpMapper jacksonJsonpMapper() {
	    // this is so we don't fail on unknown properties and can write null fields.
	    return new JacksonJsonpMapper(new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, false).setSerializationInclusion(JsonInclude.Include.ALWAYS).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules());
	  }

	
	
	@Bean
	public RestClientBuilderCustomizer restClientBuilderCustomizer() {
	  
	  
	  return new RestClientBuilderCustomizer() {
        
        @Override
        public void customize(HttpAsyncClientBuilder builder) {
          
          builder.setDefaultHeaders(
              List.of(
                  new BasicHeader(
                      HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())));
          builder.addInterceptorLast(
              (HttpResponseInterceptor)
              (response, context) ->
                  response.addHeader("X-Elastic-Product", "Elasticsearch"));
          
        }

        @Override
        public void customize(RestClientBuilder builder) {
          // TODO Auto-generated method stub
          
        }

	};
	
	}

}

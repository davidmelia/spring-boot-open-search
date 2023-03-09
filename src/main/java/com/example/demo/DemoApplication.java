package com.example.demo;

import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
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
	public RestClientBuilderCustomizer restClientBuilderCustomizer() {
	  
	  
	  return new RestClientBuilderCustomizer() {
        
        @Override
        public void customize(HttpAsyncClientBuilder builder) {
          
//          builder.setDefaultHeaders(
//              List.of(
//                  new BasicHeader(
//                      HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())));
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

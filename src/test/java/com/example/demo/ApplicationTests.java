package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchClient;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ApplicationTests {

  @Autowired
  ReactiveElasticsearchClient reactiveElasticsearchClient;
  
	@Test
	void contextLoads() {
	  
	  System.out.println(reactiveElasticsearchClient.info().block());
	  
	}

}

package com.example.demo;

import static java.util.Arrays.asList;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.example.demo.es.model.Article;
import com.example.demo.es.model.Author;
import com.example.demo.es.repository.ArticleRepository;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate;
/**
 * This Manual test requires: Elasticsearch instance running on localhost:9200.
 * 
 * The following docker command can be used: docker run -d --name es762 -p
 * 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2
 */
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ArticleRepositoryIntegrationTest {

  @Autowired
  private ReactiveElasticsearchClient reactiveElasticsearchClient;
  
  @Autowired
  private ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;
  
  @Autowired
  private ArticleRepository articleRepository;

  private final Author johnSmith = new Author("John Smith");
  private final Author johnDoe = new Author("John Doe");

  @BeforeEach
  public void before() {
    articleRepository.deleteAll().block();
      Article article = new Article("Spring Data Elasticsearch");
      article.setId("1");
      article.setAuthors(asList(johnSmith, johnDoe));
      article.setTags("elasticsearch", "spring data");
      articleRepository.save(article).block();

      article = new Article("Search engines");
      article.setId("2");
      article.setAuthors(asList(johnDoe));
      article.setTags("search engines", "tutorial");
      articleRepository.save(article).block();

      article = new Article("Second Article About Elasticsearch");
      article.setId("3");
      article.setAuthors(asList(johnSmith));
      article.setTags("elasticsearch", "spring data");
      articleRepository.save(article).block();

      article = new Article("Elasticsearch Tutorial");
      article.setId("4");
      article.setAuthors(asList(johnDoe));
      article.setTags("elasticsearch");
      articleRepository.save(article).block();
      
      
      article = new Article("Dave Elasticsearch Tutorial");
      article.setId("5");
      article.setAuthors(asList(johnDoe));
      article.setTags("elasticsearch");
      reactiveElasticsearchTemplate.save(article).block();
      
      System.out.println("*****");
      article = new Article("Dave2 Elasticsearch Tutorial");
      article.setId("5");
      article.setAuthors(asList(johnDoe));
      article.setTags((String[])null);
      
      reactiveElasticsearchTemplate.save(article).block();
  }

  @AfterEach
  public void after() {
      //articleRepository.deleteAll().block();
  }
  
  @Test
  public void test() throws InterruptedException {
    Thread.sleep(2000);


    var searchRequest = new SearchRequest.Builder().query(q -> q.bool(b -> b.must(m -> m.terms(f -> f.field("authors.name").terms(t -> t.value(List.of(FieldValue.of("john"))))))))
        .source(s -> s.filter(f -> f.includes("title", "authors.name"))).index("blog").size(2);


    System.out.println(reactiveElasticsearchClient.search(searchRequest.build(), Map.class).block());

  }
}

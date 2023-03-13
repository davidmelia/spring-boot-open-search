package com.example.demo.es.model;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

import org.springframework.data.elasticsearch.annotations.Field;

public class Author {

    @Field(type = Text)
    private String name;
    
    @Field(type = Text)
    private String temp = null;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public String getTemp() {
      return temp;
    }

    public void setTemp(String temp) {
      this.temp = temp;
    }

    @Override
    public String toString() {
        return "Author{" + "name='" + name + '\'' + '}';
    }
}

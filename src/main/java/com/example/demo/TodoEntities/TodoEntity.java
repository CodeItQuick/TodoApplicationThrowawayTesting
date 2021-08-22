package com.example.demo.TodoEntities;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;

@Entity
public class TodoEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String description;

    public TodoEntity() {

    }
    
    public TodoEntity(String message) {

        this.description = message;
    }

    public TodoEntity(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
}

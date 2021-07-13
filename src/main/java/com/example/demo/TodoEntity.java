package com.example.demo;

import javax.persistence.*;
@Entity
public class TodoEntity {


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String description;

    protected TodoEntity() {
    }

    public TodoEntity(String description)
    {
        this.description = description;
    }
    public Long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
}

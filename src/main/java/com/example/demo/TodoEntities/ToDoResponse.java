package com.example.demo.TodoEntities;

public class ToDoResponse {
    private String item;

    private Long id;

    public ToDoResponse(Long id, String item) {
        this.item = item;
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

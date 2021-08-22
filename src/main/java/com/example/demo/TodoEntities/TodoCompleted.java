package com.example.demo.TodoEntities;

import com.example.demo.TodoEntities.TodoEntity;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class TodoCompleted extends ApplicationEvent {
    private String message;
    private String fieldName;
    private String fieldValue;
    private Long id;

    public TodoCompleted(TodoEntity source, String message, String fieldName, String fieldValue) {
        super(source);
        this.id = source.getId();
        this.message = message;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public TodoCompleted(Object source) {
        super(source);
    }

    public TodoCompleted(Object source, Clock clock) {
        super(source, clock);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }
}

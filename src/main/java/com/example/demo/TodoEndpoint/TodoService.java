package com.example.demo.TodoEndpoint;

import com.example.demo.TodoEntities.TodoCompleted;
import com.example.demo.TodoEntities.TodoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;

@Service
public class TodoService {
    private TodoRepository todoRepository;
    private FeatureManager featureManager;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public TodoService(TodoRepository todoRepository, FeatureManager featureManager, ApplicationEventPublisher applicationEventPublisher) {

        this.todoRepository = todoRepository;
        this.featureManager = featureManager;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public TodoEntity saveTodo(String message) {
        TodoEntity todoEntity = new TodoEntity(message);

        TodoEntity savedTodoEntity = todoRepository.save(todoEntity);
        sendTodoCompleteEvent(savedTodoEntity, "post complete: saved todo");
        return savedTodoEntity;
    }

    public TodoEntity sendTodoCompleteEvent(TodoEntity todoEntity, String eventMessage) {
        System.out.println("sending todo completed event");
        applicationEventPublisher.publishEvent(
                new TodoCompleted(
                        todoEntity,
                        eventMessage,
                        "description",
                        todoEntity.getDescription()
                )
        );
        return todoEntity;
    }

}

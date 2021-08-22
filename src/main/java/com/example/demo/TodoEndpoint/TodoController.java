package com.example.demo.TodoEndpoint;

import com.example.demo.FeatureFlags.CurrentEnabledFeaturesResponse;
import com.example.demo.FeatureFlags.MyTogglzConfiguration;
import com.example.demo.TodoEntities.ToDoPutRequest;
import com.example.demo.TodoEntities.ToDoRequest;
import com.example.demo.TodoEntities.ToDoResponse;
import com.example.demo.TodoEntities.TodoEntity;
import com.example.demo.TodoFeatures;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.togglz.core.manager.FeatureManager;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoRepository todoRepository;
    private final FeatureManager featureManager;
    private ObjectMapper objectMapper;
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoRepository todoRepository, FeatureManager featureManager, ObjectMapper objectMapper, TodoService todoService) {
        this.todoRepository = todoRepository;
        this.featureManager = featureManager;
        this.objectMapper = objectMapper;
        this.todoService = todoService;
    }

    @CrossOrigin
    @PostMapping(value = "/create")
    public ResponseEntity<?> createEndpoint(@RequestBody ToDoRequest message) throws IOException {

        if (!featureManager.isActive(TodoFeatures.CREATE_ENABLED)) {
            return new ResponseEntity(HttpStatus.OK);
        }

        TodoEntity savedTodoEntity = todoService.saveTodo(message.getDescription());
        return ResponseEntity.ok(savedTodoEntity);
    }

    @CrossOrigin
    @GetMapping(value = "/read")
    public ResponseEntity<?> readEndpoint() {

        List<TodoEntity> savedTodoEntities = todoRepository.findAll();

        List<ToDoResponse> toDoResponses = savedTodoEntities.stream()
                .map(todo -> new ToDoResponse(todo.getId(), todo.getDescription()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(toDoResponses);

    }

    @CrossOrigin
    @PutMapping("/edit")
    public ResponseEntity<?> editEndpoint(@RequestBody ToDoPutRequest putRequest, @RequestParam String user) {
        // Mimic Authentication by creating a user by the query param
        MyTogglzConfiguration.CurrentFeatureUser.Singleton(user);
        if (featureManager.isActive(TodoFeatures.UPDATE_BY_USER)) {
            TodoEntity todoById = new TodoEntity(putRequest.getId(), putRequest.getDescription());
            TodoEntity save = todoRepository.save(todoById);
            return ResponseEntity.ok(save);
        };
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteEndpoint(@RequestBody ToDoPutRequest putRequest, @RequestParam String user) {
        MyTogglzConfiguration.CurrentFeatureUser.Singleton(user);
        if (featureManager.isActive(TodoFeatures.USER_BASED_PERCENTAGE_ROLLOUT_DELETE_FUNCTION)) {
            TodoEntity todoById = new TodoEntity(putRequest.getId(), putRequest.getDescription());
            todoRepository.delete(todoById);
            return ResponseEntity.ok("feature is on");
        } else {
            return ResponseEntity.badRequest().body("feature is off");
        }
    }

    @CrossOrigin
    @GetMapping("/features/states")
    public ResponseEntity<?> currentFeatures(@RequestParam String user) {

        CurrentEnabledFeaturesResponse currentEnabledFeaturesResponse = new CurrentEnabledFeaturesResponse(featureManager, user);
        return ResponseEntity.ok(currentEnabledFeaturesResponse);
    }
}

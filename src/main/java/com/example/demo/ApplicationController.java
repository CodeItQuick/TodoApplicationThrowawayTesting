package com.example.demo;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.togglz.core.manager.FeatureManager;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    private FeatureManager featureManager;

    @Autowired
    private ObjectMapper objectMapper;

    @CrossOrigin
    @PostMapping(value = "/create")
    public ResponseEntity<String> editEndpoint(@RequestBody String message) throws IOException {

        HttpHeaders allowJson = new HttpHeaders();
        allowJson.add("Content-Type", "application/json");

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser todoParse = factory.createParser(message);
        JsonNode todoObj = todoParse.readValueAsTree();

        TodoEntity todoEntity = new TodoEntity(
                todoObj.get("description").toString()
        );
        TodoEntity savedTodoEntity = todoRepository.save(todoEntity);
        return new ResponseEntity<>(String.valueOf(savedTodoEntity.toString()), allowJson, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/read")
    public ResponseEntity<?> readEndpoint() {

        if (!featureManager.isActive(TodoFeatures.READ_ENABLED)) return new ResponseEntity(HttpStatus.OK);

        List<TodoEntity> savedTodoEntity = todoRepository.findAll();


        HttpHeaders allowJson = new HttpHeaders();
        allowJson.add("Content-Type", "application/json");
        JSONArray jsonArray = new JSONArray();
        ObjectMapper mapper = new ObjectMapper();
        savedTodoEntity.stream().forEach((property) -> {
            try {
                JSONObject json = new JSONObject();
                json.put("item", property.getDescription());
                jsonArray.put(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return new ResponseEntity<String>(jsonArray.toString(), allowJson, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = "/delete")
    public String deleteEndpoint() {
        return "All good. You DO NOT need to be authenticated to call /api/public.";
    }
}

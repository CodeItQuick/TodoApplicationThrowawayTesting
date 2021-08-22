package com.example.demo.TodoEndpoint;

import com.example.demo.TodoEntities.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}

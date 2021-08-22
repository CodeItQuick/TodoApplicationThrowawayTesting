package com.example.demo.Logger;

import com.example.demo.TodoEntities.TodoCompleted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class LoggerAggregate implements ApplicationListener<TodoCompleted> {
    private LoggerRepository loggerRepository;

    @Autowired
    public LoggerAggregate(LoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    @Override
    public void onApplicationEvent(TodoCompleted todoCompleted) {
        System.out.println("Received spring custom event - " + todoCompleted.getId());
        LoggerEntity loggerEntity = new LoggerEntity(
                todoCompleted.getMessage(),
                todoCompleted.getId(),
                todoCompleted.getFieldName(),
                todoCompleted.getFieldValue());
        loggerRepository.save(loggerEntity);
    }
}

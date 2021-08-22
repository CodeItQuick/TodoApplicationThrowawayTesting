package com.example.demo.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LoggerEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String message;
    private Long domainEventId;
    private String fieldName;
    private String fieldValue;

    public LoggerEntity(String message, Long domainEventId, String fieldName, String fieldValue) {
        this.message = message;
        this.domainEventId = domainEventId;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Long getDomainEventId() {
        return domainEventId;
    }

    public void setDomainEventId(Long domainEventId) {
        this.domainEventId = domainEventId;
    }

}

package com.example.transaction_service.model.log.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "datasource_error_log")
public class DatasourceErrorLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "stack_trace")
    private String stackTrace;
    @Column(name = "message")
    private String message;
    @Column(name = "time")
    private Timestamp time;

    public DatasourceErrorLog(String name, String stackTrace, String message, Timestamp time) {
        this.name = name;
        this.stackTrace = stackTrace;
        this.message = message;
        this.time = time;
    }

    public DatasourceErrorLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DatasourceErrorLog that = (DatasourceErrorLog) o;
        return id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DatasourceErrorLog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stackTrace='" + stackTrace + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}

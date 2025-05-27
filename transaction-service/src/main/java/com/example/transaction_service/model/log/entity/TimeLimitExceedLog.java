package com.example.transaction_service.model.log.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "time_limit_exceed_log")
public class TimeLimitExceedLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "class")
    private String className;
    @Column(name = "method")
    private String methodName;
    @Column(name = "runtime")
    private Long runtime;
    @Column(name = "time")
    private Timestamp time;

    public TimeLimitExceedLog(String className, String methodName, Long runtime, Timestamp time) {
        this.className = className;
        this.methodName = methodName;
        this.runtime = runtime;
        this.time = time;
    }

    public TimeLimitExceedLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
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
        TimeLimitExceedLog that = (TimeLimitExceedLog) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TimeLimitExceedLog{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", runtime=" + runtime +
                ", time=" + time +
                '}';
    }
}

package com.example.transaction_service.service.common.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, отлавливающая исключения помеченных ею методов и сохранающая в базу данных лог об возникшей ошибке
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogDatasourceError {
}

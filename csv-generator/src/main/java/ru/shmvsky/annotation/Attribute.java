package ru.shmvsky.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {
    String name() default "";
    boolean ignore() default false;
}
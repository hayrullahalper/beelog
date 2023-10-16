package com.beehive.annotations;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Injectable {
    public enum Permission {
        PUBLIC,
        PRIVATE
    }

    Permission value() default Permission.PUBLIC;
}

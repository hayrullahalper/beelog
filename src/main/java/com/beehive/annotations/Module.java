package com.beehive.annotations;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Module {
    Class<?>[] modules() default {};
    Class<?>[] services() default {};
    Class<?>[] controllers() default {};
}

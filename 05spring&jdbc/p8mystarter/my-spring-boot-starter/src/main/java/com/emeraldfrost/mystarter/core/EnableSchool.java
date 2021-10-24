package com.emeraldfrost.mystarter.core;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SchoolAutoConfiguration.class})
public @interface EnableSchool {
}

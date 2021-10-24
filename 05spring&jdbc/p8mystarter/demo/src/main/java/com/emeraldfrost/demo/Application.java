package com.emeraldfrost.demo;

import com.emeraldfrost.mystarter.core.EnableSchool;
import com.emeraldfrost.mystarter.model.School;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableSchool
public class Application {

    public static void main(String[] args) {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        final School school = applicationContext.getBean(School.class);
        school.ding();
    }
}

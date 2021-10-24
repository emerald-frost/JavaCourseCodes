package com.emeraldfrost.mystarter.core;

import com.emeraldfrost.mystarter.model.Klass;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.school")
public class SchoolProperties {

    private String schoolName;

    private Klass klass1;

    private Klass klass2;
}

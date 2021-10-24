package com.emeraldfrost.mystarter.core;

import com.emeraldfrost.mystarter.model.School;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(School.class)
@EnableConfigurationProperties(SchoolProperties.class)
public class SchoolAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(School.class)
    @ConditionalOnProperty(prefix = "spring.school", value = "enabled", havingValue = "true")
    School getSchool(SchoolProperties schoolProperties) {
        return new School(schoolProperties);
    }
}

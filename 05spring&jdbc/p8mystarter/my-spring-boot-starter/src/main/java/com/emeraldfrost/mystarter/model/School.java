package com.emeraldfrost.mystarter.model;

import com.emeraldfrost.mystarter.core.SchoolProperties;
import lombok.Data;

@Data
public class School {

    private String schoolName;

    private Klass klass1;

    private Klass klass2;

    public School(SchoolProperties properties) {
        this.schoolName = properties.getSchoolName();
        this.klass1 = properties.getKlass1();
        this.klass2 = properties.getKlass2();
    }

    public void ding() {
        System.out.println("school name: " + schoolName + ", klass1: " + klass1 + ", klass2: " + klass2);
    }
}

package com.emeraldfrost.mystarter.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Klass {

    private List<Student> students = new ArrayList<>();
}

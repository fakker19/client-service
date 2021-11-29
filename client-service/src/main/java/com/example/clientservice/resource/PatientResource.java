package com.example.clientservice.resource;

import lombok.Data;

@Data
public class PatientResource {
    private Long id;
    private String name;
    private Integer age;
    private String breed;
}

package com.example.springtest.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "department")
public class Department {
    @Id @GeneratedValue
    private int id;

    private String name;

    @ManyToOne
    private Faculty faculty;

    @OneToOne
    private Instructor headInstructor;
}

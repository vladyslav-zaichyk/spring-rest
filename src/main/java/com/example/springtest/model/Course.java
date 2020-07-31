package com.example.springtest.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id @GeneratedValue
    private int id;

    private String name;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Instructor instructor;
}

package com.example.springtest.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student_group")
public class StudentGroup {
    @Id @GeneratedValue
    private int id;

    private String name;

    @ManyToOne
    private Department department;

    private int semester;

    @ManyToOne
    private StudyPlan studyPlan;
}

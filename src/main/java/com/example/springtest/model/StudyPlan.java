package com.example.springtest.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "study_plan")
public class StudyPlan {
    @Id @GeneratedValue
    private int id;

    private int semester;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_study_plan",
    joinColumns = {@JoinColumn(name = "course_id")},
    inverseJoinColumns = {@JoinColumn(name = "study_plan_id")})
    private Set<Course> courses;
}

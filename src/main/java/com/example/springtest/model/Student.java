package com.example.springtest.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id @GeneratedValue()
    private int id;

    @OneToOne
    private Person person;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private StudentGroup group;
}

package com.example.springtest.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "instructor")
public class Instructor {
    @Id @GeneratedValue
    private int id;

    @OneToOne
    private Person person;

    private String academicRank;
}

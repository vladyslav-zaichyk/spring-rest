package com.example.springtest.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "subject")
public class Subject {
    @Id @GeneratedValue
    private int id;

    private String name;

    private int hours;
}

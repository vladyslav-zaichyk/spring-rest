package com.example.springtest.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "faculty")
public class Faculty {
    @Id @GeneratedValue
    private int id;

    private String officeAddress;
}

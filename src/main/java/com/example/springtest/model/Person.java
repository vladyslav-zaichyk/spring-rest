package com.example.springtest.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "person")
public class Person {
    @Id @GeneratedValue
    private int id;

    private String firstName;

    private String lastName;

    private String middleName;

    private char sex;

    private Date birthDate;

    private String phone;
}

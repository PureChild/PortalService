package com.example.project_practice;

import lombok.Data;
import lombok.Generated;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "userinfo")
public class User {
    @Id
    @Generated()
    private Integer id;
    private String name;
    private String password;
}

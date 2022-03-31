package com.challenge.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Person {

    @Id
    @Column(nullable = false, unique = true)
    private String name;


    @Min(0)
    @Column(nullable = false)
    private double salary;

    public Person(){
    }

    public Person(String n, double s){
        name = n;
        salary = s;
    }

    public String toString(){
        return(name + ":" + salary);
    }
    public String getname(){
        return name;
    }
    public double getsalary(){
        return salary;
    }
}



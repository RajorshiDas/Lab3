package com.example.lab3.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    @ToString.Exclude
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "department")
    @ToString.Exclude
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "department")
    @ToString.Exclude
    private Set<Course> courses = new HashSet<>();
}

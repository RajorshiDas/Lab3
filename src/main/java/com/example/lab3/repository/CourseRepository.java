package com.example.lab3.repository;

import com.example.lab3.domain.Course;
import com.example.lab3.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacher(Teacher teacher);
}


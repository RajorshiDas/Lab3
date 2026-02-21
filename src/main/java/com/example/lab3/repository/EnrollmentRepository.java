package com.example.lab3.repository;

import com.example.lab3.domain.Course;
import com.example.lab3.domain.Enrollment;
import com.example.lab3.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(Student student);
    boolean existsByStudentAndCourse(Student student, Course course);
}


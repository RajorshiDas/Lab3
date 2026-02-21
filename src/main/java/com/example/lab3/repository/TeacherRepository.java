package com.example.lab3.repository;

import com.example.lab3.domain.Teacher;
import com.example.lab3.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUser(User user);
}


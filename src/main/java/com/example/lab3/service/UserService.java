package com.example.lab3.service;

import com.example.lab3.domain.Role;
import com.example.lab3.domain.Student;
import com.example.lab3.domain.Teacher;
import com.example.lab3.domain.User;
import com.example.lab3.repository.StudentRepository;
import com.example.lab3.repository.TeacherRepository;
import com.example.lab3.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, StudentRepository studentRepository,
                       TeacherRepository teacherRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

    public User registerUser(String name, String email, String password, Role role) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists: " + email);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user = userRepository.save(user);

        // Create Student or Teacher based on role
        if (role == Role.STUDENT) {
            Student student = new Student();
            student.setName(name);
            student.setUser(user);
            studentRepository.save(student);
        } else if (role == Role.TEACHER) {
            Teacher teacher = new Teacher();
            teacher.setName(name);
            teacher.setUser(user);
            teacherRepository.save(teacher);
        }

        return user;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public Student findStudentByUser(User user) {
        return studentRepository.findByUser(user).orElse(null);
    }

    public Teacher findTeacherByUser(User user) {
        return teacherRepository.findByUser(user).orElse(null);
    }
}


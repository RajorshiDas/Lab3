package com.example.lab3.controller;

import com.example.lab3.domain.Course;
import com.example.lab3.domain.Enrollment;
import com.example.lab3.domain.Student;
import com.example.lab3.domain.User;
import com.example.lab3.repository.CourseRepository;
import com.example.lab3.repository.EnrollmentRepository;
import com.example.lab3.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final UserService userService;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentController(UserService userService, CourseRepository courseRepository,
                             EnrollmentRepository enrollmentRepository) {
        this.userService = userService;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        Student student = userService.findStudentByUser(user);

        model.addAttribute("name", user.getName());

        // Get all available courses
        List<Course> allCourses = courseRepository.findAll();
        model.addAttribute("courses", allCourses);

        // Get enrolled courses
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);
        model.addAttribute("enrollments", enrollments);

        return "student/dashboard";
    }

    @PostMapping("/enroll")
    public String enroll(@RequestParam Long courseId, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        Student student = userService.findStudentByUser(user);

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null && !enrollmentRepository.existsByStudentAndCourse(student, course)) {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollmentRepository.save(enrollment);
        }

        return "redirect:/student/dashboard";
    }
}


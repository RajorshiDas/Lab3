package com.example.lab3.controller;

import com.example.lab3.domain.Course;
import com.example.lab3.domain.Teacher;
import com.example.lab3.domain.User;
import com.example.lab3.repository.CourseRepository;
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
@RequestMapping("/teacher")
public class TeacherController {

    private final UserService userService;
    private final CourseRepository courseRepository;

    public TeacherController(UserService userService, CourseRepository courseRepository) {
        this.userService = userService;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        Teacher teacher = userService.findTeacherByUser(user);

        model.addAttribute("name", user.getName());

        // Get courses created by this teacher
        List<Course> myCourses = courseRepository.findByTeacher(teacher);
        model.addAttribute("courses", myCourses);

        return "teacher/dashboard";
    }

    @PostMapping("/create-course")
    public String createCourse(@RequestParam String title, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        Teacher teacher = userService.findTeacherByUser(user);

        Course course = new Course();
        course.setTitle(title);
        course.setTeacher(teacher);
        courseRepository.save(course);

        return "redirect:/teacher/dashboard";
    }
}


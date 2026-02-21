package com.example.lab3.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;
    private Course course;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("John Doe");

        course = new Course();
        course.setId(1L);
        course.setTitle("Math 101");
    }

    @Test
    void whenStudentEnrollsInCourse_thenCourseIsAddedToStudentsCourseList() {
        // Given: a student with no courses
        assertTrue(student.getCourses().isEmpty());

        // When: student enrolls in a course
        student.getCourses().add(course);

        // Then: course is in student's course list
        assertTrue(student.getCourses().contains(course));
        assertEquals(1, student.getCourses().size());
    }

    @Test
    void whenStudentEnrollsInMultipleCourses_thenAllCoursesAreInList() {
        // Given: another course
        Course course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Physics 101");

        // When: student enrolls in both courses
        student.getCourses().add(course);
        student.getCourses().add(course2);

        // Then: both courses are in student's course list
        assertEquals(2, student.getCourses().size());
        assertTrue(student.getCourses().contains(course));
        assertTrue(student.getCourses().contains(course2));
    }

    @Test
    void whenStudentEnrollsInSameCourseTwice_thenCourseIsOnlyAddedOnce() {
        // When: student tries to enroll in same course twice
        student.getCourses().add(course);
        student.getCourses().add(course);

        // Then: course is only in list once (Set behavior)
        assertEquals(1, student.getCourses().size());
    }

    @Test
    void whenStudentUnenrollsFromCourse_thenCourseIsRemovedFromList() {
        // Given: student is enrolled in a course
        student.getCourses().add(course);
        assertTrue(student.getCourses().contains(course));

        // When: student unenrolls
        student.getCourses().remove(course);

        // Then: course is no longer in list
        assertFalse(student.getCourses().contains(course));
        assertTrue(student.getCourses().isEmpty());
    }
}


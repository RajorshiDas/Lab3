package com.example.lab3.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    private Teacher teacher;
    private Course course;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Mr. Smith");

        course = new Course();
        course.setId(1L);
        course.setTitle("Physics 101");
    }

    @Test
    void whenTeacherCreatesCourse_thenCourseIsAddedToTeachersCourseList() {
        // Given: a teacher with no courses
        assertTrue(teacher.getCourses().isEmpty());

        // When: teacher creates a course (course is assigned to teacher)
        course.setTeacher(teacher);
        teacher.getCourses().add(course);

        // Then: course is in teacher's course list
        assertTrue(teacher.getCourses().contains(course));
        assertEquals(1, teacher.getCourses().size());
        assertEquals(teacher, course.getTeacher());
    }

    @Test
    void whenTeacherCreatesMultipleCourses_thenAllCoursesAreInList() {
        // Given: another course
        Course course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Chemistry 101");

        // When: teacher creates both courses
        course.setTeacher(teacher);
        course2.setTeacher(teacher);
        teacher.getCourses().add(course);
        teacher.getCourses().add(course2);

        // Then: both courses are in teacher's course list
        assertEquals(2, teacher.getCourses().size());
        assertTrue(teacher.getCourses().contains(course));
        assertTrue(teacher.getCourses().contains(course2));
    }

    @Test
    void whenTeacherRemovesCourse_thenCourseIsRemovedFromList() {
        // Given: teacher has a course
        course.setTeacher(teacher);
        teacher.getCourses().add(course);
        assertTrue(teacher.getCourses().contains(course));

        // When: teacher removes the course
        teacher.getCourses().remove(course);

        // Then: course is no longer in list
        assertFalse(teacher.getCourses().contains(course));
        assertTrue(teacher.getCourses().isEmpty());
    }

    @Test
    void whenCourseAssignedToTeacher_thenTeacherIsSetOnCourse() {
        // When: course is assigned to teacher
        course.setTeacher(teacher);

        // Then: teacher is accessible from course
        assertEquals(teacher, course.getTeacher());
        assertEquals("Mr. Smith", course.getTeacher().getName());
    }
}


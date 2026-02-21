package com.example.lab3.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for Student and Teacher domain classes.
 * Tests the interaction between students enrolling in courses created by teachers.
 */
class StudentTeacherIntegrationTest {

    private Student student;
    private Teacher teacher;
    private Department department;

    @BeforeEach
    void setUp() {
        // Create a department
        department = new Department();
        department.setId(1L);
        department.setName("Computer Science");

        // Create a teacher in the department
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Dr. Smith");
        teacher.setDepartment(department);

        // Create a student in the department
        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setDepartment(department);
    }

    @Test
    void whenStudentEnrollsInCourseCreatedByTeacher_thenEnrollmentIsSuccessful() {
        // Given: a teacher creates a course
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Introduction to Programming");
        course.setTeacher(teacher);
        course.setDepartment(department);
        teacher.getCourses().add(course);

        // When: student enrolls in the course
        student.getCourses().add(course);
        course.getStudents().add(student);

        // Then: student is enrolled in the course
        assertTrue(student.getCourses().contains(course));
        assertTrue(course.getStudents().contains(student));
        assertEquals(teacher, course.getTeacher());
        assertEquals("Dr. Smith", course.getTeacher().getName());
    }

    @Test
    void whenStudentEnrollsInMultipleCoursesFromSameTeacher_thenAllEnrollmentsSucceed() {
        // Given: a teacher creates multiple courses
        Course course1 = new Course();
        course1.setId(1L);
        course1.setTitle("Data Structures");
        course1.setTeacher(teacher);
        course1.setDepartment(department);
        teacher.getCourses().add(course1);

        Course course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Algorithms");
        course2.setTeacher(teacher);
        course2.setDepartment(department);
        teacher.getCourses().add(course2);

        // When: student enrolls in both courses
        student.getCourses().add(course1);
        student.getCourses().add(course2);
        course1.getStudents().add(student);
        course2.getStudents().add(student);

        // Then: student is enrolled in both courses
        assertEquals(2, student.getCourses().size());
        assertEquals(2, teacher.getCourses().size());
        assertTrue(student.getCourses().contains(course1));
        assertTrue(student.getCourses().contains(course2));
    }

    @Test
    void whenStudentEnrollsInCoursesFromDifferentTeachers_thenAllEnrollmentsSucceed() {
        // Given: two teachers with different courses
        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setName("Prof. Johnson");
        teacher2.setDepartment(department);

        Course course1 = new Course();
        course1.setId(1L);
        course1.setTitle("Math 101");
        course1.setTeacher(teacher);
        teacher.getCourses().add(course1);

        Course course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Physics 101");
        course2.setTeacher(teacher2);
        teacher2.getCourses().add(course2);

        // When: student enrolls in courses from both teachers
        student.getCourses().add(course1);
        student.getCourses().add(course2);
        course1.getStudents().add(student);
        course2.getStudents().add(student);

        // Then: student is enrolled in both courses from different teachers
        assertEquals(2, student.getCourses().size());
        assertEquals(teacher, course1.getTeacher());
        assertEquals(teacher2, course2.getTeacher());
        assertNotEquals(course1.getTeacher(), course2.getTeacher());
    }

    @Test
    void whenMultipleStudentsEnrollInSameCourse_thenAllEnrollmentsSucceed() {
        // Given: a course created by a teacher
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Web Development");
        course.setTeacher(teacher);
        teacher.getCourses().add(course);

        // And: another student
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane Doe");
        student2.setDepartment(department);

        // When: both students enroll in the course
        student.getCourses().add(course);
        student2.getCourses().add(course);
        course.getStudents().add(student);
        course.getStudents().add(student2);

        // Then: both students are enrolled
        assertEquals(2, course.getStudents().size());
        assertTrue(course.getStudents().contains(student));
        assertTrue(course.getStudents().contains(student2));
        assertTrue(student.getCourses().contains(course));
        assertTrue(student2.getCourses().contains(course));
    }

    @Test
    void whenStudentUnenrollsFromCourse_thenCourseStillBelongsToTeacher() {
        // Given: a student enrolled in a teacher's course
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Database Systems");
        course.setTeacher(teacher);
        teacher.getCourses().add(course);
        student.getCourses().add(course);
        course.getStudents().add(student);

        // When: student unenrolls from the course
        student.getCourses().remove(course);
        course.getStudents().remove(student);

        // Then: course still belongs to teacher but student is no longer enrolled
        assertFalse(student.getCourses().contains(course));
        assertFalse(course.getStudents().contains(student));
        assertTrue(teacher.getCourses().contains(course));
        assertEquals(teacher, course.getTeacher());
    }

    @Test
    void verifyCourseRelationshipsBetweenStudentAndTeacher() {
        // Given: a complete setup with teacher, course, and student
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Software Engineering");
        course.setTeacher(teacher);
        course.setDepartment(department);
        teacher.getCourses().add(course);
        student.getCourses().add(course);
        course.getStudents().add(student);

        // Then: verify all relationships are correctly established
        // Course -> Teacher relationship
        assertNotNull(course.getTeacher());
        assertEquals("Dr. Smith", course.getTeacher().getName());

        // Teacher -> Course relationship
        assertTrue(teacher.getCourses().contains(course));

        // Student -> Course relationship
        assertTrue(student.getCourses().contains(course));

        // Course -> Student relationship
        assertTrue(course.getStudents().contains(student));

        // Both student and teacher are in the same department
        assertEquals(student.getDepartment(), teacher.getDepartment());
        assertEquals(department, course.getDepartment());
    }
}


package com.example.school;

import com.example.school.student.StudentService;
import com.example.school.student.model.Student;
import com.example.school.teacher.TeacherService;
import com.example.school.teacher.model.Teacher;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class MockData {
    public static void CreateMockData() {
        // Create mock data
        Teacher teacher = new Teacher();
        teacher.setUsername("teacher1");
        teacher.setPassword(BCrypt.hashpw("password1", BCrypt.gensalt()));

        TeacherService.saveTeacher(teacher);

        Student student1 = new Student();
        student1.setName("student1");
        student1.setSurname("surname1");
        student1.setAdress("adress1");
        student1.setCity("city1");
        student1.setPostalCode("postalCode1");
        student1.setEmail("email1@student.es");

        StudentService.createStudent(student1);

        Student student2 = new Student();
        student2.setName("student2");
        student2.setSurname("surname2");
        student2.setAdress("adress2");
        student2.setCity("city2");
        student2.setPostalCode("postalCode2");
        student2.setEmail("email2@student.es");

        StudentService.createStudent(student2);

    }
}

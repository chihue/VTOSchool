package com.example.school.student;

import com.example.school.student.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    @Autowired
    private StudentRepository tStudentRepository;
    private static StudentRepository studentRepository;

    public static List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public static Optional<Student> getStudent(UUID id) {
        return studentRepository.findById(id);
    }

    public static void deleteStudent(UUID id) {
        studentRepository.deleteById(id);
    }

    public static Student createStudent(Student student) {
        student.setUuid(UUID.randomUUID());
        return studentRepository.save(student);
    }

    public static Student updateStudent(Student student) {
        Student updatedStudent = null;

        if (student.getUuid() != null) {
            Optional<Student> opStudent = StudentService.getStudent(student.getUuid());
            if (opStudent.isPresent()) {
                updatedStudent = opStudent.get();
                if (student.getName() != null) {
                    updatedStudent.setName(student.getName());
                }

                if (student.getName() != null) {
                    updatedStudent.setName(student.getName());
                }

                if (student.getSurname() != null) {
                    updatedStudent.setSurname(student.getSurname());
                }

                if (student.getAdress() != null) {
                    updatedStudent.setAdress(student.getAdress());
                }

                if (student.getCity() != null) {
                    updatedStudent.setCity(student.getCity());
                }

                if (student.getPostalCode() != null) {
                    updatedStudent.setPostalCode(student.getPostalCode());
                }

                if (student.getEmail() != null) {
                    updatedStudent.setEmail(student.getEmail());
                }


                studentRepository.save(updatedStudent);
            }
        }

        return updatedStudent;
    }

    @PostConstruct
    public void init() {
        studentRepository = tStudentRepository;
    }

}

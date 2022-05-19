package com.example.school.student;

import com.example.school.student.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
/**
 * It's a service class that provides CRUD operations for the Student entity
 */
public class StudentService {
    @Autowired
    private StudentRepository tStudentRepository;
    private static StudentRepository studentRepository;

    /**
     * Get all students from the database and return them as a list.
     *
     * @return A list of all students in the database.
     */
    public static List<Student> getStudents() {
        return studentRepository.findAll();
    }

    /**
     * If the student exists, return the student, otherwise return an empty Optional.
     *
     * @param id The id of the student to retrieve
     * @return Optional<Student>
     */
    public static Optional<Student> getStudent(UUID id) {
        return studentRepository.findById(id);
    }

    /**
     * Delete a student by id.
     *
     * @param id The id of the student to be deleted.
     */
    public static void deleteStudent(UUID id) {
        studentRepository.deleteById(id);
    }

    /**
     * Create a student by generating a random UUID and saving the student to the database.
     *
     * @param student The student object that is passed in the request body.
     * @return A student object
     */
    public static Student createStudent(Student student) {
        student.setUuid(UUID.randomUUID());
        return studentRepository.save(student);
    }

    /**
     * If the student exists, update the student with the new values
     *
     * @param student The student object that contains the data to be updated.
     * @return The updated student
     */
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

package com.example.school.student;

import com.example.school.model.ApiResponse;
import com.example.school.student.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/student")
/**
 * CRUD dor Students Entoty
 */
public class StudentController {

    /**
     * It returns a list of students from the database
     *
     * @return A ResponseEntity object is being returned.
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getStudents() {
        ApiResponse response = new ApiResponse();

        try {
            List<Student> students = StudentService.getStudents();
            response.setStudents(students);
        }   catch (Exception e) {
            response.addError("Error while getting students");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * If the student is found, return the student in the response. If the student is not found, return a 404 error
     *
     * @param uuid The UUID of the student to retrieve
     * @return ResponseEntity<ApiResponse>
     */
    @RequestMapping(value = "{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getStudent(@PathVariable("uuid") UUID uuid) {
        ApiResponse response = new ApiResponse();

        Optional<Student> opStudent = StudentService.getStudent(uuid);

        if (opStudent.isPresent()) {
            response.setStudent(opStudent.get());
        }   else {
            response.addError("Student not found");
            response.setStatus(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * It deletes a student from the database.
     *
     * @param uuid The unique identifier of the student to be deleted.
     * @return A ResponseEntity object is being returned.
     */
    @RequestMapping(value = "{uuid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable("uuid") UUID uuid) {
        ApiResponse response = new ApiResponse();
        StudentService.deleteStudent(uuid);
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * If the request body is valid, create a new student and return the student object with a status of 201. If the
     * request body is not valid, return a list of errors with a status of 400
     *
     * @param student The student object that will be created.
     * @param errors This is the object that will contain the validation errors.
     * @return A ResponseEntity object is being returned.
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createStudnet(@Valid @RequestBody Student student, Errors errors) {
        ApiResponse response = new ApiResponse();

        if (errors.hasErrors()) {
            response.setSucess(false);
            response.setStatus(HttpStatus.BAD_REQUEST);
            for (ObjectError error : errors.getAllErrors()) {
                response.addError(error.getDefaultMessage());
            }
        }   else {
            Student studentCreated = StudentService.createStudent(student);

            if (studentCreated != null) {
                response.setStudent(studentCreated);
                response.setStatus(HttpStatus.CREATED);
            }   else {
                response.addError("Student not created");
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * This function updates a student in the database
     *
     * @param student The student object that will be updated.
     * @return A ResponseEntity object is being returned.
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateStudent(@RequestBody Student student) {
        ApiResponse response = new ApiResponse();

        Student studentUpdated = StudentService.updateStudent(student);
        if (studentUpdated != null) {
            response.setStudent(studentUpdated);
        }   else {
            response.addError("Student not found");
            response.setStatus(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, response.getStatus());
    }

}

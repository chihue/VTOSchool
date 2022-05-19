package com.example.school.model;

import com.example.school.student.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import java.util.List;

/**
 * Transfer Object for API response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private Boolean sucess = true;
    private List<String> errors;
    private List<Student> students;
    private Student student;
    private String token = null;
    private Long validTime = null;
    @JsonIgnore
    private HttpStatus status = HttpStatus.OK;

    public Boolean getSucess() {
        return sucess;
    }

    public void setSucess(Boolean sucess) {
        this.sucess = sucess;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getValidTime() {
        return validTime;
    }

    public void setValidTime(Long validTime) {
        this.validTime = validTime;
    }

    public void addError(String error) {
        if (errors == null) {
            errors = new java.util.ArrayList<>();
        }
        errors.add(error);
        this.setSucess(false);
    }

    @Override
    public String toString() {
        String response = "";
        try {
            response = new ObjectMapper().writeValueAsString(this);
        }   catch (Exception e) {}
        return response;
    }
}

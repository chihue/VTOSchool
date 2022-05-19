package com.example.school.teacher;

import com.example.school.teacher.model.Teacher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class TeacherService {
    private static final Logger log = LogManager.getLogger(TeacherService.class);

    @Autowired
    private TeacherRepository tTeacherRepository;
    private static TeacherRepository teacherRepository;

    public static void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    /**
     * If the teacher is present, return the teacher, otherwise return null
     *
     * @param name The name of the teacher to be retrieved.
     * @return A Teacher object
     */
    public static Teacher getTeacherByName(String name) {
        Teacher teacher = null;

        Optional<Teacher> opTeacher = TeacherService.teacherRepository.findById(name);

        if (opTeacher.isPresent()) {
            teacher = opTeacher.get();
        }   else {
            log.error("Teacher not found: " + name);
        }

        return teacher;
    }

    @PostConstruct
    public void init() {
        TeacherService.teacherRepository = tTeacherRepository;
    }

}

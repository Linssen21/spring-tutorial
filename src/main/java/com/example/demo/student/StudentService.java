package com.example.demo.student;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Class use @AllArgsConstructor which Generates a constructor with
 * @since 1.0.0
 * @author Linssen
 */

@AllArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        // check if email is taken
        studentRepository.save(student);
    }

//    @AllArgsConstructor - Generates this code
//    public StudentService(StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }
}

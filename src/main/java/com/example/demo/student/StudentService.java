package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo.util.BaseEntity.*;
import java.util.List;

import static com.example.demo.util.BaseEntity.SORT_BY_CREATED_AT_DESC;

/**
 * Service Class use @AllArgsConstructor which Generates a constructor with
 * @since 1.0.0
 * @author Linssen
 */

@AllArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;

//    @AllArgsConstructor - Generates this code for dependency injection
//    public StudentService(StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }
    public List<Student> getAllStudents(){
        return studentRepository.findAll(SORT_BY_CREATED_AT_DESC);
    }

//    public List<Student> findAllStudents(){
//        return  studentRepository.getAll();
//    }

    public void addStudent(Student student) {
        // check if email is taken
        Boolean existEmail = studentRepository.selectExistsEmail(student.getEmail());
        if(existEmail){
            throw new BadRequestException("Email " + student.getEmail() + " taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Boolean existsByIdId = studentRepository.existsById(id);
        if(!existsByIdId){
            throw new StudentNotFoundException("Student with id " + id + " does not exists");
        }
        studentRepository.deleteById(id);
    }


}

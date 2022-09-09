package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents(){
//        throw new IllegalStateException("Oops Error");
        return studentService.getAllStudents();
    }

//    @GetMapping("/all")
//    public Iterable<Student> findAllStudents(){
//        return studentService.findAllStudents();
//    }

    @PostMapping
    public void addStudent(@RequestBody Student student){
        studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id){
        //throw new IllegalStateException("Oops Error");
        studentService.deleteStudent(id);
    }



}

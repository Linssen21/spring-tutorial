package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.demo.util.BaseEntity.SORT_BY_CREATED_AT_DESC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    /**
     * Instead of using Autowired Annotation we will be using @Mock
     * Mock the implementation of Student Repository
     */
    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    // Runs before Each test
    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void getAllStudents() {
        // when
        underTest.getAllStudents();
        // then
        verify(studentRepository).findAll(SORT_BY_CREATED_AT_DESC);
    }

    @Test
    void canAddStudent() {
        // given
        String email = "linssen@gmail.com";
        Student student = new Student("Linssen", email,Gender.MALE);
        // when
        underTest.addStudent(student);
        // then
        // Capture
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        // Check if StudentRepository.save(Student) is executed
        verify(studentRepository).save(studentArgumentCaptor.capture());
        // Get the captured value upon listening to the save method
        Student capturedStudent = studentArgumentCaptor.getValue();
        // Compared the given student to the added student
        assertThat(capturedStudent).isEqualTo(student);

    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        String email = "linssen@gmail.com";
        Student student = new Student("Linssen", email,Gender.MALE);

        // Mock an Existing student
        given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true);

        // when
        // then
        /**
         * Expected result:
         * Return an instace of BadRequestException
         * Return a String that the email is already taken
         */
        assertThatThrownBy(() -> underTest.addStudent(student)).isInstanceOf(BadRequestException.class).hasMessageContaining("Email " + student.getEmail() + " taken");

        // Mock that student doest not save
        verify(studentRepository, never()).save(any());
    }

    @Test
    void canDeleteStudent() {
        // given
        Long id = 10L;
        given(studentRepository.existsById(id)).willReturn(true);

        //when
        underTest.deleteStudent(id);

        //then
        verify(studentRepository).deleteById(id);

    }

    @Test
    void willThrowWhenDeleteStudentNotFound(){
        //given
        // Mock a student that does not exist
        Long id = 20L;
        given(studentRepository.existsById(id)).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> underTest.deleteStudent(id)).isInstanceOf(StudentNotFoundException.class).hasMessageContaining("Student with id " + id + " does not exists");

        // then
        verify(studentRepository, never()).deleteById(id);
    }

}
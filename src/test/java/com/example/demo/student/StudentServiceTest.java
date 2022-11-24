package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.demo.util.BaseEntity.SORT_BY_CREATED_AT_DESC;
import static org.mockito.Mockito.verify;

class StudentServiceTest {
    /**
     * Instead of using Autowired Annotation we will be using @Mock
     * Mock the implementation of Student Repository inside of Student Service Test
     */
    @Mock
    private StudentRepository studentRepository;
    private AutoCloseable autoCloseable;
    private StudentService underTest;

    // Runs before Each test
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

    /**
     * Close the Resource after each Test
     * @throws Exception
     */
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllStudents() {
        // when
        underTest.getAllStudents();
        // then
        verify(studentRepository).findAll(SORT_BY_CREATED_AT_DESC);
    }

    @Test
    @Disabled
    void addStudent() {
    }

    @Test
    @Disabled
    void deleteStudent() {
    }

}
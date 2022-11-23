package com.example.demo.student;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test for JPA
 */

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    /**
     * Delete All data after the test
     * Reset / delete data after each test
     */
    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExists() {
        // give
        String email = "linssentest@gmail.com";
        Student student = new Student("Linssen", email,Gender.MALE);
        underTest.save(student);
        // when
        boolean exists = underTest.selectExistsEmail(email);
        // then
        assertThat(exists).isTrue();
    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExist() {
        // give
        String email = "linssentest@gmail.com";
        // when
        boolean exists = underTest.selectExistsEmail(email);
        // then
        assertThat(exists).isFalse();
    }
}
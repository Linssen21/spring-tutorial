package com.example.demo.integration;

import com.example.demo.student.Gender;
import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Student Integration Test
 * Set the application-it.properties to use on this integration test
 * Testing the StudentController
 *
 * Integration Test we will actual HTTP request test
 * We will use MockMvc for Integration Test
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
public class StudentIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    private final Faker faker = new Faker();

    /**
     * HTTP Mock Test "api/v1/students"
     * set Content type to JSON
     * Convert student Object to Json using ObjectMapper
     * @throws Exception
     */
    @Test
    void canRegisterNewStudent() throws Exception {
        String name =  String.format("%s %s", faker.name().firstName(), faker.name().lastName());
        String email = StringUtils.trimAllWhitespace(String.format("%s@gmail.com", name.toLowerCase()));
        //given
        Student student = new Student(name, email, Gender.MALE);
        //when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/students").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(student)));
        //then
        resultActions.andExpect(status().isOk());
        List<Student> students = studentRepository.findAll();
        // Ignores id for finding Student on the list (does not work)
        // assertThat(students).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").contains(student);
        assertThat(students).contains(student);
    }

    @Test
    void canDeleteStudent() throws Exception{
        //given
        String name =  String.format("%s %s", faker.name().firstName(), faker.name().lastName());
        String email = StringUtils.trimAllWhitespace(String.format("%s@gmail.com", name.toLowerCase()));
        Student student = new Student(name, email, Gender.MALE);

        // Add new student
        mockMvc
                .perform(post("/api/v1/students").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(student)));
        // Fetch/Get all students
        MvcResult getStudentResult = mockMvc.perform(get("/api/v1/students").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        // Convert Response to String
        String contentString = getStudentResult.getResponse().getContentAsString();
        // Convert Response String to Object by using ObjectMapper
        List<Student> students = objectMapper.readValue(contentString, new TypeReference<List<Student>>() {
        });

        /**
         * Fetch student id by using stream function
         * Filter all students from the list by the Added email
         * Map the filtered student and only returns the ID field
         * Return first result
         */
        long id = students
                .stream()
                .filter(studentPredicate -> studentPredicate.getEmail().equals(student.getEmail()))
                .map(Student::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("student with email: " + email + " not found"));

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/students/{id}", id));

        // then
        resultActions.andExpect(status().isOk());
        boolean exists = studentRepository.existsById(id);
        assertThat(exists).isFalse();
    }
}

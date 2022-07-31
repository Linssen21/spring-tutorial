package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JpaRepository Passed generic data Entity and Entity ID Data type
 * @since 1.0.0
 * @author Linssen
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}

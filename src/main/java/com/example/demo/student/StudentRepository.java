package com.example.demo.student;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JpaRepository Passed generic data Entity and Entity ID Data type
 * @since 1.0.0
 * @author Linssen
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
   @Override
   public List<Student> findAll(Sort sort);

   /**
    * @author Linssen
    * @param email
    * @return boolean
    * @implNote since JpaRepository does not have an exist check, need to create a custom exist check query
    */
   @Query("" +
           "SELECT CASE WHEN COUNT (student) > 0 THEN " +
           "TRUE ELSE FALSE END " +
           "FROM Student student " +
           "WHERE student.email = ?1"
   )
   Boolean selectExistsEmail(String email);
}

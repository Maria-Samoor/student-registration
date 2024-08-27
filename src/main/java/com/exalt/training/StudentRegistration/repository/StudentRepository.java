package com.exalt.training.StudentRegistration.repository;

import com.exalt.training.StudentRegistration.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repository interface for performing CRUD operations on {@link Student} entities.
 * Extends {@link MongoRepository} to leverage built-in MongoDB functionalities.
 */
@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    /**
     * Finds a {@link Student} by their email address.
     *
     * @param email The email address of the student.
     * @return An {@link Optional} containing the student if found, or {@link Optional#empty()} if not found.
     */
    Optional<Student> findStudentByEmail(String email);
}

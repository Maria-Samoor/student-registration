package com.exalt.training.StudentRegistration.service;

import com.exalt.training.StudentRegistration.enums.Specialization;
import com.exalt.training.StudentRegistration.model.Student;

import java.util.List;

/**
 * Service interface for student-related operations.
 */
public interface StudentService {
    /**
     * Retrieves all students.
     *
     * @return List of all students
     */
    List<Student> getAllStudents();

    /**
     * Retrieves a student by email.
     *
     * @param email Email of the student to retrieve
     * @return The student with the specified email
     */
    Student getStudentByEmail(String email);

    /**
     * Deletes a student by email.
     *
     * @param email Email of the student to delete
     */
    void deleteStudentByEmail(String email);
    /**
     * Updates the specialization of a student identified by their email address.
     *
     * @param email Email of the student to update
     * @param newSpecialization The new specialization to assign to the student
     * @return The updated student
     */
    Student updateStudentSpecializationByEmail(String email, Specialization newSpecialization);

    /**
     * Creates a new student and saves them to the database.
     *
     * @param student The student to create
     * @return The created student
     */
    Student createStudent(Student student);
}

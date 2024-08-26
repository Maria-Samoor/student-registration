package com.exalt.training.StudentRegistration.service.impl;

import com.exalt.training.StudentRegistration.exception.EmailAlreadyInUseException;
import com.exalt.training.StudentRegistration.exception.StudentNotFoundException;
import com.exalt.training.StudentRegistration.model.Specialization;
import com.exalt.training.StudentRegistration.model.Student;
import com.exalt.training.StudentRegistration.repository.StudentRepository;
import com.exalt.training.StudentRegistration.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the StudentService interface.
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class); // Logger instance
    private final StudentRepository studentRepository;

    /**
     * Retrieves all students from the database.
     *
     * @return List of all students
     */
    @Transactional
    @Override
    public List<Student> getAllStudents(){
        logger.info("Fetching all students from the database");
        return studentRepository.findAll();
    }

    /**
     * Retrieves a student by their email address.
     *
     * @param email Email of the student to retrieve
     * @return The student with the specified email
     * @throws StudentNotFoundException if no student is found with the given email
     */
    @Transactional
    @Override
    public Student getStudentByEmail(String email){
        logger.info("Fetching student with email: {}", email);
        return studentRepository.findStudentByEmail(email)
                .orElseThrow(() -> {
                    logger.error("Student with email {} not found", email);
                    return new StudentNotFoundException("Student with email " + email + " not found");
                });
    }

    /**
     * Deletes a student by their email address.
     *
     * @param email Email of the student to delete
     * @throws StudentNotFoundException if no student is found with the given email
     */
    @Transactional
    @Override
    public void deleteStudentByEmail(String email) {
        logger.info("Deleting student with email: {}", email);
        Student student = studentRepository.findStudentByEmail(email)
                .orElseThrow(() -> {
                    logger.error("Student with email {} not found for deletion", email);
                    return new StudentNotFoundException("Student with email " + email + " not found");
                });
        studentRepository.delete(student);
        logger.info("Student with email {} deleted successfully", email);
    }

    /**
     * Updates the specialization of a student identified by their email address.
     *
     * @param email Email of the student to update
     * @param newSpecialization The new specialization to assign to the student
     * @return The updated student
     * @throws StudentNotFoundException if no student is found with the given email
     */
    @Transactional
    @Override
    public Student updateStudentSpecializationByEmail(String email, Specialization newSpecialization) {
        logger.info("Updating specialization for student with email: {}", email);
        Student student = studentRepository.findStudentByEmail(email)
                .orElseThrow(() -> {
                    logger.error("Student with email {} not found for updating specialization", email);
                    return new StudentNotFoundException("Student with email " + email + " not found");
                });
        student.setSpecialization(newSpecialization);
        Student updatedStudent = studentRepository.save(student);
        logger.info("Updated specialization for student with email: {}", email);
        return updatedStudent;
    }

    /**
     * Creates a new student and saves them to the database.
     *
     * @param student The student to create
     * @return The created student
     */
    @Transactional
    @Override
    public Student createStudent(Student student) {
        logger.info("Creating a new student with email: {}", student.getEmail());
        if (studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
            logger.error("Email already in use: {}", student.getEmail());
            throw new EmailAlreadyInUseException(student.getEmail()+ " is already in use");
        }
        Student savedStudent = studentRepository.save(student);
        logger.info("Student with email {} created successfully", student.getEmail());
        return savedStudent;
    }
}

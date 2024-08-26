package com.exalt.training.StudentRegistration.controller;

import com.exalt.training.StudentRegistration.exception.EmailAlreadyInUseException;
import com.exalt.training.StudentRegistration.exception.StudentNotFoundException;
import com.exalt.training.StudentRegistration.model.Specialization;
import com.exalt.training.StudentRegistration.model.Student;
import com.exalt.training.StudentRegistration.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling student-related requests.
 */
@RestController
@RequestMapping("/exalt/training/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService; //Service for providing student-related operations

    /**
     * Fetches all students from the database.
     *
     * @return ResponseEntity containing a list of students and HTTP status OK
     */
    @GetMapping("/fetch")
    public  ResponseEntity<List<Student>> fetchAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /**
     * Retrieves a student by email.
     *
     * @param email Email of the student to retrieve
     * @return ResponseEntity containing the student or an error message
     */
    @GetMapping("/retrieve/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(studentService.getStudentByEmail(email));
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Deletes a student by email.
     *
     * @param email Email of the student to delete
     * @return ResponseEntity containing a success message or an error message
     */
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) {
        try {
            studentService.deleteStudentByEmail(email);
            return ResponseEntity.ok("User deleted successfully");
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Updates a student's specialization by email.
     *
     * @param email Email of the student to update
     * @param newSpecialization New specialization to set
     * @return ResponseEntity containing the updated student or an error message
     */
    @PutMapping("/update-specialization/{email}")
    public ResponseEntity<?> updateUserRole(@PathVariable String email, @RequestParam("specialization") String newSpecialization) {
        try {
            Specialization specialization = Specialization.valueOf(newSpecialization);
            return ResponseEntity.ok(studentService.updateStudentSpecializationByEmail(email, specialization));
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Creates a new student.
     *
     * @param student The student to create
     * @param bindingResult Contains validation errors, if any
     * @return ResponseEntity containing the created student or validation error message
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            Student createdStudent = studentService.createStudent(student);
            return ResponseEntity.ok(createdStudent);
        } catch (EmailAlreadyInUseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

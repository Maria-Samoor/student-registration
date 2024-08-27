package com.exalt.training.StudentRegistration.controller;

import com.exalt.training.StudentRegistration.exception.EmailAlreadyInUseException;
import com.exalt.training.StudentRegistration.exception.StudentNotFoundException;
import com.exalt.training.StudentRegistration.enums.Specialization;
import com.exalt.training.StudentRegistration.model.Student;
import com.exalt.training.StudentRegistration.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/all-students")
    public  ResponseEntity<List<Student>> fetchAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /**
     * Retrieves a student by email.
     *
     * @return ResponseEntity containing the student or an error message
     */
    @GetMapping("/get-student")
    public ResponseEntity<?> getStudentByEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            return ResponseEntity.ok(studentService.getStudentByEmail(email));
        } catch (StudentNotFoundException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a student by email.
     *
     * @return ResponseEntity containing a success message or an error message
     */
    @DeleteMapping("/delete-student")
    public ResponseEntity<?> deleteUserByEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            studentService.deleteStudentByEmail(email);
            return ResponseEntity.ok("User deleted successfully");
        } catch (StudentNotFoundException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates a student's specialization by email.
     *
     * @return ResponseEntity containing the updated student or an error message
     */
    @PutMapping("/update-specialization")
    public ResponseEntity<?> updateStudentSpecialization(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newSpecialization = request.get("specialization");
        try {
            Specialization specialization = Specialization.valueOf(newSpecialization);
            return ResponseEntity.ok(studentService.updateStudentSpecializationByEmail(email, specialization));
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Invalid specialization provided", HttpStatus.BAD_REQUEST);
        } catch (StudentNotFoundException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a new student.
     *
     * @param student The student to create
     * @param bindingResult Contains validation errors, if any
     * @return ResponseEntity containing the created student or validation error message
     */
    @PostMapping("/create-student")
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            Student createdStudent = studentService.createStudent(student);
            return ResponseEntity.ok(createdStudent);
        } catch (EmailAlreadyInUseException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Creates a standardized error response to be returned in case of exceptions or errors.
     *
     * @param message The error message to be included in the response.
     * @param status The HTTP status code to be set for the response.
     * @return A {@link ResponseEntity} containing a map with the error message and status code.
     */
    private ResponseEntity<Map<String, Object>> createErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        errorResponse.put("status", status.value());
        return new ResponseEntity<>(errorResponse, status);
    }
}

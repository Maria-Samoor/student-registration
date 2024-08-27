package com.exalt.training.StudentRegistration.model;

import com.exalt.training.StudentRegistration.enums.Gender;
import com.exalt.training.StudentRegistration.enums.Specialization;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * Represents a student collection in the non-relational mongoDB .
 */
@Data
@NoArgsConstructor
@Document(collection = "student")
public class Student {
    @Id
    private String id; //The unique identifier for the student.
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName; //The first name of the student.
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName; //The last name of the student.
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;//The email address of the student.
    @NotNull(message = "Gender is required")
    private Gender gender; //The gender of the student.
    @NotNull(message = "Address is required")
    private Address address; //The address of the student, which includes country, city, and postcode.
    @NotNull(message = "Specialization is required")
    private Specialization specialization; //The specialization of the student.
    private LocalDateTime created=LocalDateTime.now(); //The date and time when the student was created.

    /**
     * Constructs a new {@code Student} with the specified details. The {@code created} field is initialized
     * to the current date and time.
     *
     * @param firstName       The first name of the student. Must be between 2 and 50 characters.
     * @param lastName        The last name of the student. Must be between 2 and 50 characters.
     * @param email           The email address of the student. Must be a valid email format.
     * @param gender          The gender of the student. Cannot be {@code null}.
     * @param address         The address of the student, including country, city, and postcode. Cannot be {@code null}.
     * @param specialization The specialization of the student. Cannot be {@code null}.
     */
    public Student(String firstName, String lastName, String email, Gender gender, Address address, Specialization specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.specialization = specialization;
        this.created = LocalDateTime.now();
    }
}

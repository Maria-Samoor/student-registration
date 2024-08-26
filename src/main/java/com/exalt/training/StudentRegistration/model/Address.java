package com.exalt.training.StudentRegistration.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents an address associated with a student.
 */
@Data
@AllArgsConstructor
public class Address {
    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters")
    private String country; //The country of the student's address.
    @NotBlank(message = "City is required")
    @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters")
    private String city; //The city of the student's address.
    @NotBlank(message = "Postcode is required")
    @Pattern(regexp = "\\d{5}(-\\d{4})?", message = "Postcode must be in the format 12345 or 12345-6789")
    private String postCode; //The postcode of the student's address.
}

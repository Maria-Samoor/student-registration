package com.exalt.training.StudentRegistration.Config;

import com.exalt.training.StudentRegistration.model.Address;
import com.exalt.training.StudentRegistration.model.Gender;
import com.exalt.training.StudentRegistration.model.Specialization;
import com.exalt.training.StudentRegistration.model.Student;
import com.exalt.training.StudentRegistration.repository.StudentRepository;
import com.exalt.training.StudentRegistration.service.impl.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up initial data for the student repository.
 * This class defines a {@code CommandLineRunner} bean that executes when the
 * Spring Boot application starts, allowing for the setup of initial student data.
 */
@Configuration
public class StudentConfig {

    /**
     * Provides a {@code CommandLineRunner} bean for initializing the student repository
     * with a sample student record if no student with the same email already exists.
     *
     * @param studentRepository The repository used to perform CRUD operations on student data.
     * @return A {@code CommandLineRunner} that initializes the student data.
     */
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class); // Logger instance
        return args ->{
            Address address= new Address("palestine","hebron","12345");
            Student student =new Student("maria","abu sammour","maria@gmail.com", Gender.FEMALE,address, Specialization.CSE);
            try {
                if (studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
                    throw new IllegalStateException("This Email Already Used");
                } else {
                    studentRepository.save(student);
                    logger.info("Student saved successfully: {}", student.getEmail());
                }
            } catch (IllegalStateException e) {
            logger.error("Error: {}", e.getMessage());
        }
        };
    }
}

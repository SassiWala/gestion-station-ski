package tn.esprit.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.services.RegistrationServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceMockTest {

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @Test
    public void testAddRegistrationAndAssignToSkier() {
        // Arrange
        Skier skier = new Skier();
        skier.setNumSkier(1L);

        Registration registration = new Registration();

        when(skierRepository.findById(eq(1L))).thenReturn(Optional.of(skier));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, 1L);

        // Assert
        assertEquals(registration, result);
        assertEquals(skier, registration.getSkier());
        Mockito.verify(registrationRepository).save(registration);
    }

    @Test
    public void testAssignRegistrationToCourse() {
        // Arrange
        Registration registration = new Registration();
        Course course = new Course();
        course.setNumCourse(1L);

        when(registrationRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(registration));
        when(courseRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(course));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Act
        Registration result = registrationServices.assignRegistrationToCourse(1L, 1L);

        // Assert
        assertEquals(registration, result);
        assertEquals(course, registration.getCourse());
        Mockito.verify(registrationRepository).save(registration);
    }

}
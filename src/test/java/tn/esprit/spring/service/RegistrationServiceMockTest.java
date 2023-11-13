package tn.esprit.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.services.RegistrationServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceMockTest {

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @Test
    void testAddRegistrationAndAssignToSkier() {
        // Mock data
        Registration registration = new Registration();
        Long numSkier = 1L;
        Skier skier = new Skier();


        // Mock the behavior of the skierRepository
        when(skierRepository.findById(numSkier)).thenReturn(Optional.of(skier));

        // Mock the behavior of the registrationRepository
        when(registrationRepository.save(any())).thenReturn(registration);

        // Test the service method
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, numSkier);

        // Verify that skierRepository.findById was called once with the correct argument
        verify(skierRepository, times(1)).findById(numSkier);

        // Verify that registrationRepository.save was called once with the correct argument
        verify(registrationRepository, times(1)).save(any());

        // Assert the result
        assertEquals(registration, result);
    }

    @Test
    void testAssignRegistrationToCourse() {
        // Mock data
        Long numRegistration = 1L;
        Long numCourse = 2L;
        Registration registration = new Registration();

        Course course = new Course();


        // Mock the behavior of the registrationRepository
        when(registrationRepository.findById(numRegistration)).thenReturn(Optional.of(registration));

        // Mock the behavior of the courseRepository
        when(courseRepository.findById(numCourse)).thenReturn(Optional.of(course));

        // Mock the behavior of the registrationRepository.save
        when(registrationRepository.save(any())).thenReturn(registration);

        // Test the service method
        Registration result = registrationServices.assignRegistrationToCourse(numRegistration, numCourse);

        // Verify that registrationRepository.findById was called once with the correct argument
        verify(registrationRepository, times(1)).findById(numRegistration);

        // Verify that courseRepository.findById was called once with the correct argument
        verify(courseRepository, times(1)).findById(numCourse);

        // Verify that registrationRepository.save was called once with the correct argument
        verify(registrationRepository, times(1)).save(any());

        // Assert the result
        assertEquals(registration, result);
    }



    @Test
    void testNumWeeksCourseOfInstructorBySupport() {
        // Mock data
        Long numInstructor = 1L;
        Support support = Support.SKI;

        // Mock the behavior of the registrationRepository
        when(registrationRepository.numWeeksCourseOfInstructorBySupport(numInstructor, support)).thenReturn(Arrays.asList(1, 2, 3));

        // Test the service method
        List<Integer> result = registrationServices.numWeeksCourseOfInstructorBySupport(numInstructor, support);

        // Verify that registrationRepository.numWeeksCourseOfInstructorBySupport was called once with the correct arguments
        verify(registrationRepository, times(1)).numWeeksCourseOfInstructorBySupport(numInstructor, support);

        // Assert the result
        assertEquals(Arrays.asList(1, 2, 3), result);
    }
}

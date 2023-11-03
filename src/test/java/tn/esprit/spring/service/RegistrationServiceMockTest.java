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

    @Test
    public void testAddRegistrationAndAssignToSkierAndCourse_IndividualCourse() {
        // Arrange
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        Course course = new Course();
        course.setNumCourse(1L);
        course.setTypeCourse(TypeCourse.INDIVIDUAL);

        Registration registration = new Registration();
        registration.setNumWeek(1);

        when(skierRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(skier));
        when(courseRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(eq(1), eq(1L), eq(1L))).thenReturn(0L);
        when(registrationRepository.countByCourseAndNumWeek(eq(course), eq(1))).thenReturn(0L);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);

        // Assert
        assertEquals(registration, result);
        assertEquals(skier, registration.getSkier());
        assertEquals(course, registration.getCourse());
        Mockito.verify(registrationRepository).save(registration);
    }

    @Test
    public void testAddRegistrationAndAssignToSkierAndCourse_CollectiveChildrenCourse() {
        // Arrange
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setDateOfBirth(LocalDate.now().minusYears(15));

        Course course = new Course();
        course.setNumCourse(1L);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);

        Registration registration = new Registration();
        registration.setNumWeek(1);

        when(skierRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(skier));
        when(courseRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(eq(1), eq(1L), eq(1L))).thenReturn(0L);
        when(registrationRepository.countByCourseAndNumWeek(eq(course), eq(1))).thenReturn(5L);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);

        //Assert
        assertEquals(null, result);
        Mockito.verify(registrationRepository, Mockito.never()).save(registration);
    }

    @Test
    public void testAddRegistrationAndAssignToSkierAndCourse_CollectiveAdultCourse() {
        // Arrange
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setDateOfBirth(LocalDate.now().minusYears(18));

        Course course = new Course();
        course.setNumCourse(1L);
        course.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);

        Registration registration = new Registration();
        registration.setNumWeek(1);

        when(skierRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(skier));
        when(courseRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(eq(1), eq(1L), eq(1L))).thenReturn(0L);
        when(registrationRepository.countByCourseAndNumWeek(eq(course), eq(1))).thenReturn(5L);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);

        // Assert
        assertEquals(null, result);
        Mockito.verify(registrationRepository, Mockito.never()).save(registration);
    }

    @Test
    public void testAddRegistrationAndAssignToSkierAndCourse_AlreadyRegistered() {
        // Arrange
        Skier skier = new Skier();
        skier.setNumSkier(1L);

        Course course = new Course();
        course.setNumCourse(1L);

        Registration registration = new Registration();
        registration.setNumWeek(1);

        when(skierRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(skier));
        when(courseRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(eq(1), eq(1L), eq(1L))).thenReturn(1L);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);

        // Assert
        assertEquals(null, result);
        Mockito.verify(registrationRepository, Mockito.never()).save(registration);
    }

    @Test
    public void testNumWeeksCourseOfInstructorBySupport() {
        // Arrange
        Long numInstructor = 1L;
        Support support = Support.SKI;

        List<Integer> weeks = new ArrayList<>();
        weeks.add(1);
        weeks.add(2);

        when(registrationRepository.numWeeksCourseOfInstructorBySupport(eq(numInstructor), eq(support))).thenReturn(weeks);

        // Act
        List<Integer> result = registrationServices.numWeeksCourseOfInstructorBySupport(numInstructor, support);

        // Assert
        assertEquals(weeks, result);
    }
}
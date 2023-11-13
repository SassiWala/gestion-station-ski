package tn.esprit.spring.Service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.IInstructorServices;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class InstructorServiceMockitoTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @Autowired
    IInstructorServices ii;

   @Test
   public void testAddInstructor() {
        // Arrange
        Instructor instructor = new Instructor();
        Mockito.when(instructorRepository.save(instructor)).thenReturn(instructor);

        // Act
        Instructor result = instructorServices.addInstructor(instructor);

        // Assert
        Assertions.assertEquals(instructor, result);
    }


    @Test
    public void testRetrieveInstructor() {
        // Arrange
        Long numInstructor = 1L;
        Instructor instructor = new Instructor();
        Mockito.when(instructorRepository.findById(numInstructor)).thenReturn(Optional.of(instructor));

        // Act
        Instructor result = instructorServices.retrieveInstructor(numInstructor);

        // Assert
        Assertions.assertEquals(instructor, result);
    }
    @Test
    public void testRetrieveAllInstructors() {
        // Arrange
        Instructor instructor1 = new Instructor();
        Instructor instructor2 = new Instructor();
        List<Instructor> expectedInstructors = Arrays.asList(instructor1, instructor2);

        // Define the behavior of the instructorRepository.findAll() method
        Mockito.when(instructorRepository.findAll()).thenReturn(expectedInstructors);

        // Act
        List<Instructor> result = instructorServices.retrieveAllInstructors();

        // Assert
        Assertions.assertEquals(expectedInstructors, result);
    }

   /* @Test
    public void testUpdateInstructor() {
        // Arrange
        Instructor updatedInstructor = new Instructor();

        // Act
        Instructor result = instructorServices.updateInstructor(updatedInstructor);

        // Assert
        Mockito.verify(instructorRepository, Mockito.times(1)).save(updatedInstructor);

        Assertions.assertEquals(updatedInstructor, result);
    }*/

}

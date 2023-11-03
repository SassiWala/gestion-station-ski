package tn.esprit.spring.Services;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@Slf4j
class CourseServiceMockTest {

    @InjectMocks
    private CourseServicesImpl coursesServices;

    @Mock
    private ICourseRepository courseRepository;

    @Test
    public void testAddCourse() {
        Course course = new Course( 6, TypeCourse.INDIVIDUAL, Support.SKI);
        course.setLevel(1);
        course.setTypeCourse(TypeCourse.INDIVIDUAL);
        course.setSupport(Support.SKI);
        course.setPrice((float) 5.6);




        // Mock the repository's save method
        when(courseRepository.save(course)).thenReturn(course);

        Course result = coursesServices.addCourse(course);

        // Verify that the save method was called once
        verify(courseRepository, times(1)).save(course);

        // Verify that the result is not null
        assertNotNull(result);

        // Verify that the result is the same as the input skier
        assertEquals(course, result);
    }




    @Test
    public void testRetrieveAllSkiers() {
        // Create a list of Course objects using the parameterized constructor
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1,TypeCourse.COLLECTIVE_ADULT,Support.SNOWBOARD));
        courses.add(new Course(2,TypeCourse.INDIVIDUAL,Support.SKI));


        // Mock the behavior of the courseRepository to return the list of courses
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = coursesServices.retrieveAllCourses();

        // Verify that the service method returns the expected result
        assertEquals(courses, result);

    }

   /* @Test
    public void testRetrieveCourse() {
        // Create a sample Course object for testing
        Long numCourse = 1L;
        Course sampleCourse = new Course( 1, TypeCourse.INDIVIDUAL,Support.SKI);

        // Mock the behavior of the courseRepository to return the sampleCourse when findById is called with numCourse
        when(courseRepository.findById(numCourse)).thenReturn(Optional.of(sampleCourse));

        // Call the retrieveCourse function
        Course retrievedCourse = coursesServices.retrieveCourse(numCourse);


        // Verify that the retrieved course is the same as the sampleCourse
        assertEquals(sampleCourse,retrievedCourse);

        // Verify that the retrievedCourse's ID matches the numCourse
        assertEquals(numCourse, retrievedCourse.getNumCourse());
    }*/
    @Test
    public void testRemoveCourse() {
        // Create a sample skier ID for testing
        Long numCourseToRemove = 1L;

        // Call the removeCourse function with the numCourse
        coursesServices.removeCourse(numCourseToRemove);

        // Verify that the deleteById method of courseRepository is called with the numCourseToRemove
        verify(courseRepository, times(1)).deleteById(numCourseToRemove);
    }


}

package tn.esprit.spring.services;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;

import java.util.logging.Logger;
import java.time.LocalDate;
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
public class SkierServiceMockTest {

    @InjectMocks
    private SkierServicesImpl skierServices;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Test
    public void testAddSkier() {
        Skier skier = new Skier();
        skier.setFirstName("wael");
        skier.setLastName("hcine");
        skier.setDateOfBirth(LocalDate.of(1990, 1, 1));
        skier.setCity("Tunis");

        Subscription subscription = new Subscription();
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.now());

        skier.setSubscription(subscription);

        // Mock the repository's save method
        when(skierRepository.save(skier)).thenReturn(skier);

        Skier result = skierServices.addSkier(skier);

        // Verify that the save method was called once
        verify(skierRepository, times(1)).save(skier);

        // Verify that the result is not null
        assertNotNull(result);

        // Verify that the result is the same as the input skier
        assertEquals(skier, result);
    }




    @Test
    public void testRetrieveAllSkiers() {
        // Create a list of Skier objects using the parameterized constructor
        List<Skier> skiers = new ArrayList<>();
        skiers.add(new Skier(null,
                "FirstName1", "LastName1",
                LocalDate.of(1990, 1, 1),
                "City1", null, null, null));
        skiers.add(new Skier(null,
                "FirstName2", "LastName2",
                LocalDate.of(1991, 2, 2),
                "City2", null, null, null));

        // Mock the behavior of the skierRepository to return the list of skiers
        when(skierRepository.findAll()).thenReturn(skiers);

        List<Skier> result = skierServices.retrieveAllSkiers();

        // Verify that the service method returns the expected result
        assertEquals(skiers, result);

    }

    @Test
    public void testRetrieveSkier() {
        // Create a sample Skier object for testing
        Long skierId = 1L;
        Skier sampleSkier = new Skier(skierId, "John", "Doe", LocalDate.of(1990, 1, 1), "City", null, null, null);

        // Mock the behavior of the skierRepository to return the sampleSkier when findById is called with skierId
        when(skierRepository.findById(skierId)).thenReturn(Optional.of(sampleSkier));

        // Call the retrieveSkier function
        Skier retrievedSkier = skierServices.retrieveSkier(skierId);

        // Verify that the retrieved skier is the same as the sampleSkier
        assertEquals(sampleSkier, retrievedSkier);

        // Verify that the retrievedSkier's ID matches the skierId
        assertEquals(skierId, retrievedSkier.getNumSkier());
    }
    @Test
    public void testRemoveSkier() {
        // Create a sample skier ID for testing
        Long skierIdToRemove = 1L;

        // Call the removeSkier function with the skier ID
        skierServices.removeSkier(skierIdToRemove);

        // Verify that the deleteById method of skierRepository is called with the skierIdToRemove
        verify(skierRepository, times(1)).deleteById(skierIdToRemove);
    }


}

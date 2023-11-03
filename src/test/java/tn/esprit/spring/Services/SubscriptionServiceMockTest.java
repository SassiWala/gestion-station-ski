package tn.esprit.spring.Services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SubscriptionServicesImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;



import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class SubscriptionServiceMockTest {

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionServicesImpl subscriptionServices;

    @Test
    public void testAddSubscription() {
        Subscription subscription = new Subscription();
        subscription.setNumSub(15L);

        subscription.setStartDate(LocalDate.of(1990, 1, 1));
        subscription.setEndDate(LocalDate.of(1990, 1, 1));
        subscription.setPrice(15616F);
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        // Mock the repository's save method
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        Subscription result = subscriptionServices.addSubscription(subscription);

        // Verify that the save method was called once
        verify(subscriptionRepository, times(1)).save(subscription);

        // Verify that the result is not null
        Assertions.assertNotNull(result);

        // Verify that the result is the same as the input skier
        Assertions.assertEquals(subscription, result);
    }
    @Test
    public void retrieveSubscriptionByIdTest() {
        Long numSubscription = 1L; // Replace with a valid subscription ID
        Subscription subscription = new Subscription(1L,LocalDate.parse("2023/02/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),LocalDate.parse("2023/08/02",DateTimeFormatter.ofPattern("yyyy/MM/dd")),25F,TypeSubscription.MONTHLY);
        Mockito.when(subscriptionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(subscription));

        Subscription retrievedSubscription = subscriptionServices.retrieveSubscriptionById(numSubscription);

        Assertions.assertNotNull(retrievedSubscription);
        Assertions.assertEquals(subscription, retrievedSubscription);
    }



    @Test
    public void retrieveSubscriptionsByDatesTest() {
        LocalDate startDate = LocalDate.now(); // Replace with the desired start date
        LocalDate endDate = LocalDate.now().plusMonths(3); // Replace with the desired end date
        List<Subscription> subscriptions = new ArrayList<Subscription>(){{
            add(new Subscription(LocalDate.parse("2023/12/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),LocalDate.parse("2023/08/02",DateTimeFormatter.ofPattern("yyyy/MM/dd")),25F,TypeSubscription.SEMESTRIEL));
            add(new Subscription(LocalDate.parse("2023/02/02",DateTimeFormatter.ofPattern("yyyy/MM/dd")),LocalDate.parse("2023/08/02",DateTimeFormatter.ofPattern("yyyy/MM/dd")),25F,TypeSubscription.MONTHLY));
        }} ;
        Mockito.when(subscriptionRepository.getSubscriptionsByStartDateBetween(startDate, endDate)).thenReturn(subscriptions);

        List<Subscription> retrievedSubscriptions = subscriptionServices.retrieveSubscriptionsByDates(startDate, endDate);

        Assertions.assertNotNull(retrievedSubscriptions);
        Assertions.assertEquals(subscriptions, retrievedSubscriptions);
    }

    // You can similarly create tests for other methods like updateSubscription, retrieveSubscriptions, and showMonthlyRecurringRevenue.
}

    //

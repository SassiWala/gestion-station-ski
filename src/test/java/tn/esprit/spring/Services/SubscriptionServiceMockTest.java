package tn.esprit.spring.Services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SubscriptionServicesImpl;

import java.time.LocalDate;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

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
        assertNotNull(result);

        // Verify that the result is the same as the input skier
        assertEquals(subscription, result);
    }
    //
}
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
        assertNotNull(result);

        // Verify that the result is the same as the input skier
        assertEquals(subscription, result);
    }
@Test
    public void testUpdateSubscription() {
        // Créer un mock de la dépendance subscriptionRepository
        ISubscriptionRepository subscriptionRepository = mock(ISubscriptionRepository.class);


        // Créer un objet Subscription fictif pour le test
        Subscription subscription = new Subscription();

        // Configurer le comportement du mock
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        // Appeler la méthode à tester
        Subscription result = subscriptionServices.updateSubscription(subscription);

        // Vérifier que la méthode save du mock a été appelée avec les bons arguments
        verify(subscriptionRepository).save(subscription);

        // Vérifier que le résultat renvoyé par la méthode est correct
        assertEquals(subscription, result);
    }
}
    //

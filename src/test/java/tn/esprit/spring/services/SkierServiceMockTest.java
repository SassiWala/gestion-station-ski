package tn.esprit.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ISkierRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Slf4j
public class SkierServiceMockTest {
    @Mock
    ISkierRepository skierRepo;

    @InjectMocks
    SkierServicesImpl skierService;

    // Define test data
    Subscription s = Subscription.builder().price(200F).endDate(LocalDate.parse("2025-12-04"))
            .numSub(2L).typeSub(TypeSubscription.ANNUAL).startDate(LocalDate.parse("2024-12-04"))
            .build();
    Skier m = Skier.builder().numSkier(3L).dateOfBirth(LocalDate.ofEpochDay(1999 - 12 - 19))
            .city("Tunis").firstName("wassim").lastName("becheikh").subscription(s).build();
    List<Skier> list = new ArrayList<Skier>() {
        {
            add(Skier.builder().numSkier(2L).dateOfBirth(LocalDate.ofEpochDay(1999 - 11 - 19))
                    .city("Tunis").firstName("ahmed").lastName("Musa").build());
            add(Skier.builder().numSkier(75L).dateOfBirth(LocalDate.ofEpochDay(2000 - 10 - 18))
                    .city("Tunis").firstName("sadio").lastName("Mane").build());
        }
    };

    @Test
    public void addSkierTest() {
        // Define the behavior of the mock repository
        Mockito.when(skierRepo.save(Mockito.any(Skier.class))).then(invocation -> {
            Skier model = invocation.getArgument(0, Skier.class);
            model.setNumSkier(1L);
            return model;
        });

        log.info("Before ==> " + m.toString());
        Skier skier = skierService.addSkier(m);
        assertSame(skier, m);
        log.info("After ==> " + m.toString());
    }

    @Test
    public void retrieveSkierTest() {
        // Define the behavior of the mock repository
        Mockito.when(skierRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(m));

        Skier skier = skierService.retrieveSkier((long) 75);
        assertNotNull(skier);
        log.info("get ==> " + skier);

        // Verify that the repository method was called with the correct argument
        verify(skierRepo).findById(Mockito.anyLong());
    }

    @Test
    public void retrieveAllSkierTest() {
        // Define the behavior of the mock repository
        Mockito.when(skierRepo.findAll()).thenReturn(list);

        List<Skier> skiers = skierService.retrieveAllSkiers();
        assertNotNull(skiers);
    }
}

package tn.esprit.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ISkierRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Slf4j

public class SkierServiceMockTest  {
    @Mock
    ISkierRepository skierRepo;
    //ou ISkierRepository skierRepo = Mockito.mock(ISkierRepository.class);

    @InjectMocks
    SkierServicesImpl skierService;

    Subscription s=Subscription.builder().price(200F).endDate(LocalDate.parse("2025-12-04"))
            .numSub(2L).typeSub(TypeSubscription.ANNUAL).startDate(LocalDate.parse("2024-12-04"))
            .build();
    Skier m  = Skier.builder().numSkier(3L).dateOfBirth(LocalDate.ofEpochDay(1999-12-19))
            .city("Tunis").firstName("wael").lastName("hcine").subscription(s).build();
    List<Skier> list= new ArrayList<Skier>() {
        {
            add(Skier.builder().numSkier(2L).dateOfBirth(LocalDate.ofEpochDay(1999-11-19))
                    .city("Tunis").firstName("ahmed").lastName("hfaiedh").build());
            add(Skier.builder().numSkier(75L).dateOfBirth(LocalDate.ofEpochDay(2000-10-18))
                    .city("Tunis").firstName("saif").lastName("amara").build());
        }
    };

    @Test
    public void addSkierTest() {
        Mockito.when(skierRepo.save(Mockito.any(Skier.class))).then(invocation -> {
            Skier model = invocation.getArgument(0, Skier.class);
            model.setNumSkier(1L);
            return model;
        });
        log.info("Avant ==> " + m.toString());
        Skier skier = skierService.addSkier(m);
        assertSame(skier, m);
        log.info("Après ==> " + m.toString());
    }

    @Test
    public void retreiveSkierTest() {
        Mockito.when(skierRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(m));
        Skier skier = skierService.retrieveSkier((long) 75);
        assertNotNull(skier);
        log.info("get ==> " + skier.toString());
        verify(skierRepo).findById(Mockito.anyLong());

    }


}
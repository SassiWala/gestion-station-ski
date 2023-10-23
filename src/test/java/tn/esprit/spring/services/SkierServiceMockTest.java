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
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.repositories.ISkierRepository;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Slf4j
public class SkierServiceMockTest  {
    @Mock
    ISkierRepository skierRepository;
    @InjectMocks
    SkierServicesImpl skierServices;

    Set<Piste> pistes = new HashSet<>();
    Set<Registration> registrations = new HashSet<>();

    Skier skier = new Skier( 1L,"wael","hcine", LocalDate.of(2018, 5, 5),"ariana", pistes,registrations);
    List<Skier> skierList = new ArrayList<Skier>() {
        {
            add(new Skier(12L,"wael1","hcine1", LocalDate.of(2019, 6, 6),"ariana1", pistes,registrations));
            add(new Skier(13L,"wael2","hcine2", LocalDate.of(2022, 7, 7),"ariana2", pistes,registrations));
        }
    };
    @Test
    public void retrieveSkier (){
        Mockito.when(skierRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(skier));
        Skier skier1 = skierServices.retrieveSkier(Long.valueOf("1L"));
        Assertions.assertNotNull(skier1);
    }

}
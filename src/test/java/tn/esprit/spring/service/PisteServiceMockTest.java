package tn.esprit.spring.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Slf4j
public class PisteServiceMockTest {
    @Mock
    IPisteRepository pisteRepository;
    @InjectMocks
    PisteServicesImpl pisteServices;

    Piste piste = new Piste("Piste1", Color.BLACK,15,20,null);
    List<Piste> pistes = new ArrayList<Piste>(){
        {
            add(new Piste("Piste1", Color.GREEN,20,1,null));
            add(new Piste("Piste2", Color.RED,25,10,null));
        }
    };
    @Test
    public void retrieveUserTest(){
        Mockito.when(pisteRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(piste));
        Piste piste1 = pisteServices.retrievePiste(Long.valueOf(1));
        Assertions.assertEquals(piste1,piste);


    }
}

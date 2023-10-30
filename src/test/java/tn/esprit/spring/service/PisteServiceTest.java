package tn.esprit.spring.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.services.IPisteServices;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PisteServiceTest {

//    @Autowired
//    IPisteServices pisteServices;
//    @Test
//    @Order(1)
//    public void testRetrieveAllPistes(){
//        List<Piste> pistes = pisteServices.retrieveAllPistes();
//        Assertions.assertEquals(1,pistes.size());
//    }
}

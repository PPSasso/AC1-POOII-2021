package com.engSoft.ac2.event;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.engSoft.ac2.application.dtos.EventCreateDTO;
import com.engSoft.ac2.domain.model.Admin;
import com.engSoft.ac2.domain.model.Event;
import com.engSoft.ac2.domain.repositories.AdminRepository;
import com.engSoft.ac2.domain.repositories.EventRepository;
import com.engSoft.ac2.domain.services.EventService;

public class EventServiceTest {

    private EventService eventService;

    @Mock
    private EventRepository eventRepo;

    @Mock
    private AdminRepository adminRepo;

    @BeforeEach
    void setup() {

        eventService = new EventService();
        ReflectionTestUtils.setField(eventService, "eventRepo", eventRepo);
        ReflectionTestUtils.setField(eventService, "adminRepo", adminRepo);
    }

    @Test
    public void deveCriarUmEvento() {

        Admin admin = new Admin(1, "Thiago", "thi.sanches@hotmail.com", "15997445566");

        // Criando um Optional com o objeto Admin
        Optional<Admin> optAdmin = Optional.of(admin);

        // Configurando o comportamento simulado do eventRepo
        Mockito.when(adminRepo.findById(any())).thenReturn(optAdmin);

        Event event = new Event();
        event.setAdmin(admin);

        EventCreateDTO eventCreation = new EventCreateDTO();

        eventCreation.setName("Abril Fest");
        eventCreation.setDescription("descrition");
        eventCreation.setStartDate(LocalDate.of(2023, 06, 01));
        eventCreation.setEndDate(LocalDate.of(2023, 06, 02));
        eventCreation.setStartTime(LocalTime.now());
        eventCreation.setEndTime(LocalTime.now());
        eventCreation.setEmailContact("teste@hotmail.com");

        // Execução
        EventCreateDTO eventCreated = eventService.createEvent(eventCreation);

        // Verificação
        Assertions.assertEquals(eventCreation, eventCreated);
    }
}

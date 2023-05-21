package com.engSoft.ac2.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.engSoft.ac2.application.dtos.EventCreateDTO;
import com.engSoft.ac2.application.dtos.EventDTO;
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

    Event event;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(eventRepo);
        ReflectionTestUtils.setField(eventService, "adminRepo", adminRepo);

        EventCreateDTO eventCreation = new EventCreateDTO();

        eventCreation.setName("Abril Fest");
        eventCreation.setDescription("descrition");
        eventCreation.setStartDate(LocalDate.of(2023, 06, 01));
        eventCreation.setEndDate(LocalDate.of(2023, 06, 02));
        eventCreation.setStartTime(LocalTime.now());
        eventCreation.setEndTime(LocalTime.now());
        eventCreation.setEmailContact("teste@hotmail.com");

        Admin admin = new Admin(1, "Thiago", "thi.sanches@hotmail.com", "15997445566");

        this.event = new Event(eventCreation, admin);

    }

    @Test
    public void deveCriarUmEvento() {

        Admin admin = new Admin(1, "Thiago", "thi.sanches@hotmail.com", "15997445566");

        // Criando um Optional com o objeto Admin
        Optional<Admin> optAdmin = Optional.of(admin);

        // Configurando o comportamento simulado do eventRepo
        Mockito.when(adminRepo.findById(any())).thenReturn(optAdmin);

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
        EventDTO eventCreated = eventService.createEvent(eventCreation);

        // Verificação
        Assertions.assertEquals(event.getName(), eventCreated.getName());
        Assertions.assertEquals(event.getDescription(), eventCreated.getDescription());
    }

    @Test
    public void deveRetornarErroAoCriarEventoComDataFutura() {
        // Configuração
        EventCreateDTO eventCreation = new EventCreateDTO();
        eventCreation.setStartDate(LocalDate.of(2023, 06, 01));
        eventCreation.setEndDate(LocalDate.of(2023, 05, 30)); // Data final anterior à data de início

        // Execução e verificação
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            eventService.createEvent(eventCreation);
        });
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals("ERRO DE DATA: Verifique as datas de inicio e fim do evento.", exception.getReason());
    }

    @Test
    public void deveBuscarUmEventoPorId() {

        Mockito.when(eventRepo.findById(1L)).thenReturn(Optional.of(this.event));

        EventDTO returnedEvent = this.eventService.getEventById(1);
        assertEquals(new EventDTO(event), returnedEvent);
    }

}

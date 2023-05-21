package com.engSoft.ac2.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.engSoft.ac2.application.dtos.EventCreateDTO;
import com.engSoft.ac2.application.dtos.EventDTO;
import com.engSoft.ac2.application.dtos.PlaceCreationDTO;
import com.engSoft.ac2.domain.model.Admin;
import com.engSoft.ac2.domain.model.Event;
import com.engSoft.ac2.domain.model.Place;
import com.engSoft.ac2.domain.repositories.AdminRepository;
import com.engSoft.ac2.domain.repositories.EventRepository;
import com.engSoft.ac2.domain.repositories.PlaceRepository;
import com.engSoft.ac2.domain.services.EventService;
import com.engSoft.ac2.domain.services.PlaceService;

public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private PlaceService placeService;

    @Mock
    private EventRepository eventRepo;

    @Mock
    private AdminRepository adminRepo;

    @Mock
    private PlaceRepository placeRepo;

    Event event;

    Place place;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(eventRepo, placeRepo);
        placeService = new PlaceService(placeRepo);
        ReflectionTestUtils.setField(eventService, "adminRepo", adminRepo);
        ReflectionTestUtils.setField(eventService, "placeService", placeService);

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

        PlaceCreationDTO placeCreation = new PlaceCreationDTO();
        placeCreation.setName("Casa do Sasso");
        placeCreation.setAddress("Rua um numero dois");
        placeCreation.setIdEvent(1L);

        this.place = new Place(placeCreation);

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

    @Test
    void deveAssociarEventoAUmPlace() {

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        when(eventRepo.findAll()).thenReturn(eventList);
        when(eventRepo.findById(any(Long.class))).thenReturn(Optional.of(event));

        when(placeRepo.findById(any(Long.class))).thenReturn(Optional.of(place));
        place.addEvent(event);
        when(placeRepo.save(any(Place.class))).thenReturn(place);

        // Act
        Event result = eventService.associatePlaceToEvent(event.getId(), place.getId());

        // Assert
        // Verify that the place is associated with the event
        Assertions.assertTrue(result.getPlaces().contains(place));
        // Verify that the event is associated with the place
        Assertions.assertTrue(place.getEvents().contains(result));
    }

    @Test
    void deveVerificarErroDeAssociacaoDePlaceComEventoComDatasConflitantes() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);

        // Criar um evento existente com o mesmo horário e data
        Event existingEvent = new Event();
        existingEvent.setStartDate(event.getStartDate());
        existingEvent.setEndDate(event.getEndDate());
        existingEvent.setStartTime(event.getStartTime());
        existingEvent.setEndTime(event.getEndTime());
        existingEvent.addPlaces(place);
        eventList.add(existingEvent);

        when(eventRepo.findAll()).thenReturn(eventList);
        when(eventRepo.findById(anyLong())).thenReturn(Optional.of(event));
        when(placeRepo.findById(anyLong())).thenReturn(Optional.of(place));

        Executable executable = () -> eventService.associatePlaceToEvent(event.getId(), place.getId());
        assertThrows(ResponseStatusException.class, executable);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, executable);

        // Verificar a mensagem de erro
        String errorMessage = exception.getMessage();
        Assertions.assertEquals("400 BAD_REQUEST \"ERRO DE LOCAL: O local já possui um evento neste horário e data.\"",
                errorMessage);
    }

    @Test
    void deveRetornarTrueQuandoEventStartTimeSaoIguais() {
        Event event1 = new Event();
        event1.setStartTime(LocalTime.of(10, 0));

        Event event2 = new Event();
        event2.setStartTime(LocalTime.of(10, 0));

        boolean result = eventService.eventTimesIncompativel(event1, event2);

        Assertions.assertTrue(result);
    }

    @Test
    void deveRetornarTrueQuandoEventEndTimeSaoIguais() {
        Event event1 = new Event();
        event1.setStartTime(LocalTime.of(10, 0));
        event1.setEndTime(LocalTime.of(12, 0));

        Event event2 = new Event();
        event2.setStartTime(LocalTime.of(10, 0));
        event2.setEndTime(LocalTime.of(12, 0));

        boolean result = eventService.eventTimesIncompativel(event1, event2);

        Assertions.assertTrue(result);
    }

    @Test
    void deveRetornarTrueQuandoUmEventoComecaAntesDeOutroTerminar() {
        Event event1 = new Event();
        event1.setStartTime(LocalTime.of(10, 0));
        event1.setEndTime(LocalTime.of(12, 0));

        Event event2 = new Event();
        event2.setStartTime(LocalTime.of(11, 0));
        event2.setEndTime(LocalTime.of(13, 0));

        boolean result = eventService.eventTimesIncompativel(event1, event2);

        Assertions.assertTrue(result);
    }

    @Test
    void deveRetornarTrueQuandoUmEventoComecaAntesDeOutroTerminarInverso() {
        Event event1 = new Event();
        event1.setStartTime(LocalTime.of(10, 0));
        event1.setEndTime(LocalTime.of(12, 0));

        Event event2 = new Event();
        event2.setStartTime(LocalTime.of(9, 0));
        event2.setEndTime(LocalTime.of(11, 0));

        boolean result = eventService.eventTimesIncompativel(event1, event2);

        Assertions.assertTrue(result);
    }

    @Test
    void DeveRetornarFalseQuandoEventosSaoCompativeis() {
        Event event1 = new Event();
        event1.setStartTime(LocalTime.of(10, 0));
        event1.setEndTime(LocalTime.of(12, 0));

        Event event2 = new Event();
        event2.setStartTime(LocalTime.of(13, 0));
        event2.setEndTime(LocalTime.of(14, 0));

        boolean result = eventService.eventTimesIncompativel(event1, event2);

        Assertions.assertFalse(result);
    }

    @Test
    void deveRetornarTrueEmEventosDeMesmaDataDeInicio() {
        Event event1 = new Event();
        event1.setStartDate(LocalDate.of(2023, 5, 21));

        Event event2 = new Event();
        event2.setStartDate(LocalDate.of(2023, 5, 21));

        boolean result = eventService.eventDatesIncompativel(event1, event2);

        Assertions.assertTrue(result);
    }

    @Test
    void deveRetornarTrueQuandoADataDosEventosForemAsMesmas() {
        Event event1 = new Event();
        event1.setStartDate(LocalDate.of(2023, 5, 21));
        event1.setEndDate(LocalDate.of(2023, 5, 23));

        Event event2 = new Event();
        event2.setStartDate(LocalDate.of(2023, 5, 21));
        event2.setEndDate(LocalDate.of(2023, 5, 23));

        boolean result = eventService.eventDatesIncompativel(event1, event2);

        Assertions.assertTrue(result);
    }

    @Test
    void deveRetornarTrueQuandoADataDeFimDosEventosForAMesma() {
        Event event1 = new Event();
        event1.setStartDate(LocalDate.of(2023, 5, 21));
        event1.setEndDate(LocalDate.of(2023, 5, 23));

        Event event2 = new Event();
        event2.setStartDate(LocalDate.of(2023, 5, 20));
        event2.setEndDate(LocalDate.of(2023, 5, 23));

        boolean result = eventService.eventDatesIncompativel(event1, event2);

        Assertions.assertTrue(result);
    }

    @Test
    void DeveRetornarTrueQuandoDatasDeInicioConflotarem() {
        Event event1 = new Event();
        event1.setStartDate(LocalDate.of(2023, 5, 21));
        event1.setEndDate(LocalDate.of(2023, 5, 23));

        Event event2 = new Event();
        event2.setStartDate(LocalDate.of(2023, 5, 22));
        event2.setEndDate(LocalDate.of(2023, 5, 25));

        boolean result = eventService.eventDatesIncompativel(event1, event2);

        Assertions.assertTrue(result);
    }

    void DeveRetornarFalseQuandoEventosTiveremDatasCompativeis() {
        Event event1 = new Event();
        event1.setStartDate(LocalDate.of(2023, 5, 21));
        event1.setEndDate(LocalDate.of(2023, 5, 23));

        Event event2 = new Event();
        event2.setStartDate(LocalDate.of(2023, 5, 25));
        event2.setEndDate(LocalDate.of(2023, 5, 26));

        boolean result = eventService.eventDatesIncompativel(event1, event2);

        Assertions.assertFalse(result);
    }
}

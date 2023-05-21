package com.engSoft.ac2.event;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.engSoft.ac2.application.dtos.EventCreateDTO;
import com.engSoft.ac2.application.dtos.EventUpdateDTO;
import com.engSoft.ac2.domain.model.Admin;
import com.engSoft.ac2.domain.model.Event;
import com.engSoft.ac2.domain.repositories.AdminRepository;
import com.engSoft.ac2.domain.repositories.EventRepository;
import com.engSoft.ac2.domain.services.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class EventControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private EventService eventService;

  @Mock
  private EventRepository eventRepo;

  @Mock
  private AdminRepository adminRepo;

  @LocalServerPort
  private int port;

  Event event;

  EventCreateDTO eventCreation = new EventCreateDTO();

  EventUpdateDTO eventUpdate = new EventUpdateDTO();

  private String getRootUrl() {
    return "http://localhost:" + port;
  }

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    eventService = new EventService(eventRepo);
    ReflectionTestUtils.setField(eventService, "adminRepo", adminRepo);

    eventCreation.setName("Abril Fest");
    eventCreation.setDescription("descrition");
    eventCreation.setStartDate(LocalDate.of(2023, 06, 01));
    eventCreation.setEndDate(LocalDate.of(2023, 06, 02));
    eventCreation.setStartTime(LocalTime.now());
    eventCreation.setEndTime(LocalTime.now());
    eventCreation.setEmailContact("teste@hotmail.com");
    eventCreation.setAmountFreeTickets(10L);
    eventCreation.setAmountPaidTickets(20L);
    eventCreation.setTicketPrice(100.00);
    eventCreation.setAdminId(1L);

    Admin admin = new Admin(1, "Thiago", "thi.sanches@hotmail.com", "15997445566");

    this.event = new Event(eventCreation, admin);

  }

  @Test
  public void testCreateEvent() throws Exception {
    mockMvc.perform(post("/events")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(eventCreation)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value(eventCreation.getName()))
        .andExpect(jsonPath("$.description").value(eventCreation.getDescription()));
  }

  @Test
  public void testUpdateEvent() throws Exception {
    EventUpdateDTO eventUpdate = new EventUpdateDTO(event);
    eventUpdate.setName("Novo nome");
    eventUpdate.setDescription("Nova descrição");

    mockMvc.perform(put("/events/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(eventUpdate)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.emailContact").value(eventUpdate.getEmailContact()))
        .andExpect(jsonPath("$.description").value(eventUpdate.getDescription()));
  }

  @Test
  public void testDeleteEvent() throws Exception {
    mockMvc.perform(delete("/events/{id}", 1L))
        .andExpect(status().isNoContent());
  }

  @Test
  public void testGetEventById() throws Exception {
    mockMvc.perform(get("/events/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L));
  }

  @Test
  public void testGetEvents() throws Exception {
    mockMvc.perform(get("/events"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray());
  }

}

package com.engSoft.ac2.domain.services;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.engSoft.ac2.application.dtos.AdminDTO;
import com.engSoft.ac2.application.dtos.EventCreateDTO;
import com.engSoft.ac2.application.dtos.EventDTO;
import com.engSoft.ac2.application.dtos.EventUpdateDTO;
import com.engSoft.ac2.application.dtos.SuperDTO;
import com.engSoft.ac2.application.dtos.TicketDTO;
import com.engSoft.ac2.application.dtos.TicketListDTO;
import com.engSoft.ac2.application.factory.TicketFactory;
import com.engSoft.ac2.domain.model.Admin;
import com.engSoft.ac2.domain.model.Attend;
import com.engSoft.ac2.domain.model.Event;
import com.engSoft.ac2.domain.model.Place;
import com.engSoft.ac2.domain.model.Ticket;
import com.engSoft.ac2.domain.model.TypeTicket;
import com.engSoft.ac2.domain.repositories.AdminRepository;
import com.engSoft.ac2.domain.repositories.AttendRepository;
import com.engSoft.ac2.domain.repositories.EventRepository;
import com.engSoft.ac2.domain.repositories.PlaceRepository;
import com.engSoft.ac2.domain.repositories.TicketRepository;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepo;

    @Autowired
    PlaceRepository placeRepo;

    @Autowired
    AdminRepository adminRepo;

    @Autowired
    AttendRepository attendRepo;

    @Autowired
    TicketRepository ticketRepo;

    @Autowired
    PlaceService placeService;

    public EventService(EventRepository eventRepo2, PlaceRepository placeRepo) {
        this.eventRepo = eventRepo2;
        this.placeRepo = placeRepo;
    }

    public EventDTO createEvent(EventCreateDTO eventIn) {

        if (eventIn.getStartDate().isAfter(eventIn.getEndDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERRO DE DATA: Verifique as datas de inicio e fim do evento.");
        } else if (eventIn.getStartDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERRO DE DATA: Nao e possivel cadastrar um evento no passado.");
        } else {

            try {
                Admin admin = adminRepo.findById(eventIn.getAdminId()).get();

                Event event = new Event(eventIn, admin);

                eventIn.setId(event.getId());

                eventRepo.save(event);

                eventIn.setAdmin(new AdminDTO(admin));
                eventIn.setId(event.getId());

                return new EventDTO(event);

            } catch (NoSuchElementException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "ERRO DE ENTIDADE: Nao foi encontrado um admin com o ID informado.");
            }

        }

    }

    public Page<EventDTO> getEvents(Pageable pageRequest, String name, String startDate, String description) {
        LocalDate date = LocalDate.parse(startDate);
        Page<Event> pages = eventRepo.find(pageRequest, name, date, description);

        List<EventDTO> eventDtos = pages.stream()
                .map(event -> new EventDTO(event))
                .collect(Collectors.toList());

        Page<EventDTO> eventDTOPage = new PageImpl<>(eventDtos, pageRequest, pages.getTotalElements());

        return eventDTOPage;
    }

    public EventDTO updateEvent(EventUpdateDTO eventIn, long id) {
        try {
            Event event = eventRepo.findById(id).get();
            ;
            event.setDescription(eventIn.getDescription());
            event.setEmailContact(eventIn.getEmailContact());
            event.setStartDate(eventIn.getStartDate());
            event.setEndDate(eventIn.getEndDate());
            event.setStartTime(eventIn.getStartTime());
            event.setEndTime(eventIn.getEndTime());
            event.setTicketPrice(eventIn.getTicketPrice());

            try {
                event.setAdmin(adminRepo.findById(eventIn.getAdminId()).get());
            } catch (NoSuchElementException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "ERRO DE ENTIDADE: Nao foi encontrado um admin com o ID informado.");
            }

            if (event.getStartDate().isAfter(event.getEndDate())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "ERRO DE DATA: Verifique as datas de inicio e fim do evento.");
            } else if (event.getStartDate().isBefore(LocalDate.now())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "ERRO DE DATA: Nao e possivel alterar um evento no passado.");
            } else {
                eventRepo.save(event);
            }

            return new EventDTO(event);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public void deleteEvent(long id) {
        try {
            eventRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERRO DE ENTIDADE: A entidade Event nao foi encontrada.");
        }
    }

    public EventDTO getEventById(long id) {
        try {
            Event event = eventRepo.findById(id).get();

            return new EventDTO(event);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERRO DE ENTIDADE: A entidade Event nao foi encontrada.");
        }
    }

    public Event associatePlaceToEvent(Long idEvent, Long idPlace) {
        Event event = eventRepo.findById(idEvent)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com o ID: " + idEvent));
        Place place = new Place(placeService.getPlaceById(idPlace));

        validaEventPlaceConflito(event, place);

        event.addPlaces(place);
        place.addEvent(event);

        eventRepo.save(event);
        placeRepo.save(place);

        return event;
    }

    private void validaEventPlaceConflito(Event event, Place place) {
        for (Event e : eventRepo.findAll()) {
            if (eventDatesIncompativel(event, e) && eventTimesIncompativel(event, e)) {
                for (Place p : e.getPlaces()) {
                    if (p.equals(place)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "ERRO DE LOCAL: O local já possui um evento neste horário e data.");
                    }
                }
            }
        }
    }

    public boolean eventDatesIncompativel(Event event1, Event event2) {
        return event1.getStartDate().isEqual(event2.getStartDate())
                || event1.getStartDate().isEqual(event2.getEndDate())
                || event1.getEndDate().isEqual(event2.getStartDate())
                || event1.getEndDate().isEqual(event2.getEndDate())
                || (event1.getStartDate().isBefore(event2.getStartDate())
                        && event1.getEndDate().isAfter(event2.getStartDate()))
                || (event1.getStartDate().isAfter(event2.getStartDate())
                        && event1.getStartDate().isBefore(event2.getEndDate()))
                || (event1.getStartDate().isBefore(event2.getEndDate())
                        && event1.getEndDate().isAfter(event2.getEndDate()))
                || (event1.getStartDate().isBefore(event2.getStartDate())
                        && event1.getEndDate().isAfter(event2.getEndDate()));
    }

    public boolean eventTimesIncompativel(Event event1, Event event2) {
        return event1.getStartTime().compareTo(event2.getStartTime()) == 0
                || event1.getStartTime().compareTo(event2.getEndTime()) == 0
                || event1.getEndTime().compareTo(event2.getStartTime()) == 0
                || event1.getEndTime().compareTo(event2.getEndTime()) == 0
                || (event1.getStartTime().compareTo(event2.getStartTime()) > 0
                        && event1.getStartTime().compareTo(event2.getEndTime()) < 0)
                || (event1.getEndTime().compareTo(event2.getStartTime()) > 0
                        && event1.getEndTime().compareTo(event2.getEndTime()) < 0);
    }

    public void deletePlaceFromEvent(Long idEvent, Long idPlace) {
        Event event = eventRepo.findById(idEvent).get();

        placeService.getPlaceById(idPlace);

        List<Place> list = event.getPlaces();

        Boolean flag = true;

        if (LocalDate.now().isAfter(event.getEndDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERRO: Nao e possivel deletar um Place de um Event no passado.");
        }

        for (Place p : list) {
            if (p.getId() == idPlace) {
                event.getPlaces().remove(p);
                p.removeEvent(event);

                flag = false;

                placeRepo.save(p);

                break;
            }
        }

        if (flag) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERRO DE BUSCA: O PLACE informado nao se encontra na lista deste EVENT.");
        }

        eventRepo.save(event);
    }

    public Ticket buyTicketFromEvent(Long idEvent, TicketDTO tDto) {
        try {
            eventRepo.findById(idEvent).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERRO DE BUSCA: O Event informado nao foi encontrado.");
        }

        try {
            attendRepo.findById(tDto.getAttendId()).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERRO DE BUSCA: O Attendee informado nao foi encontrado.");
        }

        Attend attend = attendRepo.findById(tDto.getAttendId()).get();
        Event event = eventRepo.findById(idEvent).get();
        int paidTickets = 0;
        int freeTickets = 0;

        // Contagem de tickets pagos e gratuitos:
        for (Ticket t : event.getTickets()) {
            if (t.getType() == TypeTicket.PAID) {
                paidTickets = paidTickets + 1;
            } else {
                freeTickets = freeTickets + 1;
            }
        }

        if (event.getEndDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERRO DE REQUISICAO: Nao e possivel comprar um ticket de um evento passado.");
        }

        if (tDto.getTypeTicket().toLowerCase().equalsIgnoreCase("paid")) {
            tDto.setType(TypeTicket.PAID);
            if (paidTickets >= event.getAmountPaidTickets()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "ERRO DE REQUISICAO: Tickets pagos esgotados.");
            }
            tDto.setPrice(event.getTicketPrice());

        } else if (tDto.getTypeTicket().toLowerCase().equalsIgnoreCase("free")) {
            tDto.setType(TypeTicket.FREE);
            if (freeTickets >= event.getAmountPaidTickets()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "ERRO DE REQUISICAO: Tickets gratuitos esgotados.");
            }
            tDto.setPrice(0.0);

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERRO DE REQUISICAO: Tipo de Ticket invalido! Valores aceitos: PAID ou FREE");
        }

        tDto.setDate(Instant.now());

        Ticket ticket = TicketFactory.createTicket(tDto, attend, event);

        event.addTickets(ticket);
        attend.addTickets(ticket);

        ticketRepo.save(ticket);
        eventRepo.save(event);
        attendRepo.save(attend);

        return ticket;

    }

    public void ticketRefund(Long idEvent, Long idTicket) {
        Boolean found = false;

        try {
            for (Ticket t : eventRepo.findById(idEvent).get().getTickets()) {
                if (t.getId() == idTicket) {
                    found = true;
                }
            }
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERRO DE BUSCA: O Evento informado nao foi encontrado.");
        }

        try {
            ticketRepo.findById(idTicket).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERRO DE BUSCA: O Ticket informado nao foi encontrado.");
        }

        if (!found) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERRO DE BUSCA: O Ticket informado nao pertence a este Event.");
        }

        Ticket ticket = ticketRepo.findById(idTicket).get();

        Event event = ticket.getEvent();
        Attend attend = ticket.getAttend();

        if (LocalDate.now().isAfter(event.getStartDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERRO DE REQUISICAO: Nao e permitido o reembolso de Ticket em um evento em andamento ou passado.");
        }

        // Parte do Event
        event.refundTicket(ticket);

        // Parte do Attendee
        attend.refundTicket(ticket);
        if (ticket.getType() == TypeTicket.PAID) {
            attend.addBalance(ticket.getPrice());
        }

        // Parte do Ticket
        ticketRepo.delete(ticket);
    }

    public SuperDTO getEventTickets(Long idEvent) {
        try {
            Event event = eventRepo.findById(idEvent).get();
            List<TicketListDTO> listTicket = new ArrayList<>();
            long amPaidTickets = 0;
            long amFreeTickets = 0;

            for (Ticket t : event.getTickets()) {
                TicketListDTO dto = new TicketListDTO(t);
                listTicket.add(dto);
                if (t.getType() == TypeTicket.FREE) {
                    amFreeTickets = amFreeTickets + 1;
                } else {
                    amPaidTickets = amPaidTickets + 1;
                }
            }

            SuperDTO superDTO = new SuperDTO(
                    listTicket,
                    event.getAmountFreeTickets(),
                    event.getAmountPaidTickets(),
                    amFreeTickets,
                    amPaidTickets);

            return superDTO;

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERRO DE BUSCA: O Event informado nao foi encontrado.");
        }
    }

}

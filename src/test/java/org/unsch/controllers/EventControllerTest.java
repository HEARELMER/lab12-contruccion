package org.unsch.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.unsch.infrastructure.adapters.in.rest.dto.EventDTO;
import org.unsch.infrastructure.adapters.in.rest.dto.PartnerDTO;
import org.unsch.infrastructure.adapters.in.rest.dto.SubscribeDTO;
import org.unsch.domain.model.Customer;
import org.unsch.domain.model.Partner;
import org.unsch.infrastructure.adapters.out.persistence.repository.CustomerJpaRepository;
import org.unsch.infrastructure.adapters.out.persistence.repository.EventJpaRepository;
import org.unsch.infrastructure.adapters.out.persistence.repository.PartnerJpaRepository;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CustomerJpaRepository customerRepository;

    @Autowired
    private PartnerJpaRepository partnerRepository;

    @Autowired
    private EventJpaRepository eventRepository;

    private Customer johnDoe;
    private Partner disney;

    @BeforeEach
    void setUp() {
        johnDoe = customerRepository.save(new Customer(null, "John Doe", "123", "john@gmail.com"));
        disney = partnerRepository.save(new Partner(null, "Disney", "456", "disney@gmail.com"));
    }

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
        customerRepository.deleteAll();
        partnerRepository.deleteAll();
    }

    @Test
    @DisplayName("Debes crear un evento")
    public void testCreate() throws Exception {

        var event = new EventDTO();
        event.setDate("2021-01-01");
        event.setName("Disney on Ice");
        event.setTotalSpots(100);
        event.setPartner(new PartnerDTO(disney.getId()));

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.post("/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(event))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, EventDTO.class);
        Assertions.assertEquals(event.getDate(), actualResponse.getDate());
        Assertions.assertEquals(event.getTotalSpots(), actualResponse.getTotalSpots());
        Assertions.assertEquals(event.getName(), actualResponse.getName());
    }

    @Test
    @Transactional
    @DisplayName("Debes comprar un boleto para un evento.")
    public void testReserveTicket() throws Exception {

        var event = new EventDTO();
        event.setDate("2021-01-01");
        event.setName("Disney on Ice");
        event.setTotalSpots(100);
        event.setPartner(new PartnerDTO(disney.getId()));

        final var createResult = this.mvc.perform(
                        MockMvcRequestBuilders.post("/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(event))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var eventId = mapper.readValue(createResult, EventDTO.class).getId();

        var sub = new SubscribeDTO();
        sub.setCustomerId(johnDoe.getId());

        this.mvc.perform(
                        MockMvcRequestBuilders.post("/events/{id}/subscribe", eventId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(sub))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        var actualEvent = eventRepository.findById(eventId).get();
        Assertions.assertEquals(1, actualEvent.getTickets().size());
    }
}
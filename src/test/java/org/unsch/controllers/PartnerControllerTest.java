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
import org.unsch.dtos.PartnerDTO;
import org.unsch.repositories.PartnerRepository;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class PartnerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PartnerRepository partnerRepository;

    @AfterEach
    void tearDown() {
        partnerRepository.deleteAll();
    }

    @Test
    @DisplayName("Debe crear un socio")
    public void testCreate() throws Exception {

        var partner = new PartnerDTO();
        partner.setRuc("41536538000100");
        partner.setEmail("john.doe@gmail.com");
        partner.setName("John Doe");

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.post("/partners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(partner))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, PartnerDTO.class);
        Assertions.assertEquals(partner.getName(), actualResponse.getName());
        Assertions.assertEquals(partner.getRuc(), actualResponse.getRuc());
        Assertions.assertEquals(partner.getEmail(), actualResponse.getEmail());
    }

    @Test
    @DisplayName("Un socio no debe inscribirse con un RUC duplicado.")
    public void testCreateWithDuplicatedRUCShouldFail() throws Exception {

        var partner = new PartnerDTO();
        partner.setRuc("41536538000100");
        partner.setEmail("john.doe@gmail.com");
        partner.setName("John Doe");

        // Crear el primer socio.
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/partners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(partner))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        partner.setEmail("john2@gmail.com");

        // Intente crear un segundo socio con el mismo RUC
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/partners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(partner))
                )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("Partner already exists"));
    }

    @Test
    @DisplayName("No debe registrar un socio con una dirección de correo electrónico duplicada.")
    public void testCreateWithDuplicatedEmailShouldFail() throws Exception {

        var partner = new PartnerDTO();
        partner.setRuc("41536538000100");
        partner.setEmail("john.doe@gmail.com");
        partner.setName("John Doe");

        // Crear el primer socio.
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/partners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(partner))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        partner.setRuc("20538000100");

        // Intente crear un segundo socio con el mismo RUC
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/partners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(partner))
                )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("Partner already exists"));
    }

    @Test
    @DisplayName("Debes obtener un socio por identificación.")
    public void testGet() throws Exception {

        var partner = new PartnerDTO();
        partner.setRuc("20538000100");
        partner.setEmail("john.doe@gmail.com");
        partner.setName("John Doe");

        final var createResult = this.mvc.perform(
                        MockMvcRequestBuilders.post("/partners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(partner))
                )
                .andReturn().getResponse().getContentAsByteArray();

        var partnerId = mapper.readValue(createResult, PartnerDTO.class).getId();

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.get("/partners/{id}", partnerId)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, PartnerDTO.class);
        Assertions.assertEquals(partnerId, actualResponse.getId());
        Assertions.assertEquals(partner.getName(), actualResponse.getName());
        Assertions.assertEquals(partner.getRuc(), actualResponse.getRuc());
        Assertions.assertEquals(partner.getEmail(), actualResponse.getEmail());
    }
}
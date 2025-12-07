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
import org.unsch.dtos.CustomerDTO;
import org.unsch.repositories.CustomerRepository;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Debe crear un cliente")
    public void testCreate() throws Exception {

        var customer = new CustomerDTO();
        customer.setDni("56789014");
        customer.setEmail("john.doe@gmail.com");
        customer.setName("John Doe");

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, CustomerDTO.class);
        Assertions.assertEquals(customer.getName(), actualResponse.getName());
        Assertions.assertEquals(customer.getDni(), actualResponse.getDni());
        Assertions.assertEquals(customer.getEmail(), actualResponse.getEmail());
    }

    @Test
    @DisplayName("No debe registrar un cliente con un DNI duplicado.")
    public void testCreateWithDuplicatedDNIShouldFail() throws Exception {

        var customer = new CustomerDTO();
        customer.setDni("56789014");
        customer.setEmail("john.doe@gmail.com");
        customer.setName("John Doe");

        // Crea tu primer cliente.
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        customer.setEmail("john2@gmail.com");

        // Intente crear un segundo cliente con el mismo DNI
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("Customer already exists"));
    }

    @Test
    @DisplayName("No debe registrar un cliente con una dirección de correo electrónico duplicada.")
    public void testCreateWithDuplicatedEmailShouldFail() throws Exception {

        var customer = new CustomerDTO();
        customer.setDni("45618901");
        customer.setEmail("john.doe@gmail.com");
        customer.setName("John Doe");

        // Crea tu primer cliente.
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        customer.setDni("99918901");

        // Intente crear un segundo cliente con el mismo DNI.
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("Customer already exists"));
    }

    @Test
    @DisplayName("Debes obtener un cliente por identificador (id).")
    public void testGet() throws Exception {

        var customer = new CustomerDTO();
        customer.setDni("12345678901");
        customer.setEmail("john.doe@gmail.com");
        customer.setName("John Doe");

        final var createResult = this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andReturn().getResponse().getContentAsByteArray();

        var customerId = mapper.readValue(createResult, CustomerDTO.class).getId();

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.get("/customers/{id}", customerId)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, CustomerDTO.class);
        Assertions.assertEquals(customerId, actualResponse.getId());
        Assertions.assertEquals(customer.getName(), actualResponse.getName());
        Assertions.assertEquals(customer.getDni(), actualResponse.getDni());
        Assertions.assertEquals(customer.getEmail(), actualResponse.getEmail());
    }
}
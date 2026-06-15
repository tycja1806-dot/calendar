package org.example.taxcalendar;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.taxcalendar.client.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ClientTests {

  @Container
  @ServiceConnection
  static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17").withDatabaseName(
      "calendar").withUsername("root").withPassword("root");

  static {
    postgres.start();
  }

  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private MockMvc mockMvc;

  @AfterEach
  void tearDown() {
    clientRepository.deleteAll();
  }

  @Test
  void addClient_ShouldCreateClient() throws Exception {
    String json = """
        {
          "name": "test"
        }
        """;

    mockMvc.perform(
            MockMvcRequestBuilders.post("/api/clients").contentType("application/json").content(json))
        .andExpect(status().isCreated());
    Assertions.assertFalse(clientRepository.findAll().isEmpty());
  }

  @Test
  void addClient_NullName_ShouldReturnBadRequest() throws Exception {
    String json = """
        {
        }
        """;

    mockMvc.perform(
            MockMvcRequestBuilders.post("/api/clients").contentType("application/json").content(json))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Name cannot be null or empty."));
    Assertions.assertTrue(clientRepository.findAll().isEmpty());
  }

  @Test
  void addClient_BlankName_ShouldReturnBadRequest() throws Exception {
    String json = """
        {
          "name": " "
        }
        """;

    mockMvc.perform(
            MockMvcRequestBuilders.post("/api/clients").contentType("application/json").content(json))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Name cannot be null or empty."));
    Assertions.assertTrue(clientRepository.findAll().isEmpty());
  }
  @Test
  void addClient_shouldCreateClientWithTrimmedName() throws Exception {
    String json = """
        {
          "name": "     test        "
        }
        """;

    mockMvc.perform(
            MockMvcRequestBuilders.post("/api/clients").contentType("application/json").content(json))
        .andExpect(status().isCreated());
    Assertions.assertFalse(clientRepository.findAll().isEmpty());
    var addedClient = clientRepository.findAll().getFirst();
    Assertions.assertEquals("test", addedClient.getName());
  }
}

package org.example.taxcalendar;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;
import org.example.taxcalendar.client.Client;
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
        .andExpect(status().isBadRequest()).andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("Name cannot be null or empty."));
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
        .andExpect(status().isBadRequest()).andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("Name cannot be null or empty."));
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

  @Test
  void findAllClients_displayAllClients() throws Exception {
    clientRepository.save(getClient1());
    clientRepository.save(getClient2());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/clients")).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("test1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("test2"));

  }

  @Test
  void findAllClients_displayAllClients_EmptyList() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/clients")).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(0));

  }

  @Test
  void findClientId_displayClientById_shouldReturnClient() throws Exception {
    Client client = getClient1();
    client = clientRepository.save(client);
    clientRepository.save(getClient2());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/" + client.getId()))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(client.getName()));

  }

  @Test
  void findClientById_nonExistingClient_shouldReturnBadRequest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/9999"))
        .andExpect(status().isBadRequest()).andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("Client with id 9999 not found"));

  }

  @Test
  void updateClient_shouldUpdateClient() throws Exception {
    Client client = getClient1();
    client = clientRepository.save(client);
    String json = """
        {
          "name": "testUpdate",
          "dateDeactivated": "2026-01-01T01:00:00Z"
        }
        """;
    mockMvc.perform(MockMvcRequestBuilders.patch("/api/clients/" + client.getId())
            .contentType("application/json").content(json)).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testUpdate")).andExpect(
            MockMvcResultMatchers.jsonPath("$.dateDeactivated").value("2026-01-01"));
    Optional<Client> foundClient = clientRepository.findById(client.getId());
    Assertions.assertTrue(foundClient.isPresent());
    Assertions.assertEquals("testUpdate", foundClient.get().getName());
  }

  @Test
  void updateClient_shouldNotClient() throws Exception {

    String json = """
        {
          "name": "testUpdate",
          "dateDeactivated": "2026-01-01T01:00:00Z"
        }
        """;
    mockMvc.perform(
        MockMvcRequestBuilders.patch("/api/clients/9999").contentType("application/json")
            .content(json)).andExpect(status().isBadRequest()).andExpect(
        MockMvcResultMatchers.jsonPath("$.message").value("Client with id 9999 not found"));
  }

  private Client getClient1() {
    Client client = new Client();
    client.setName("test1");
    client.setCreationDate(LocalDate.parse("2025-01-01"));
    return client;
  }

  private Client getClient2() {
    Client client = new Client();
    client.setName("test2");
    client.setCreationDate(LocalDate.EPOCH);
    return client;
  }


}

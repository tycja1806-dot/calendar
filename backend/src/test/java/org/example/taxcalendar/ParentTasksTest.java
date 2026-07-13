package org.example.taxcalendar;


import static java.time.Instant.EPOCH;

import java.time.Instant;
import java.util.List;
import org.example.taxcalendar.client.Client;
import org.example.taxcalendar.client.ClientRepository;
import org.example.taxcalendar.parenttasks.ParentTask;
import org.example.taxcalendar.parenttasks.ParentTasksRepository;
import org.jetbrains.annotations.NotNull;
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
public class ParentTasksTest {

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
  private ParentTasksRepository parentTasksRepository;
  @Autowired
  private MockMvc mockMvc;

  @AfterEach
  void tearDown() {
    parentTasksRepository.deleteAll();
    clientRepository.deleteAll();
  }

  @Test
  void addParentTask_ShouldCreateParentTask() throws Exception {
    Client client = addClient();
    String parentJson = """
        {
          "name": "test",
          "dateStart": "2021-02-02T12:00:00Z",
          "taskFrequency": "MONTHLY",
          "reminderTimeDays": 1,
          "clientId": %d
        }
        """.formatted(client.getId());

    mockMvc.perform(
        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/parent")
            .contentType("application/json").content(parentJson)).andExpect(
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated());
    List<ParentTask> tasks = parentTasksRepository.findAll();
    Assertions.assertFalse(tasks.isEmpty());
    ParentTask parentTask = tasks.getFirst();
    Assertions.assertSame(parentTask.getClient().getId(), client.getId());
    Assertions.assertEquals("test", parentTask.getName());
    Assertions.assertEquals(TaskFrequency.MONTHLY, parentTask.getTaskFrequency());
    Assertions.assertEquals("2021-02-02T12:00:00Z", parentTask.getDateStart().toString());
    Assertions.assertEquals(1, parentTask.getReminderTimeDays());
  }

  @Test
  void getIdParentTask_ShouldFindParentTaskById() throws Exception {
    Client client = addClient();
    ParentTask parentTaskSave = new ParentTask();
    parentTaskSave.setName("test");
    parentTaskSave.setDateStart(Instant.EPOCH);
    parentTaskSave.setTaskFrequency(TaskFrequency.MONTHLY);
    parentTaskSave.setReminderTimeDays(1);
    client = clientRepository.save(client);
    parentTaskSave.setClient(client);
    parentTaskSave = parentTasksRepository.save(parentTaskSave);
    Long id = parentTaskSave.getId();
    mockMvc.perform(MockMvcRequestBuilders.get("/api/parent/" + id))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(parentTaskSave.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.dateStart")
            .value(parentTaskSave.getDateStart().toString())).andExpect(
            MockMvcResultMatchers.jsonPath("$.taskFrequency")
                .value(parentTaskSave.getTaskFrequency().toString())).andExpect(
            MockMvcResultMatchers.jsonPath("$.reminderTimeDays")
                .value(parentTaskSave.getReminderTimeDays()));
  }

  @Test
  void getAllParentTask_ShouldFindParentTasksAll() throws Exception {
    Client client = addClient();
    client = clientRepository.save(client);
    ParentTask parentTaskSave = getParentTask1(client);
    parentTaskSave = parentTasksRepository.save(parentTaskSave);
    ParentTask parentTaskSave2 = getParentTask2(client);
    parentTaskSave2 = parentTasksRepository.save(parentTaskSave2);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/parent"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));

  }

  private static @NotNull ParentTask getParentTask1(Client client) {
    ParentTask parentTaskSave = new ParentTask();
    parentTaskSave.setName("test");
    parentTaskSave.setDateStart(Instant.EPOCH);
    parentTaskSave.setTaskFrequency(TaskFrequency.MONTHLY);
    parentTaskSave.setReminderTimeDays(1);
    parentTaskSave.setClient(client);
    return parentTaskSave;
  }
  private static @NotNull ParentTask getParentTask2(Client client) {
    ParentTask parentTaskSave = new ParentTask();
    parentTaskSave.setName("test22");
    parentTaskSave.setDateStart(Instant.EPOCH);
    parentTaskSave.setTaskFrequency(TaskFrequency.YEARLY);
    parentTaskSave.setReminderTimeDays(15);
    parentTaskSave.setClient(client);
    return parentTaskSave;
  }

  private Client addClient() {
    Client client = new Client();
    client.setName("test");
    client.setCreationDate(EPOCH);
    return clientRepository.save(client);
  }
}

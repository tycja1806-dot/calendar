package org.example.taxcalendar;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.example.taxcalendar.client.Client;
import org.example.taxcalendar.client.ClientRepository;
import org.example.taxcalendar.parenttasks.ParentTask;
import org.example.taxcalendar.parenttasks.ParentTasksRepository;
import org.example.taxcalendar.parenttasks.TaskFrequency;
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

  private static @NotNull ParentTask getParentTask1(Client client) {
    ParentTask parentTaskSave = new ParentTask();
    parentTaskSave.setName("test");
    parentTaskSave.setDateStart(LocalDate.EPOCH);
    parentTaskSave.setTaskFrequency(TaskFrequency.MONTHLY);
    parentTaskSave.setReminderTimeDays(1);
    parentTaskSave.setClient(client);
    return parentTaskSave;
  }

  private static @NotNull ParentTask getParentTask2(Client client) {
    ParentTask parentTaskSave = new ParentTask();
    parentTaskSave.setName("test22");
    parentTaskSave.setDateStart(LocalDate.EPOCH);
    parentTaskSave.setTaskFrequency(TaskFrequency.YEARLY);
    parentTaskSave.setReminderTimeDays(15);
    parentTaskSave.setClient(client);
    return parentTaskSave;
  }

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
          "dateStart": "2021-02-02",
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
    Assertions.assertEquals("2021-02-02", parentTask.getDateStart().toString());
    Assertions.assertEquals(1, parentTask.getReminderTimeDays());
  }

  @Test
  void getIdParentTask_ShouldFindParentTaskById() throws Exception {
    Client client = addClient();
    ParentTask parentTaskSave = new ParentTask();
    parentTaskSave.setName("test");
    parentTaskSave.setDateStart(LocalDate.EPOCH);
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

  @Test
  void UpdateParentTask_ShouldUpdateParentTask() throws Exception {
    Client client = addClient();
    client = clientRepository.save(client);
    ParentTask parentTaskSave = getParentTask1(client);
    parentTaskSave = parentTasksRepository.save(parentTaskSave);
    mockMvc.perform(MockMvcRequestBuilders.patch("/api/parent/" + parentTaskSave.getId())
            .contentType("application/json")
            .content("""
                {
                  "name": "updatedName",
                  "dateStart": "2022-01-01",
                  "taskFrequency": "YEARLY",
                  "reminderTimeDays": 5
                }
                """))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updatedName"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.dateStart").value("2022-01-01"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.taskFrequency").value("YEARLY"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.reminderTimeDays").value(5));

    Optional<ParentTask> fromDb = parentTasksRepository.findById(parentTaskSave.getId());
    Assertions.assertFalse(fromDb.isEmpty());
    ParentTask taskFromDb = fromDb.get();
    Assertions.assertEquals("updatedName", taskFromDb.getName());
    Assertions.assertEquals(taskFromDb.getDateStart(), LocalDate.parse("2022-01-01"));
    Assertions.assertEquals(taskFromDb.getTaskFrequency(), TaskFrequency.YEARLY);
    Assertions.assertEquals(5, taskFromDb.getReminderTimeDays());
  }

  private Client addClient() {
    Client client = new Client();
    client.setName("test");
    client.setCreationDate(LocalDate.EPOCH);
    return clientRepository.save(client);
  }
}

package org.example.taxcalendar.parenttasks;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.taxcalendar.client.Client;
import org.example.taxcalendar.client.ClientRepository;
import org.example.taxcalendar.client.ClientService;
import org.example.taxcalendar.parenttasks.dto.ParentTaskRequest;
import org.example.taxcalendar.parenttasks.dto.ParentTaskRequestUpdate;
import org.example.taxcalendar.parenttasks.dto.ParentTaskResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for {@link ParentTask} operations.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParentTaskService {

  private final ClientRepository clientRepository;
  private final ParentTasksRepository parentTaskRepository;

  public static String generateExceptionMessage(long id) {
    return "Parent task with id %d not found".formatted(id);
  }

  /**
   * Method for creating new {@link ParentTask} with trimmed name to {@link ParentTasksRepository}.
   *
   * @param parentTaskRequest task request received from controller.
   * @return ParentTaskResponse that should be sent to client.
   */
  @Transactional
  public ParentTaskResponse createNewParentTask(ParentTaskRequest parentTaskRequest) {
    ParentTask parentTask = new ParentTask();
    Client client = clientRepository.findById(parentTaskRequest.clientId())
        .orElseThrow(() -> new IllegalArgumentException(
            ClientService.generateNotFoundMessage(parentTaskRequest.clientId())));
    parentTask.setName(parentTaskRequest.name());
    parentTask.setTaskFrequency(parentTaskRequest.taskFrequency());
    parentTask.setClient(client);
    parentTask.setDateStart(parentTaskRequest.dateStart());
    parentTask.setReminderTimeDays(parentTaskRequest.reminderTimeDays());
    parentTask = parentTaskRepository.save(parentTask);
    return mapToParentTaskResponse(parentTask);
  }

  /**
   * Method for searching {@link ParentTask} by id.
   *
   * @param id client request received from controller.
   */

  public ParentTaskResponse getParentTaskId(Long id) {
    ParentTask parentTask = parentTaskRepository.findById(id)
        .orElseThrow(
            () -> new IllegalArgumentException(generateExceptionMessage(id)));
    return mapToParentTaskResponse(parentTask);
  }

  public List<ParentTaskResponse> getParentTaskAll() {
    List<ParentTask> parentTask = parentTaskRepository.findAll().stream().toList();
    return parentTask.stream().map(ParentTaskService::mapToParentTaskResponse).toList();
  }

  /**
   * Method for mapping a {@link ParentTask} to a {@link ParentTaskResponse}.
   *
   * @param parentTask the parent task to map.
   * @return the mapped {@link ParentTaskResponse}.
   */
  public static ParentTaskResponse mapToParentTaskResponse(ParentTask parentTask) {
    return new ParentTaskResponse(
        parentTask.getId(),
        parentTask.getName(),
        parentTask.getDateStart(),
        parentTask.getDateDeactivated(),
        parentTask.getTaskFrequency(),
        parentTask.getReminderTimeDays(),
        ClientService.changeClientToClientResponse(parentTask.getClient())
    );
  }

  /**
   * Method for update {@link ParentTask} by id.
   *
   * @param parentTaskRequestUpdate update request for the parent task.
   * @param id                      the id of the parent task to update.
   * @return the updated parent task response.
   */
  @Transactional
  public ParentTaskResponse updateParentTask(ParentTaskRequestUpdate parentTaskRequestUpdate,
      Long id) {
    ParentTask parentTaskToUpdate = parentTaskRepository.findById(id)
        .orElseThrow(
            () -> new IllegalArgumentException(generateExceptionMessage(id)));
    String nameUpdate = parentTaskRequestUpdate.name();
    if (nameUpdate != null) {
      parentTaskToUpdate.setName(nameUpdate);
    }
    LocalDate dateStartUpdate = parentTaskRequestUpdate.dateStart();
    if (dateStartUpdate != null) {
      parentTaskToUpdate.setDateStart(dateStartUpdate);
    }
    LocalDate dateDeactivatedUpdate = parentTaskRequestUpdate.dateDeactivated();
    if (dateDeactivatedUpdate != null && dateDeactivatedUpdate.isAfter(
        parentTaskToUpdate.getDateStart())) {
      parentTaskToUpdate.setDateDeactivated(dateDeactivatedUpdate);
    }
    TaskFrequency taskFrequencyUpdate = parentTaskRequestUpdate.taskFrequency();
    if (taskFrequencyUpdate != null) {
      parentTaskToUpdate.setTaskFrequency(taskFrequencyUpdate);
    }
    Integer reminderTimeDaysUpdate = parentTaskRequestUpdate.reminderTimeDays();
    if (reminderTimeDaysUpdate != null) {
      parentTaskToUpdate.setReminderTimeDays(reminderTimeDaysUpdate);
    }
    parentTaskToUpdate = parentTaskRepository.save(parentTaskToUpdate);
    return mapToParentTaskResponse(parentTaskToUpdate);
  }
}

package org.example.taxcalendar.parenttasks;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.taxcalendar.client.Client;
import org.example.taxcalendar.client.ClientRepository;
import org.example.taxcalendar.client.ClientService;
import org.example.taxcalendar.parenttasks.dto.ParentTaskRequest;
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
            "Client with id " + parentTaskRequest.clientId() + " not found"));
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


  @Transactional
  public ParentTaskResponse getParentTaskId(Long id) {
    ParentTask parentTask = parentTaskRepository.findById(id)
        .orElseThrow(
            () -> new IllegalArgumentException("Parent task with id " + id + " not found"));
    return mapToParentTaskResponse(parentTask);
  }

  @Transactional
  public List<ParentTaskResponse> getParentTaskAll() {
    List<ParentTask> parentTask = parentTaskRepository.findAll().stream().toList();
    return parentTask.stream().map(this::mapToParentTaskResponse).toList();
  }

  private ParentTaskResponse mapToParentTaskResponse(ParentTask parentTask) {
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
}

package org.example.taxcalendar.parentTasks;

import lombok.RequiredArgsConstructor;
import org.example.taxcalendar.client.Client;
import org.example.taxcalendar.client.ClientRepository;
import org.example.taxcalendar.client.ClientService;
import org.example.taxcalendar.parentTasks.dto.ParentTaskRequest;
import org.example.taxcalendar.parentTasks.dto.ParentTaskResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParentTaskService {

  private final ClientRepository clientRepository;
  private final ParentTasksRepository parentTaskRepository;

  @Transactional
  public ParentTaskResponse createNewParentTask(ParentTaskRequest parentTaskRequest) {
    ParentTask parentTask = new ParentTask();
    Client client = clientRepository.findById(parentTaskRequest.clientId())
        .orElseThrow(() -> new IllegalArgumentException("Client with id " + parentTaskRequest.clientId() + " not found"));
    parentTask.setName(parentTaskRequest.name());
    parentTask.setTaskFreuquency(parentTaskRequest.taskFreuquency());
    parentTask.setClient(client);
    parentTask.setDateStart(parentTaskRequest.dateStart());
    parentTask.setReminderTimeDays(parentTaskRequest.reminderTimeDays());
    parentTask = parentTaskRepository.save(parentTask);
    return mapToParentTaskResponse(parentTask);
  }
  
  private ParentTaskResponse mapToParentTaskResponse(ParentTask parentTask) {
    return new ParentTaskResponse(
        parentTask.getId(),
        parentTask.getName(),
        parentTask.getDateStart(),
        parentTask.getDateDeactivated(),
        parentTask.getTaskFreuquency(),
        parentTask.getReminderTimeDays(),
        ClientService.changeClientToClientResponse(parentTask.getClient())
    );
  }
}

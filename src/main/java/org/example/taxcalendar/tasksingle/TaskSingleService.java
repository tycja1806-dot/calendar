package org.example.taxcalendar.tasksingle;


import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.taxcalendar.parenttasks.ParentTaskService;
import org.example.taxcalendar.tasksingle.dto.TaskSingleResponse;
import org.example.taxcalendar.tasksingle.dto.TaskSingleUpdate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskSingleService {

  private final ApplicationEventPublisher applicationEventPublisher;
  private final TaskSingleRepository taskSingleRepository;

  public static String generateExceptionMessage(Long id) {
    return "Single task with id %d not found ".formatted(id);
  }

  public static TaskSingleResponse changeTaskSingeToTaskSingleResponse(TaskSingle taskSingle) {
    return new TaskSingleResponse(taskSingle.getId(), taskSingle.getNameTask(),
        taskSingle.getDateCompletion(), taskSingle.getDeadline(), taskSingle.getTaskFrequency(),
        taskSingle.getDeactivationDate(),
        ParentTaskService.mapToParentTaskResponse(taskSingle.getParentTask()));
  }

  public TaskSingleResponse getSingleTask(Long id) {
    TaskSingle task = taskSingleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(generateExceptionMessage(id)));
    return changeTaskSingeToTaskSingleResponse(task);
  }

  public List<TaskSingleResponse> getTasksSingleResponsesAll() {
    List<TaskSingle> taskSingles = taskSingleRepository.findAll();
    return taskSingles.stream().map(TaskSingleService::changeTaskSingeToTaskSingleResponse)
        .toList();
  }
  @Transactional
  public TaskSingleResponse editTaskSingle(Long id, TaskSingleUpdate taskSingleUpdate) {
    TaskSingle taskToUpdate = taskSingleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(generateExceptionMessage(id)));

    String name = taskSingleUpdate.nameTask();
    if (name != null) {
      taskToUpdate.setNameTask(name);
    }
    LocalDate deadline = taskSingleUpdate.deadline();
    if (deadline != null) {
      taskToUpdate.setDeadline(deadline);
    }
    LocalDate dateCompletion = taskSingleUpdate.dateCompletion();
    if (dateCompletion != null) {
      taskToUpdate.setDateCompletion(dateCompletion);
    }
    taskToUpdate = taskSingleRepository.save(taskToUpdate);
    return changeTaskSingeToTaskSingleResponse(taskToUpdate);
  }
}

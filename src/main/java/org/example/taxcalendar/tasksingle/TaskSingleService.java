package org.example.taxcalendar.tasksingle;


import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.taxcalendar.parenttasks.ParentTaskService;
import org.example.taxcalendar.tasksingle.dto.TaskSingleResponse;
import org.example.taxcalendar.tasksingle.dto.TaskSingleUpdate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for {@link TaskSingle} operations.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskSingleService {

  private final TaskSingleRepository taskSingleRepository;

  /**
   * Generates an exception message for a task not found error.
   *
   * @param id the ID of the task that was not found
   * @return a formatted error message string
   */
  public static String generateExceptionMessage(Long id) {
    return "Single task with id %d not found ".formatted(id);
  }

  /**
   * Converts a TaskSingle entity to a TaskSingleResponse DTO.
   *
   * @param taskSingle the TaskSingle entity to convert
   * @return a TaskSingleResponse containing the task data
   */
  public static TaskSingleResponse changeTaskSingeToTaskSingleResponse(TaskSingle taskSingle) {
    return new TaskSingleResponse(taskSingle.getId(), taskSingle.getNameTask(),
        taskSingle.getDateCompletion(), taskSingle.getDeadline(), taskSingle.getTaskFrequency(),
        taskSingle.getDeactivationDate(),
        ParentTaskService.mapToParentTaskResponse(taskSingle.getParentTask()));
  }

  /**
   * Retrieves a single task by its ID.
   *
   * @param id the ID of the task to retrieve
   * @return a TaskSingleResponse containing the task data
   * @throws IllegalArgumentException if no task with the given ID is found
   */
  public TaskSingleResponse getSingleTask(Long id) {
    TaskSingle task = taskSingleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(generateExceptionMessage(id)));
    return changeTaskSingeToTaskSingleResponse(task);
  }

  /**
   * Retrieves all tasks.
   *
   * @return a list of TaskSingleResponse objects for all tasks
   */
  public List<TaskSingleResponse> getTasksSingleResponsesAll() {
    List<TaskSingle> taskSingles = taskSingleRepository.findAll();
    return taskSingles.stream().map(TaskSingleService::changeTaskSingeToTaskSingleResponse)
        .toList();
  }

  /**
   * Updates a task with new information.
   *
   * @param id               the ID of the task to update
   * @param taskSingleUpdate a DTO containing the updated task information (null fields are
   *                         ignored)
   * @return a TaskSingleResponse containing the updated task data
   * @throws IllegalArgumentException if no task with the given ID is found
   */
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

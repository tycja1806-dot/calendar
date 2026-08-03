package org.example.taxcalendar.tasksingle;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.taxcalendar.tasksingle.dto.TaskSingleResponse;
import org.example.taxcalendar.tasksingle.dto.TaskSingleUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for managing single tasks (liabilities).
 */
@RestController
@RequestMapping("/api/single")
@RequiredArgsConstructor
public class TaskSingleController {

  TaskSingleService taskSingleService;

  /**
   * Retrieves a single task by its ID.
   *
   * @param id the ID of the task to retrieve
   * @return a TaskSingleResponse containing the task data
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public TaskSingleResponse getSingleTask(@PathVariable Long id) {
    return taskSingleService.getSingleTask(id);
  }

  /**
   * Retrieves all single tasks.
   *
   * @return a list of TaskSingleResponse objects for all tasks
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<TaskSingleResponse> getTasksSingleResponseAll() {
    return taskSingleService.getTasksSingleResponsesAll();
  }

  /**
   * Updates a single task with new information.
   *
   * @param id the ID of the task to update
   * @param taskSingleUpdate a DTO containing the updated task information
   * @return a TaskSingleResponse containing the updated task data
   */
  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public TaskSingleResponse editTaskSingle(@PathVariable Long id,
      @Valid TaskSingleUpdate taskSingleUpdate) {
    return taskSingleService.editTaskSingle(id, taskSingleUpdate);
  }
}

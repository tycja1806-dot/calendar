package org.example.taxcalendar.parenttasks;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.taxcalendar.parenttasks.dto.ParentTaskRequest;
import org.example.taxcalendar.parenttasks.dto.ParentTaskResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for {@link ParentTask} operations.
 */
@RestController
@RequestMapping("/api/parent")
@RequiredArgsConstructor
public class ParentTaskController {

  private final ParentTaskService parentTaskService;

  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public ParentTaskResponse addParentTask(@RequestBody @Valid ParentTaskRequest parentTaskRequest) {
    return parentTaskService.createNewParentTask(parentTaskRequest);
  }


  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ParentTaskResponse getParentTaskById(@PathVariable Long id) {
    return parentTaskService.getParentTaskId(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ParentTaskResponse> getAllParentTasks() {
    return parentTaskService.getParentTaskAll();
  }
}
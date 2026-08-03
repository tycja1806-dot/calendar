package org.example.taxcalendar.tasksingle;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.taxcalendar.tasksingle.dto.TaskSingleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/single")
@RequiredArgsConstructor
public class TaskSingleController {

  TaskSingleService taskSingleService;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public TaskSingleResponse getSingleTask(@PathVariable Long id) {
    return taskSingleService.getSingleTask(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<TaskSingleResponse> getTasksSingleResponseAll() {
    return taskSingleService.getTasksSingleResponsesAll();
  }


}

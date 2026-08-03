package org.example.taxcalendar.tasksingle.dto;

import java.time.LocalDate;
import org.example.taxcalendar.parenttasks.TaskFrequency;
import org.example.taxcalendar.parenttasks.dto.ParentTaskResponse;

public record TaskSingleResponse(
    Long id,
    String nameTask,
    LocalDate dateCompletion,
    LocalDate deadline,
    TaskFrequency taskFrequency,
    LocalDate deactivationDate,
    ParentTaskResponse parentTask) {

}

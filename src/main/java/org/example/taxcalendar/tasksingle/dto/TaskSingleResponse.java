package org.example.taxcalendar.tasksingle.dto;

import java.time.LocalDate;
import org.example.taxcalendar.parenttasks.TaskFrequency;
import org.example.taxcalendar.parenttasks.dto.ParentTaskResponse;

/**
 * Response DTO for a single task.
 *
 * @param id               the unique identifier of the task
 * @param nameTask         the name or description of the task
 * @param dateCompletion   the date when the task was completed, or null if not yet completed
 * @param deadline         the deadline for completing the task
 * @param taskFrequency    the frequency of the task (e.g., monthly, quarterly, annually)
 * @param deactivationDate the date when the task was deactivated, or null if still active
 * @param parentTask       the parent task this single task belongs to
 */
public record TaskSingleResponse(
    Long id,
    String nameTask,
    LocalDate dateCompletion,
    LocalDate deadline,
    TaskFrequency taskFrequency,
    LocalDate deactivationDate,
    ParentTaskResponse parentTask) {

}

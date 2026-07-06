package org.example.taxcalendar.parentTasks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import org.example.taxcalendar.TaskFreuquency;

/**
 * Request for {@link org.example.taxcalendar.parentTasks.ParentTask} operations.
 */
public record ParentTaskRequest(@NotBlank(message = "Name cannot be null or empty.") String name,
                                @NotNull Instant dateStart, @NotNull TaskFreuquency taskFreuquency,
                                @NotNull Integer reminderTimeDays, @NotNull Long clientId) {

}

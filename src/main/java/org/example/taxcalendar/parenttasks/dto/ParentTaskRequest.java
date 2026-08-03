package org.example.taxcalendar.parenttasks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.example.taxcalendar.parenttasks.TaskFrequency;

/**
 * Request for {@link org.example.taxcalendar.parenttasks.ParentTask} operations.
 */
public record ParentTaskRequest(@NotBlank(message = "Name cannot be null or empty.") String name,
                                @NotNull LocalDate dateStart, @NotNull TaskFrequency taskFrequency,
                                @NotNull Integer reminderTimeDays, @NotNull Long clientId) {

}

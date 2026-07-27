package org.example.taxcalendar.parenttasks.dto;

import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import org.example.taxcalendar.parenttasks.TaskFrequency;

/**
 * Request for {@link org.example.taxcalendar.parenttasks.ParentTask} operations.
 */
public record ParentTaskRequestUpdate(@Size(min = 3) String name, LocalDate dateStart,
                                      LocalDate dateDeactivated,
                                      TaskFrequency taskFrequency, Integer reminderTimeDays) {

}

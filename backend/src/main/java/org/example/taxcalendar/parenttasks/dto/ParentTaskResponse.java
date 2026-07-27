package org.example.taxcalendar.parenttasks.dto;

import java.time.Instant;
import java.time.LocalDate;
import org.example.taxcalendar.client.dto.ClientResponse;
import org.example.taxcalendar.parenttasks.ParentTask;
import org.example.taxcalendar.parenttasks.TaskFrequency;

/**
 * Response for {@link ParentTask} operations.
 */
public record ParentTaskResponse(
    Long id,
    String name,
    LocalDate dateStart,
    LocalDate dateDeactivated,
    TaskFrequency taskFrequency,
    Integer reminderTimeDays,
    ClientResponse client
) {

}


package org.example.taxcalendar.parenttasks.dto;

import java.time.Instant;
import org.example.taxcalendar.TaskFrequency;
import org.example.taxcalendar.client.dto.ClientResponse;
import org.example.taxcalendar.parenttasks.ParentTask;

/**
 * Response for {@link ParentTask} operations.
 */
public record ParentTaskResponse(
    Long id,
    String name,
    Instant dateStart,
    Instant dateDeactivated,
    TaskFrequency taskFrequency,
    Integer reminderTimeDays,
    ClientResponse client
) {

}


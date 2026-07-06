package org.example.taxcalendar.parentTasks.dto;

import java.time.Instant;
import org.example.taxcalendar.TaskFreuquency;
import org.example.taxcalendar.client.dto.ClientResponse;

public record ParentTaskResponse(
    Long id,
    String name,
    Instant dateStart,
    Instant dateDeactivated,
    TaskFreuquency taskFreuquency,
    Integer reminderTimeDays,
    ClientResponse client
) {}


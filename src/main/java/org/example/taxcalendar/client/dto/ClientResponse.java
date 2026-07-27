package org.example.taxcalendar.client.dto;

import java.time.LocalDate;
import org.example.taxcalendar.client.Client;

/**
 * Response for {@link Client} operations.
 */
public record ClientResponse(Long id, String name, LocalDate dateActivated,
                             LocalDate dateDeactivated) {

}


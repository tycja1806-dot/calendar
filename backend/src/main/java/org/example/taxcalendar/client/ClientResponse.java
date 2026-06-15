package org.example.taxcalendar.client;

import java.time.Instant;

/**
 * Response for {@link Client} operations.
 */
public record ClientResponse(Long id, String name, Instant dateActivated, Instant dateDeactivated) {

}


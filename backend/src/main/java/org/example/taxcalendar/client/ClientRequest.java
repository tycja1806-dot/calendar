package org.example.taxcalendar.client;

import jakarta.validation.constraints.NotBlank;

/**
 * Request for {@link Client} operations.
 */

public record ClientRequest(@NotBlank(message = "Name cannot be null or empty.") String name) {

}

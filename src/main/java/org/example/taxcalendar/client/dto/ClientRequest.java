package org.example.taxcalendar.client.dto;

import jakarta.validation.constraints.NotBlank;
import org.example.taxcalendar.client.Client;

/**
 * Request for {@link Client} operations.
 */

public record ClientRequest(@NotBlank(message = "Name cannot be null or empty.") String name) {

}

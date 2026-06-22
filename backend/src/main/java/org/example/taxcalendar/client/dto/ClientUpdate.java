package org.example.taxcalendar.client.dto;

import java.time.Instant;
import org.example.taxcalendar.client.Client;


/**
 * Request for updating {@link Client} in the database.
 */
public record ClientUpdate(String name, Instant dateDeactivated) {

}

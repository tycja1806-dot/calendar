package org.example.taxcalendar.client.dto;

import java.time.LocalDate;
import org.example.taxcalendar.client.Client;


/**
 * Request for updating {@link Client} in the database.
 */
public record ClientUpdate(String name, LocalDate dateDeactivated) {

}

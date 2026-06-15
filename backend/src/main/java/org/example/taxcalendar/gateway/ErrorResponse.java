package org.example.taxcalendar.gateway;

import java.time.Instant;

/**
 * Response sent to client in case of an error in execution of request.
 */
public record ErrorResponse(Instant timestamp, Integer code, String message) {

}

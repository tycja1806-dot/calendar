package org.example.taxcalendar.gateway;

import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class for handling exceptions.
 */

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  /**
   * Method for handling {@link MethodArgumentNotValidException}.
   *
   * @param ex exception that has occurred.
   * @return {@link ErrorResponse} sent to client.
   */

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
    List<String> errors = new ArrayList<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      errors.add(error.getDefaultMessage());
    });

    return new ErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST.value(),
        String.join(" ", errors));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ErrorResponse handleEntityNotFoundException(EntityNotFoundException ex) {
    return new ErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
  }
}

package org.example.taxcalendar;

import org.springframework.boot.SpringApplication;

public class TestTaxCalendarApplication {

  public static void main(String[] args) {
    SpringApplication.from(TaxCalendarApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }

}

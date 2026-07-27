package org.example.taxcalendar.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.taxcalendar.parenttasks.ParentTask;

/**
 * This is model used to represent single client in the database. It contains list of
 * {@link ParentTask} which are assigned to client
 */
@Entity
@Table(name = "clients")
@NoArgsConstructor
@Getter
@Setter
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Column(name = "deactivation_date")
  private LocalDate dateDeactivated;
  @Column(name = "creation_date", nullable = false)
  private LocalDate creationDate;
}

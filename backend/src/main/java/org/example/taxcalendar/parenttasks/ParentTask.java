package org.example.taxcalendar.parenttasks;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.taxcalendar.client.Client;

/**
 * This is model used to represent parent task in the database.
 *
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "liabilities-of-clients")
public class ParentTask {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "start_date")
  private LocalDate dateStart;
  @Column(name = "deactivated_date")
  private LocalDate dateDeactivated;
  @Column(name = "frequency")
  private TaskFrequency taskFrequency;
  @Column(name = "reminder_time_days")
  private Integer reminderTimeDays;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id")
  private Client client;
}


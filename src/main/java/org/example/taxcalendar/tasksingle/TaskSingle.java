package org.example.taxcalendar.tasksingle;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.taxcalendar.parenttasks.ParentTask;
import org.example.taxcalendar.parenttasks.TaskFrequency;

/**
 * This is model used to represent single task in the database.
 */
@Entity
@NoArgsConstructor
@Table(name = "liabilities_single")
@Getter
@Setter
public class TaskSingle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nameTask;
  private LocalDate dateCompletion;
  private LocalDate deadline;
  private TaskFrequency taskFrequency;
  private LocalDate deactivationDate;
  @ManyToOne
  private ParentTask parentTask;

}

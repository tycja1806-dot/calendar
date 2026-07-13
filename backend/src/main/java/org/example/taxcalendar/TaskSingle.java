package org.example.taxcalendar;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Date;
import lombok.NoArgsConstructor;
import org.example.taxcalendar.parenttasks.ParentTask;
import org.springframework.data.annotation.Id;

/**
 * This is model used to represent single task in the database.
 *
 */
@NoArgsConstructor
@Table(name = "liabilities_single")
public class TaskSingle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nameTask;
  private Date dateCompletion;
  private Date deadline;
  private TaskFrequency taskFrequency;
  private Date deactivationDate;
  @ManyToOne
  private ParentTask parentTask;

}

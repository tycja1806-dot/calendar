import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Date;
@NoArgsConstructor
@Table(name = "liabilities_single")
public class TaskSingle {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nameTask;
  private Date date_completion;
  private Date deadline;
  private TaskFreuquency taskFreuquency;
  private Date deactivationDate;
  @ManyToOne
  private ParentTask parentTask;

}

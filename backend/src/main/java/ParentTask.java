import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Date;
import lombok.NoArgsConstructor;

/**
 * This is model used to represent parent task in the database.
 *
 */
@Entity
@NoArgsConstructor
public class ParentTask {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Date dateStart;
  private Date dateDeactivated;
  private TaskFreuquency taskFreuquency;
  private Integer reminderTimeDays;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id")
  private Client client;
}


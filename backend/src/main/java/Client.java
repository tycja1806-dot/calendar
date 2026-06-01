import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.List;
import lombok.NoArgsConstructor;


/**
 * This is model used to represent single client in the database. It contains list of
 * {@link ParentTask} which are assigned to client
 */
@Entity
@Table(name = "clients")
@NoArgsConstructor
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Date dateDeactivated;
  private Date dateActivated;
  @OneToMany(fetch = FetchType.EAGER)
  private List<ParentTask> parentTasks;
}

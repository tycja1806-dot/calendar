import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.util.List;

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

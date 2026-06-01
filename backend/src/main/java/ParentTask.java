import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Entity
@NoArgsConstructor
public class ParentTask {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Date dateStart;
  private Date dateDeactivated;
  private TaskFreuquency taskFreuquency;
  private Integer reminder_time_days;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id")
  private Client client;
}


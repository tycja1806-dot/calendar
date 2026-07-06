package org.example.taxcalendar.parentTasks;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link ParentTask} operations.
 */

public interface ParentTasksRepository extends JpaRepository<ParentTask, Long>{

}

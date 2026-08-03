package org.example.taxcalendar.tasksingle;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for accessing TaskSingle entities from the database.
 */
public interface TaskSingleRepository extends JpaRepository<TaskSingle, Long> {

}

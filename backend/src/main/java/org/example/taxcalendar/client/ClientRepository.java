package org.example.taxcalendar.client;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Client} operations.
 */

public interface ClientRepository extends JpaRepository<Client, Long> {

}

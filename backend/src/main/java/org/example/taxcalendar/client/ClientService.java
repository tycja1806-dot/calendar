package org.example.taxcalendar.client;

import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.taxcalendar.client.dto.ClientRequest;
import org.example.taxcalendar.client.dto.ClientResponse;
import org.springframework.stereotype.Service;

/**
 * Service for {@link Client} operations.
 */

@Service
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository clientRepository;

  /**
   * Method for adding {@link Client} with trimmed name to {@link ClientRepository}.
   *
   * @param clientRequest client request received from controller.
   * @return Client response that should be sent to client.
   */
  public ClientResponse addClient(ClientRequest clientRequest) {
    Client client = new Client();
    client.setName(clientRequest.name().trim());
    client.setCreationDate(Instant.now());
    client = clientRepository.save(client);
    return changeClientToClientResponse(client);
  }

  private ClientResponse changeClientToClientResponse(Client client) {
    return new ClientResponse(client.getId(), client.getName(), client.getCreationDate(),
        client.getDateDeactivated());
  }

  /**
   * Method for getting all clients.
   *
   * @return List of {@link ClientResponse} of all clients in database.
   */
  public List<ClientResponse> getClients() {
    return clientRepository.findAll().stream().map(this::changeClientToClientResponse).toList();

  }

  /**
   * Method for getting single client.
   *
   * @param id id of a client.
   * @return {@link ClientResponse} of client in database.
   * @throws EntityNotFoundException when client is not found.
   */
  public ClientResponse getClientsId(long id) {
    Client foundClient = clientRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Client with id " + id + " not found"));
    return changeClientToClientResponse(foundClient);
  }
}

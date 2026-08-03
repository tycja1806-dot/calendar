package org.example.taxcalendar.client;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.taxcalendar.client.dto.ClientRequest;
import org.example.taxcalendar.client.dto.ClientResponse;
import org.example.taxcalendar.client.dto.ClientUpdate;
import org.springframework.stereotype.Service;

/**
 * Service for {@link Client} operations.
 */

@Service
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository clientRepository;

  public static String generateNotFoundMessage(long id) {
    return "Client with id %d not found".formatted(id);
  }

  public static ClientResponse changeClientToClientResponse(Client client) {
    return new ClientResponse(client.getId(), client.getName(), client.getCreationDate(),
        client.getDateDeactivated());
  }

  /**
   * Method for adding {@link Client} with trimmed name to {@link ClientRepository}.
   *
   * @param clientRequest client request received from controller.
   * @return Client response that should be sent to client.
   */
  public ClientResponse addClient(ClientRequest clientRequest) {
    Client client = new Client();
    client.setName(clientRequest.name().trim());
    client.setCreationDate(LocalDate.now(ZoneId.of("UTC")));
    client = clientRepository.save(client);
    return changeClientToClientResponse(client);
  }

  /**
   * Method for getting all clients.
   *
   * @return List of {@link ClientResponse} of all clients in database.
   */
  public List<ClientResponse> getClients() {
    return clientRepository.findAll().stream().map(ClientService::changeClientToClientResponse)
        .toList();

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
        .orElseThrow(() -> new EntityNotFoundException(generateNotFoundMessage(id)));
    return changeClientToClientResponse(foundClient);
  }

  /**
   * Method for updating single client.
   *
   * @param id           id of a client.
   * @param clientUpdate {@link ClientUpdate} DTO from request
   * @return {@link ClientResponse} of updated client in database.
   * @throws EntityNotFoundException when client is not found.
   */
  public ClientResponse updateClient(Long id, ClientUpdate clientUpdate) {
    Client foundClient = clientRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(generateNotFoundMessage(id)));
    if (clientUpdate.name() != null && !clientUpdate.name().isEmpty()) {
      foundClient.setName(clientUpdate.name().trim());
    }
    if (clientUpdate.dateDeactivated() != null && clientUpdate.dateDeactivated()
        .isAfter(foundClient.getCreationDate())) {
      foundClient.setDateDeactivated(clientUpdate.dateDeactivated());
    }
    clientRepository.save(foundClient);
    return changeClientToClientResponse(foundClient);
  }
}

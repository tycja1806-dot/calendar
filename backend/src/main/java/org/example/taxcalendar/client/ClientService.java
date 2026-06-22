package org.example.taxcalendar.client;

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

  public List<ClientResponse> getClients() {
    return clientRepository.findAll().stream().map(this::changeClientToClientResponse).toList();

  }


}

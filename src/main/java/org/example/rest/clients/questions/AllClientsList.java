package org.example.rest.clients.questions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.example.domain.validatabledata.ClientDTO;

@Subject("User retrieves the list of all clients")
public class AllClientsList implements Question<List<ClientDTO>> {

  public static AllClientsList fromTheSystem() {
    return new AllClientsList();
  }

  @Override
  public List<ClientDTO> answeredBy(Actor actor) {
    List<Map<String, String>> list = LastResponse.received().answeredBy(actor).body()
        .jsonPath().getList("clients");
    List<ClientDTO> clientDTOList = list.stream().map(ClientDTO::byMetaData)
        .collect(Collectors.toList());
    return clientDTOList;
  }
}

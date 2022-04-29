package org.example.rest.clients.questions;

import java.util.Map;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.example.domain.validatabledata.ClientDTO;

@Subject("User retrieves a single client")
public class SingleClient implements Question<ClientDTO> {

  public static SingleClient fromTheSystem() {
    return new SingleClient();
  }

  @Override
  public ClientDTO answeredBy(Actor actor) {
    Map<String, String> dtoMap = LastResponse.received().answeredBy(actor).body().jsonPath()
        .getMap(".");
    return ClientDTO.byMetaData(dtoMap);
  }
}

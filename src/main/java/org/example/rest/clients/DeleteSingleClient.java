package org.example.rest.clients;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.EventualConsequence;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.serenitybdd.screenplay.rest.interactions.Put;
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence;
import net.thucydides.core.annotations.Step;
import org.apache.http.HttpStatus;
import org.example.domain.Endpoint;
import org.example.domain.validatabledata.ClientDTO;
import org.example.specification.Param;
import org.example.specification.ReusableSpec;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteSingleClient implements Interaction {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeleteSingleClient.class);
  private static final Endpoint ENDPOINT = Endpoint.CLIENT_ID;
  private final ClientDTO clientDTO;

  public DeleteSingleClient(ClientDTO clientDTO) {
    this.clientDTO = clientDTO;
  }

  public static Interaction inTheSystem(ClientDTO clientDTO) {
    return Instrumented.instanceOf(DeleteSingleClient.class)
        .withProperties(clientDTO);
  }

  @Step("{0} attempts to delete a client with")
  @Override
  public <T extends Actor> void performAs(T actor) {
    LOGGER.info("{} attempts to delete a client with id {}", actor.getName(), clientDTO.getId());
    actor.attemptsTo(
        Delete.from(ENDPOINT.getResource()).with(requestSpecification -> requestSpecification.spec(
            ReusableSpec.forActorWithApiKey(actor)
                .pathParam(Param.ID.getDesc(), clientDTO.getId()))));
    actor.should(EventualConsequence.eventually(
        ResponseConsequence.seeThatResponse(validatableResponse -> validatableResponse.statusCode(
            HttpStatus.SC_OK).body("message", Matchers.equalTo("client deleted")))));
  }
}

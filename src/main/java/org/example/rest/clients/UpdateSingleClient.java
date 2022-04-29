package org.example.rest.clients;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.rest.interactions.Put;
import net.thucydides.core.annotations.Step;
import org.example.domain.Endpoint;
import org.example.domain.validatabledata.ClientDTO;
import org.example.specification.Param;
import org.example.specification.ReusableConsequence;
import org.example.specification.ReusableSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateSingleClient implements Interaction {

  private static final Logger LOGGER = LoggerFactory.getLogger(UpdateSingleClient.class);
  private static final Endpoint ENDPOINT = Endpoint.CLIENT_ID;
  private final ClientDTO clientDTO;

  public UpdateSingleClient(ClientDTO clientDTO) {
    this.clientDTO = clientDTO;
  }

  public static Interaction inTheSystem(ClientDTO clientDTO) {
    return Instrumented.instanceOf(UpdateSingleClient.class)
        .withProperties(clientDTO);
  }

  @Step("{0} attempts to update the details of a client")
  @Override
  public <T extends Actor> void performAs(T actor) {
    LOGGER.info("{} attempts to update the details of a client with id ", actor.getName(),
        clientDTO.getId());
    actor.attemptsTo(
        Put.to(ENDPOINT.getResource()).with(requestSpecification -> requestSpecification.spec(
            ReusableSpec.forActorWithApiKey(actor).pathParam(Param.ID.getDesc(), clientDTO.getId())
                .body(clientDTO.getPayload()))));
    actor.should(ReusableConsequence.ASSERT_ALL_USER_FIELDS);
  }
}

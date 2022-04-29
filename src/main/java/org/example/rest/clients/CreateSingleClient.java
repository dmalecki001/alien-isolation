package org.example.rest.clients;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.core.annotations.Step;
import org.example.domain.Endpoint;
import org.example.domain.validatabledata.ClientDTO;
import org.example.specification.ReusableConsequence;
import org.example.specification.ReusableSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateSingleClient implements Interaction {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreateSingleClient.class);
  private static final Endpoint ENDPOINT = Endpoint.CLIENT;
  private final ClientDTO clientDTO;

  public CreateSingleClient(ClientDTO clientDTO) {
    this.clientDTO = clientDTO;
  }

  public static Interaction inTheSystem(ClientDTO clientDTO) {
    return Instrumented.instanceOf(CreateSingleClient.class)
        .withProperties(clientDTO);
  }

  @Step("{0} attempts to create a new client")
  @Override
  public <T extends Actor> void performAs(T actor) {
    LOGGER.info("{} attempts to create a new client", actor.getName());
    actor.attemptsTo(
        Post.to(ENDPOINT.getResource()).with(requestSpecification -> requestSpecification.spec(
            ReusableSpec.forActorWithApiKey(actor)
                .body(clientDTO.getPayload()))));
    actor.should(ReusableConsequence.ASSERT_ALL_USER_FIELDS);
  }
}

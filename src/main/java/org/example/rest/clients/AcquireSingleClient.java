package org.example.rest.clients;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.thucydides.core.annotations.Step;
import org.example.domain.Endpoint;
import org.example.specification.Param;
import org.example.specification.ReusableConsequence;
import org.example.specification.ReusableSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcquireSingleClient implements Interaction {

  private static final Logger LOGGER = LoggerFactory.getLogger(AcquireSingleClient.class);
  private static final Endpoint ENDPOINT = Endpoint.CLIENT_ID;
  private final String clientId;

  public AcquireSingleClient(String clientId) {
    this.clientId = clientId;
  }

  public static Interaction fromTheSystem(String clientId) {
    return Instrumented.instanceOf(AcquireSingleClient.class).withProperties(clientId);
  }

  @Step("{0} attempts to acquire single client with")
  @Override
  public <T extends Actor> void performAs(T actor) {
    LOGGER.info("{} attempts to acquire single client with id {}", actor.getName(), clientId);
    actor.attemptsTo(
        Get.resource(ENDPOINT.getResource()).with(requestSpecification -> requestSpecification.spec(
            ReusableSpec.forActorWithApiKey(actor).pathParam(Param.ID.getDesc(), clientId))));
    actor.should(ReusableConsequence.ASSERT_ALL_USER_FIELDS);
  }
}

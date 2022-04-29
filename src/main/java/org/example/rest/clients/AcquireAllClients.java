package org.example.rest.clients;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.EventualConsequence;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence;
import net.thucydides.core.annotations.Step;
import org.apache.http.HttpStatus;
import org.example.domain.Endpoint;
import org.example.specification.ReusableSpec;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcquireAllClients implements Interaction {

  private static final Logger LOGGER = LoggerFactory.getLogger(AcquireAllClients.class);
  private static final Endpoint ENDPOINT = Endpoint.CLIENTS;

  public static Performable fromTheSystem() {
    return Instrumented.instanceOf(AcquireAllClients.class).newInstance();
  }

  @Step("{0} attempts to acquire a list of all clients")
  @Override
  public <T extends Actor> void performAs(T actor) {
    LOGGER.info("{} attempts to acquire a list of all clients", actor.getName());
    actor.attemptsTo(Get.resource(ENDPOINT.getResource())
        .with(requestSpecification -> requestSpecification.spec(
            ReusableSpec.forActorWithApiKey(actor))));
    actor.should(EventualConsequence.eventually(
        ResponseConsequence.seeThatResponse(validatableResponse -> validatableResponse.statusCode(
            HttpStatus.SC_OK).body(".", Matchers.is(Matchers.not(Matchers.empty()))))));
  }


}

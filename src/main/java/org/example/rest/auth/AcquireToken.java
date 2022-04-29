package org.example.rest.auth;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.EventualConsequence;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence;
import org.apache.http.HttpStatus;
import org.example.domain.ActorMemo;
import org.example.domain.Endpoint;
import org.example.specification.ReusableSpec;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcquireToken implements Interaction {

  private static final Logger LOGGER = LoggerFactory.getLogger(AcquireToken.class);
  private static final Endpoint ENDPOINT = Endpoint.TOKEN;

  public static Interaction fromTheSystem() {
    return Instrumented.instanceOf(AcquireToken.class).newInstance();
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    LOGGER.info("Attempting to acquire token");
    sendTokenRequestFor(actor);
    validateResponse(actor);
    String token = actor.asksFor(extractedToken());
    actor.remember(ActorMemo.TOKEN, token);
  }

  private <T extends Actor> void validateResponse(T actor) {
    actor.should(EventualConsequence.eventually(
        ResponseConsequence.seeThatResponse(validatableResponse -> validatableResponse.statusCode(
                HttpStatus.SC_OK)
            .body("key", Matchers.is(Matchers.not(Matchers.emptyString()))))));
  }

  private <T extends Actor> void sendTokenRequestFor(T actor) {
    actor.attemptsTo(Post.to(ENDPOINT.getResource())
        .with(requestSpecification -> requestSpecification.spec(
            ReusableSpec.forActorWithBasicAuth())));
  }

  private Question<String> extractedToken() {
    return Question.about("Api key extracted").answeredBy(
        actor -> LastResponse.received().answeredBy(actor).body().jsonPath().getString("key"));
  }

}

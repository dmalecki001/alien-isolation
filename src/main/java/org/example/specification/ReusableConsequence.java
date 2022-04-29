package org.example.specification;

import io.restassured.response.Response;
import net.serenitybdd.screenplay.EventualConsequence;
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence;
import org.apache.http.HttpStatus;

public class ReusableConsequence {

  public static final EventualConsequence<Response> ASSERT_ALL_USER_FIELDS = EventualConsequence.eventually(
      ResponseConsequence.seeThatResponse(validatableResponse -> validatableResponse.statusCode(
              HttpStatus.SC_OK).body("firstName", ReusableMatchers.IS_STRING_BLANK_OR_NULL)
          .body("lastName", ReusableMatchers.IS_STRING_BLANK_OR_NULL)
          .body("phone", ReusableMatchers.IS_STRING_BLANK_OR_NULL)
          .body("id", ReusableMatchers.IS_STRING_BLANK_OR_NULL)));

}

package org.example.specification;

import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.Random;
import net.serenitybdd.screenplay.Actor;
import org.example.domain.ActorMemo;
import org.example.domain.DomainProp;
import org.example.domain.EnvironmentConfig;

public class ReusableSpec {

  private static final List<Filter> FILTERS = List.of(new RequestLoggingFilter(),
      new ResponseLoggingFilter());
  private static final RequestSpecBuilder builder = new RequestSpecBuilder();
  private static final boolean LOG_ALL = Boolean.parseBoolean(
      EnvironmentConfig.getInstance().fetchProperty(DomainProp.LOG_ALL));
  private static final PreemptiveBasicAuthScheme BASIC_AUTH;
  private static final String HEADER_NAME = "X-API-KEY";

  static {
    BASIC_AUTH = new PreemptiveBasicAuthScheme();
    BASIC_AUTH.setUserName(EnvironmentConfig.getInstance().fetchProperty(DomainProp.USERNAME));
    BASIC_AUTH.setPassword(EnvironmentConfig.getInstance().fetchProperty(DomainProp.PASSWORD));
  }

  public static <T extends Actor> RequestSpecification forActorWithApiKey(T actor) {
    return forActor()
        .addHeader(HEADER_NAME, actor.recall(ActorMemo.TOKEN))
     //   .setConfig(RestAssuredConfig.config()
      //      .logConfig(LogConfig.logConfig().blacklistHeader(HEADER_NAME)))
        .build();
  }

  public static RequestSpecification forActorWithBasicAuth() {
    return forActor().setAuth(BASIC_AUTH).build();
  }

  public static RequestSpecBuilder forActor() {
    RequestSpecBuilder builder = new RequestSpecBuilder();
    if (LOG_ALL) {
      builder.addFilters(FILTERS);
    }
    builder.setContentType(ContentType.JSON);
    return builder;
  }

}

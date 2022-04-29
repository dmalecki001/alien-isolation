package org.example;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.annotations.CastMember;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.example.domain.DomainProp;
import org.example.domain.EnvironmentConfig;
import org.example.domain.validatabledata.ClientDTO;
import org.example.rest.auth.AcquireToken;
import org.example.rest.clients.CreateSingleClient;
import org.example.rest.clients.DeleteSingleClient;
import org.example.rest.clients.questions.SingleClient;
import org.example.specification.JsonPayload;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

@Tag("component")
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SerenityJUnit5Extension.class)
public class DeleteSingleClientTest {

  @CastMember(name = "Damian")
  private Actor damian;
  private final ClientDTO clientDTO = ClientDTO.newClientDto();

  @BeforeAll
  public void setup() {
    damian.whoCan(CallAnApi.at(EnvironmentConfig.getInstance().fetchProperty(DomainProp.BASE_URL)));
  }

  @Test
  public void delete_single_client() {
    damian.attemptsTo(AcquireToken.fromTheSystem());
    damian.attemptsTo(CreateSingleClient.inTheSystem(clientDTO));
    ClientDTO newClient = damian.asksFor(SingleClient.fromTheSystem());
    damian.attemptsTo(DeleteSingleClient.inTheSystem(newClient));
  }

}

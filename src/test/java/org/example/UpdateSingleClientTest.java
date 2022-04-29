package org.example;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.annotations.CastMember;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.example.domain.DomainProp;
import org.example.domain.EnvironmentConfig;
import org.example.domain.validatabledata.ClientDTO;
import org.example.rest.auth.AcquireToken;
import org.example.rest.clients.AcquireSingleClient;
import org.example.rest.clients.questions.SingleClient;
import org.example.rest.clients.UpdateSingleClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

@Tag("component")
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SerenityJUnit5Extension.class)
public class UpdateSingleClientTest {

  @CastMember(name = "Damian")
  private Actor damian;
  private String dummyUserId = EnvironmentConfig.getInstance().fetchProperty(DomainProp.DUMMY_ID);

  @BeforeAll
  public void setup() {
    damian.whoCan(CallAnApi.at(EnvironmentConfig.getInstance().fetchProperty(DomainProp.BASE_URL)));
  }

  @Test
  public void update_single_client() {
    damian.attemptsTo(AcquireToken.fromTheSystem());
    damian.attemptsTo(AcquireSingleClient.fromTheSystem(dummyUserId));
    ClientDTO extractedClientDto = damian.asksFor(SingleClient.fromTheSystem());
    ClientDTO clientWithNewData = validatableClientDtoWithNewDetails(extractedClientDto);
    damian.attemptsTo(UpdateSingleClient.inTheSystem(clientWithNewData));
    damian.attemptsTo(AcquireSingleClient.fromTheSystem(clientWithNewData.getId()));
    ClientDTO updatedClient = damian.asksFor(SingleClient.fromTheSystem());
    damian.attemptsTo(
        Ensure.that(clientWithNewData).isEqualTo(updatedClient));
  }

  private ClientDTO validatableClientDtoWithNewDetails(ClientDTO clientDTO) {
    return ClientDTO.builder().with(builder -> {
      builder.firstName = clientDTO.getFirstName();
      builder.lastName = clientDTO.getLastName();
      builder.phone = "+48 111 111 " + partialPhoneNumber();
      builder.id = clientDTO.getId();
    }).build();
  }

  private String partialPhoneNumber() {
    StringBuilder builder = new StringBuilder();
    return builder.append(getRandomNumber()).append(getRandomNumber()).append(getRandomNumber())
        .toString();
  }

  private int getRandomNumber() {
    return (int) ((Math.random() * (9 - 1)) + 1);
  }

}

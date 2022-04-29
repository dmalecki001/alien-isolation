package org.example.domain.validatabledata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import org.example.specification.JsonPayload;
import org.jetbrains.annotations.NotNull;

public class ClientDTO implements Comparable<ClientDTO>, JsonPayload {

  @SerializedName("firstName")
  @Expose
  private final String firstName;
  @SerializedName("lastName")
  @Expose
  private final String lastName;
  @SerializedName("phone")
  @Expose
  private final String phone;
  private final String id;

  public ClientDTO(String firstName, String lastName, String phone, String id) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhone() {
    return phone;
  }

  public String getId() {
    return id;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ClientDTO byMetaData(Map<String,String> map){
         return ClientDTO.builder().with(builder -> {
      builder.firstName = map.get("firstName");
      builder.lastName = map.get("lastName");
      builder.phone = map.get("phone");
      builder.id = map.get("id");
    }).build();
  }

  public static ClientDTO validatableClient() {
    return ClientDTO.builder().with(builder -> {
      builder.firstName = "Piotr";
      builder.lastName = "Bigos";
      builder.phone = "+48 800 190 590";
      builder.id = "2a7906e00f1b";
    }).build();
  }

  public static ClientDTO newClientDto() {
    return ClientDTO.builder().with(builder -> {
      builder.firstName = "Amanda";
      builder.lastName = "Ripley";
      builder.phone = "+48 500 190 590";
    }).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientDTO clientDTO = (ClientDTO) o;
    return Objects.equals(firstName, clientDTO.firstName) && Objects.equals(
        lastName, clientDTO.lastName) && Objects.equals(phone, clientDTO.phone)
        && Objects.equals(id, clientDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, phone, id);
  }

  @Override
  public int compareTo(@NotNull ClientDTO o) {
    return Integer.compare(this.hashCode(), o.hashCode());
  }

  @Override
  public String getPayload() {
    return convert(this);
  }

  public static final class Builder {

    public String firstName;
    public String lastName;
    public String phone;
    public String id;

    public Builder with(Consumer<Builder> consumer) {
      consumer.accept(this);
      return this;
    }

    public ClientDTO build() {
      return new ClientDTO(firstName, lastName, phone, id);
    }

  }

}

package org.example.domain;

public enum Endpoint {

  TOKEN("/token"),
  CLIENTS("/clients"),
  CLIENT("/client"),
  CLIENT_ID("/client/{id}");

  private final String resource;

  Endpoint(String resource) {
    this.resource = resource;
  }

  public String getResource() {
    return resource;
  }
}

package org.example.domain;

public enum DomainProp {

  BASE_URL("base.url"),
  LOG_ALL("log.all"),
  USERNAME("username"),
  PASSWORD("password"),
  DUMMY_ID("dummy.user");

  private final String prop;

  DomainProp(String prop) {
    this.prop = prop;
  }

  public String getProp() {
    return prop;
  }
}

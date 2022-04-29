package org.example.specification;

public enum Param {

  ID("id");

  private final String desc;

  Param(String desc) {
    this.desc = desc;
  }

  public String getDesc() {
    return desc;
  }

}

package org.example.specification;

import java.util.function.Supplier;

public enum Param implements Supplier<String> {

  ID("id");

  private final String desc;

  Param(String desc) {
    this.desc = desc;
  }

  public String getDesc() {
    return desc;
  }

  @Override
  public String get() {
    return desc;
  }
}

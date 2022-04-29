package org.example.specification;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class ReusableMatchers {

  public static final Matcher<String> IS_STRING_BLANK_OR_NULL = Matchers.either(
          Matchers.blankOrNullString())
      .or(Matchers.is(Matchers.not(Matchers.blankOrNullString())));

}

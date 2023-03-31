package org.daemio.merch;

import io.cucumber.java.ParameterType;
import org.springframework.data.domain.Sort.Direction;

public class ParameterTypes {

  @ParameterType("ascending|descending")
  public Direction order(String order) {
    return Direction.DESC;
  }
}

package org.daemio.merch.data;

import io.cucumber.spring.ScenarioScope;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class ScenarioData {

  private static final int START = 0;
  private static final int GIVEN = 1;
  private static final int WHEN = 2;
  private static final int THEN = 3;

  private RequestSpecification requestSpecification;
  private Response response;
  private ValidatableResponse validatableResponse;

  private int state = 0;

  public RequestSpecification given() {
    if (state != START && state != GIVEN) {
      throw new IllegalStateException(
          String.format("Must be in the START/GIVEN state to call, current state %d", state));
    }

    if (state == START) {
      requestSpecification = RestAssured.given();
      state = GIVEN;
    }

    return requestSpecification;
  }

  public Response get(String uri) {
    if (state != GIVEN) {
      throw new IllegalStateException(
          String.format("Must be in the GIVEN state to call, current state %d", state));
    }

    state = WHEN;
    response = requestSpecification.get(uri);
    return response;
  }

  public Response post(String uri) {
    if (state != GIVEN) {
      throw new IllegalStateException(
          String.format("Must be in the GIVEN state to call, current state %d", state));
    }

    state = WHEN;
    response = requestSpecification.post(uri);
    return response;
  }

  public Response patch(String uri) {
    if (state != GIVEN) {
      throw new IllegalStateException(
          String.format("Must be in the GIVEN state to call, current state %d", state));
    }

    state = WHEN;
    response = requestSpecification.patch(uri);
    return response;
  }

  public ValidatableResponse then() {
    if (state != WHEN && state != THEN) {
      throw new IllegalStateException(
          String.format("Must be in the WHEN/THEN state to call, current state %d", state));
    }

    if (state == WHEN) {
      validatableResponse = response.then();
      state = THEN;
    }
    return validatableResponse;
  }

  public void reset() {
    state = START;
    requestSpecification = null;
    response = null;
    validatableResponse = null;
  }
}

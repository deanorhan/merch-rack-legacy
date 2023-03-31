package org.daemio.merch.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.daemio.merch.data.ScenarioData;

@DisplayName("Steps common to various scenarios")
public final class CommonSteps {

  @Autowired private ScenarioData scenarioData;

  @Given("the request content type is {string}")
  public void the_request_content_type_is(String contentType) {
    scenarioData.given().header(HttpHeaders.CONTENT_TYPE, contentType);
  }

  @Then("the endpoint returns {int}")
  public void the_endpoint_returns(int statusCode) {
    scenarioData.then().statusCode(statusCode);
  }

  @Then("the content type is {string}")
  public void the_content_type_is(String contentType) {
    scenarioData.then().contentType(contentType);
  }
}

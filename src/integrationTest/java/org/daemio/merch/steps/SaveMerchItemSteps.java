package org.daemio.merch.steps;

import java.math.BigDecimal;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.daemio.merch.data.ScenarioData;
import org.daemio.merch.model.MerchModel;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Save a piece of merch")
public final class SaveMerchItemSteps {

  @Autowired private ScenarioData scenarioData;
  private String merchLocURI;
  private MerchModel requestMerch;

  @Given("I am a vendor and logged in")
  public void I_am_a_vendor() {
    scenarioData.given().auth().preemptive().basic("test", "pass");
  }

  @Given("there is a new piece of merch to save")
  public void there_is_a_new_piece_of_merch_to_save() {
    requestMerch =
        MerchModel.builder().title("My Awesome merch").price(BigDecimal.valueOf(10.00)).build();
    scenarioData.given().body(requestMerch);
  }

  @Given("I have saved a new merch piece")
  public void I_have_saved_a_new_merch_piece() {
    merchLocURI =
        given()
            .auth()
            .preemptive()
            .basic("test", "pass")
            .body(
                MerchModel.builder()
                    .title("My Awesome merch")
                    .price(BigDecimal.valueOf(10.00))
                    .build())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .post("/merch")
            .then()
            .extract()
            .header(HttpHeaders.LOCATION);
  }

  @Given("it doesn't have a {string}")
  public void it_doesn_t_have_a(String param)
      throws IllegalArgumentException,
          IllegalAccessException,
          NoSuchFieldException,
          SecurityException {
    var field = requestMerch.getClass().getDeclaredField(param);
    field.setAccessible(true);
    field.set(requestMerch, null);

    scenarioData.given().body(requestMerch);
  }

  @When("the call to the save endpoint is made")
  public void the_call_to_the_save_endpoint_is_made() {
    scenarioData.post("/merch");
  }

  @When("the call is made to the returned location URI")
  public void the_call_is_made_to_the_location_URI() {
    scenarioData.get(merchLocURI);
  }

  @Then("the location header is set")
  public void the_location_header_is_set() {
    scenarioData.then().header(HttpHeaders.LOCATION, is(notNullValue()));
    // .header(HttpHeaders.LOCATION, matchesRegex("^/merch/\\d+$"));
  }

  @Then("the merch piece that was saved is returned")
  public void the_merch_piece_that_was_saved_is_returned() {
    scenarioData.then().body("title", is("My Awesome merch"));
  }

  @Then("the response describes the {string} as invalid")
  public void the_response_describes_the_is_invalid(String param) {
    scenarioData.then().body(String.format("errors.%s", param), is(notNullValue()));
  }
}

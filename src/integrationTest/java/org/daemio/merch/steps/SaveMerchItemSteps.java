package org.daemio.merch.steps;

import java.math.BigDecimal;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.daemio.common.JwtUtil;
import org.daemio.merch.config.RoleConfig;
import org.daemio.merch.data.ScenarioData;
import org.daemio.merch.dto.MerchResource;
import org.daemio.merch.model.MerchStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Save a piece of merch")
public final class SaveMerchItemSteps {

  @Autowired private ScenarioData scenarioData;
  @Autowired private RoleConfig roleConfig;
  private String merchLocURI;
  private MerchResource requestMerch;

  @Given("I am a vendor and logged in")
  public void I_am_a_vendor() {
    scenarioData.given().auth().oauth2(JwtUtil.getValidJwtForRole(roleConfig.vendor()));
  }

  @Given("there is a new piece of merch to save")
  public void there_is_a_new_piece_of_merch_to_save() {
    requestMerch =
        MerchResource.builder().title("My Awesome merch").price(BigDecimal.valueOf(10.00)).build();
    scenarioData.given().body(requestMerch);
  }

  @Given("I have saved a new merch piece")
  public void I_have_saved_a_new_merch_piece() {
    merchLocURI =
        given()
            .auth()
            .oauth2(JwtUtil.getValidJwtForRole(roleConfig.vendor()))
            .body(
                MerchResource.builder()
                    .title("My Awesome merch")
                    .price(BigDecimal.valueOf(10.00))
                    .build())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .post("/merch")
            .then()
            .extract()
            .header(HttpHeaders.LOCATION)
            .substring(7);
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

  @Given("a piece of merch exists")
  public void a_piece_of_merch_exists() {
    requestMerch =
        MerchResource.builder()
            .status(MerchStatus.LOADED)
            .title("My Awesome merch")
            .price(BigDecimal.valueOf(10.00))
            .build();

    merchLocURI =
        given()
            .auth()
            .oauth2(JwtUtil.getValidJwtForRole(roleConfig.vendor()))
            .body(requestMerch)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .post("/merch")
            .then()
            .extract()
            .header(HttpHeaders.LOCATION)
            .substring(7);
  }

  @Given("I update the title for the request")
  public void I_update_the_title_for_the_request() {
    requestMerch.setTitle("Not so awesome merch");
    scenarioData.given().body(requestMerch);
  }

  @Given("I only send a new title")
  public void I_only_send_a_new_title() {
    requestMerch = MerchResource.builder().title("Not so awesome merch").build();
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

  @When("the call to the update endpoint is made")
  public void the_call_to_the_update_endpoint_is_made() {
    scenarioData.put(merchLocURI);
  }

  @When("the call to the update with delta endpoint is made")
  public void the_call_to_the_update_with_delta_endpoint_is_made() {
    scenarioData.patch(merchLocURI);
  }

  @Then("the location header is set")
  public void the_location_header_is_set() {
    scenarioData.then().header(HttpHeaders.LOCATION, is(notNullValue()));
  }

  @Then("the merch piece that was saved is returned")
  public void the_merch_piece_that_was_saved_is_returned() {
    scenarioData.then().body("title", is("My Awesome merch"));
  }

  @Then("the updated merch piece that was saved is returned")
  public void the_updated_merch_piece_that_was_saved_is_returned() {
    scenarioData.then().body("title", is("Not so awesome merch"));
  }

  @Then("the response describes the {string} as invalid")
  public void the_response_describes_the_is_invalid(String param) {
    scenarioData.then().body(String.format("errors.%s", param), is(notNullValue()));
  }
}

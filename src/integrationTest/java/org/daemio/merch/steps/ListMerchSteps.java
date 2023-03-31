package org.daemio.merch.steps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import org.daemio.merch.domain.Merch;
import org.daemio.merch.domain.MerchStatus;
import org.daemio.merch.mapper.MerchMapper;
import org.daemio.merch.model.MerchModel;
import org.daemio.merch.repository.MerchRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@DisplayName("Feature: List of merch items")
public class ListMerchSteps {

  @Autowired private MerchRepository merchRepository;

  private MerchMapper mapper = Mappers.getMapper(MerchMapper.class);
  private List<MerchModel> merchModels = new ArrayList<>();
  private int pageNumber;
  private int pageSize;
  private ValidatableResponse response;

  @Given("there are three merch items")
  public void there_are_three_merch_items() {
    merchModels.clear();

    for (int i = 0; i < 3; i++) {
      Merch m = new Merch();
      m.setTitle("Amazing band shirt");
      m.setStatus(MerchStatus.SOLD_OUT);
      m.setPrice(BigDecimal.valueOf(5));

      merchModels.add(mapper.entityToModel(merchRepository.save(m)));
    }
  }

  @Given("we're looking for page {int} with max {int} results")
  public void we_re_looing_for_page_with_results(int page, int size) {
    this.pageNumber = page;
    this.pageSize = size;
  }

  @When("the call to the list endpoint is made")
  public void the_call_to_the_list_endpoint_is_made() {
    response =
        given()
            .queryParam("page", pageNumber)
            .queryParam("size", pageSize)
            .when()
            .get("/merch")
            .then();
  }

  @Then("the endpoint returns {int}")
  public void the_endpoint_returns(int statusCode) {
    response.statusCode(statusCode).contentType(ContentType.JSON);
  }

  @Then("the three merch items are returned in a list")
  public void the_three_merch_items_are_returned_in_a_list() {
    response.body("merch", hasSize(3));
  }

  @Then("the list is empty")
  public void the_list_is_empty() {
    response.body("merch", hasSize(0));
  }

  @Then("and they are in price descending order")
  public void and_they_are_in_price_descending_order() {
    // Write code here that turns the phrase above into concrete actions
  }

  @Given("we're sorting by price descending")
  public void we_re_sorting_by_price_descending() {
    // Write code here that turns the phrase above into concrete actions
  }

  @Given("they have differing prices")
  public void they_have_differing_prices() {
    // Write code here that turns the phrase above into concrete actions
  }
}

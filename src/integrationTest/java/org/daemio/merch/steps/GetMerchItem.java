package org.daemio.merch.steps;

import java.math.BigDecimal;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import org.daemio.merch.domain.Merch;
import org.daemio.merch.domain.MerchStatus;
import org.daemio.merch.repository.MerchRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@DisplayName("Get a merch item steps")
public class GetMerchItem {

    @Autowired
    private MerchRepository merchRepository;
    
    private Merch merch;
    private ValidatableResponse response;
    
    @Given("A merch item exists")
    public void a_merch_item_exists() {
        Merch merch = new Merch();
        merch.setTitle("Amazing band shirt");
        merch.setStatus(MerchStatus.SOLD_OUT);
        merch.setPrice(BigDecimal.valueOf(5));

        this.merch = merchRepository.save(merch);
    }

    @Given("A merch item doesn't exists")
    public void a_merch_item_doesn_t_exists() {
        merch = new Merch();
        merch.setId(-1);
    }

    @When("I call the merch item endpoint with a merch id")
    public void i_call_the_merch_item_endpoint_with_a_merch_id() {
        response = given()
                .pathParam("merchId", merch.getId())
            .when()
                .get("/merch/{merchId}")
            .then();
    }

    @Then("The endpoint resturns a {int}")
    public void the_endpoint_resturns_a(Integer statusCode) {
        response.statusCode(statusCode).contentType(ContentType.JSON);
    }

    @Then("It's the merch item defined by the merch id")
    public void It_s_the_merch_item_defined_by_the_merch_id() {
        response
            .body("merchId", is(merch.getId()))
            .body("title", is(merch.getTitle()));
    }
}

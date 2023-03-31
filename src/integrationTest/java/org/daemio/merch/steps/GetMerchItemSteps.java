package org.daemio.merch.steps;

import java.math.BigDecimal;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.DisplayName;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import org.daemio.merch.data.ScenarioData;
import org.daemio.merch.domain.Merch;
import org.daemio.merch.domain.MerchStatus;
import org.daemio.merch.mapper.MerchMapper;
import org.daemio.merch.model.MerchModel;
import org.daemio.merch.repository.MerchRepository;

import static org.hamcrest.Matchers.is;

@DisplayName("Get a merch item steps")
public final class GetMerchItemSteps {

  @Autowired private ScenarioData scenarioData;
  @Autowired private MerchRepository merchRepository;

  private MerchMapper mapper = Mappers.getMapper(MerchMapper.class);
  private MerchModel merch;

  @Given("a merch item exists")
  public void a_merch_item_exists() {
    Merch merch = new Merch();
    merch.setTitle("Amazing band shirt");
    merch.setStatus(MerchStatus.LOADED);
    merch.setPrice(BigDecimal.valueOf(5));

    this.merch = mapper.entityToModel(merchRepository.save(merch));
    scenarioData.given().pathParam("merchId", this.merch.getMerchId());
  }

  @Given("a merch item doesn't exists")
  public void a_merch_item_doesn_t_exists() {
    merch = new MerchModel();
    merch.setMerchId(-1L);

    scenarioData.given().pathParam("merchId", merch.getMerchId());
  }

  @When("the call to the get item endpoint is made with a merch id")
  public void the_call_to_the_get_item_endpoint_is_made_with_a_merch_id() {
    scenarioData.get("/merch/{merchId}").then();
  }

  @Then("it's the merch item defined by the merch id")
  public void It_s_the_merch_item_defined_by_the_merch_id() {
    scenarioData
        .then()
        .body("merchId", is(merch.getMerchId().intValue()))
        .body("title", is(merch.getTitle()));
  }
}

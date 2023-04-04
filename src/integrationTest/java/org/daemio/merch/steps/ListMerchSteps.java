package org.daemio.merch.steps;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;

import org.daemio.merch.data.ScenarioData;
import org.daemio.merch.domain.Merch;
import org.daemio.merch.domain.MerchStatus;
import org.daemio.merch.mapper.MerchMapper;
import org.daemio.merch.model.MerchModel;
import org.daemio.merch.model.MerchPage;
import org.daemio.merch.repository.MerchRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

@DisplayName("Feature: List of merch items")
public final class ListMerchSteps {

  @Autowired private ScenarioData scenarioData;
  @Autowired private MerchRepository merchRepository;

  private MerchMapper mapper = Mappers.getMapper(MerchMapper.class);
  private List<MerchModel> merchModels = new ArrayList<>();
  private Faker faker = new Faker();

  @Given("there are {int} merch items")
  public void there_are_three_merch_items(final int numberItems) {
    scenarioData.given();
    merchModels.clear();

    for (int i = 0; i < numberItems; i++) {
      Merch m = new Merch();
      m.setTitle("Amazing band shirt");
      m.setStatus(MerchStatus.LOADED);
      m.setPrice(BigDecimal.valueOf(5));

      merchModels.add(mapper.entityToModel(merchRepository.save(m)));
    }
  }

  @Given("we're looking for page {int} with max {int} results")
  public void we_re_looing_for_page_with_results(final int page, final int size) {
    scenarioData.given().queryParam("page", page).queryParam("size", size);
  }

  @Given("they have differing prices")
  public void they_have_differing_prices() {
    merchRepository
        .findAll()
        .forEach(
            m -> {
              m.setPrice(BigDecimal.valueOf(faker.number().numberBetween(10, 50)));
              merchRepository.save(m);
            });
  }

  @Given("we're sorting by {string} {order}")
  public void we_re_sorting_by(final String sortCol, final Direction orderBy) {
    scenarioData.given().queryParam("sort", String.format("%s,desc", sortCol));
  }

  @When("the call to the list endpoint is made")
  public void the_call_to_the_list_endpoint_is_made() {
    scenarioData.get("/merch");
  }

  @Then("and a list of merch is returned with {int} items")
  public void and_a_list_of_merch_is_returned_with_items(final int size) {
    scenarioData.then().body("merch", hasSize(size));
  }

  @Then("and they are in {string} {order} order")
  public void and_they_are_in_order(final String parameter, final Direction orderBy)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    var result = scenarioData.then().extract().as(MerchPage.class);
    var expectedOrder = new ArrayList<>(result.getMerch());
    Collections.sort(expectedOrder, getReflectiveBigDecimalSort(parameter, orderBy));

    assertThat(result.getMerch()).isEqualTo(expectedOrder);
  }

  private Comparator<MerchModel> getReflectiveBigDecimalSort(
      final String parameter, final Direction orderBy) {
    return (m1, m2) -> {
      try {
        Field field = m1.getClass().getDeclaredField(parameter);
        field.setAccessible(true);

        return orderBy == Direction.DESC
            ? ((BigDecimal) field.get(m2)).compareTo((BigDecimal) field.get(m1))
            : ((BigDecimal) field.get(m1)).compareTo((BigDecimal) field.get(m2));

      } catch (NoSuchFieldException
          | SecurityException
          | IllegalArgumentException
          | IllegalAccessException e) {
        e.printStackTrace();
      }

      return 0;
    };
  }
}

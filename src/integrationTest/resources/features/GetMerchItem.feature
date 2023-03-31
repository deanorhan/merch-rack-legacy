Feature: Get a merch item
People should be able to get a piece of merch

  Scenario: Get a merch Item successfully
    Given a merch item exists
    When the call to the get item endpoint is made with a merch id
    Then the endpoint returns 200
    And the content type is "application/json"
    And it's the merch item defined by the merch id

  Scenario: Merch item is not found
    Given a merch item doesn't exists
    When the call to the get item endpoint is made with a merch id
    And the content type is "application/problem+json"
    Then the endpoint returns 404
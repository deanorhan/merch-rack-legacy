Feature: Save a piece of merch
  A vendor should have the abilty to save and update
  merch within the platform

  Background: User is a vendor
    Given I am a vendor and logged in

  Scenario: Save new merch
    Given there is a new piece of merch to save
    And the request content type is "application/json"
    When the call to the save endpoint is made
    Then the endpoint returns 201
    And the location header is set

  Scenario: See the new merch
    Given I have saved a new merch piece
    When the call is made to the returned location URI
    Then the endpoint returns 200
    And the merch piece that was saved is returned

  Scenario Outline: validation
    Given there is a new piece of merch to save
    But it doesn't have a <param>
    And the request content type is "application/json"
    When the call to the save endpoint is made
    Then the endpoint returns 400
    And the content type is "application/problem+json"
    And the response describes the <param> as invalid

    Examples:
      |   param |
      | "title" |
      | "price" |

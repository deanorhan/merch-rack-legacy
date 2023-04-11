Feature: List of merch items
  Paginated list of merch

  Background:
    Given a valid JWT for "fan"

  Rule: Results must be paginated

    Scenario Outline: paginating
      Given there are <items> merch items
      And we're looking for page <page> with max <size> results
      When the call to the list endpoint is made
      Then the endpoint returns 200
      And the content type is "application/json"
      And and a list of merch is returned with <result> items

      Examples:
        | items | page | size | result |
        |     3 |    0 |   10 |      3 |
        |     5 |    1 |    2 |      2 |

    Scenario: Getting an empty list of results
      Given there are 2 merch items
      And we're looking for page 1 with max 5 results
      When the call to the list endpoint is made
      Then the endpoint returns 200
      And the content type is "application/json"
      And and a list of merch is returned with 0 items

  Rule: Results must be sortable

    Scenario Outline: sorting
      Given there are <items> merch items
      And they have differing prices
      And we're sorting by <param> <order>
      When the call to the list endpoint is made
      Then the endpoint returns 200
      And the content type is "application/json"
      And and they are in <param> <order> order

      Examples:
        | items |         param |      order |
        |     3 |       "price" | descending |
        |     5 |       "price" |  ascending |
        #|     7 | "createdTime" |  ascending |
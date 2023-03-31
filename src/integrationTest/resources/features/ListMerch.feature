Feature: List of merch items
    Paginated list of merch

    Scenario: Get the first page of merch items
        Given there are three merch items
        And we're looking for page 1 with max 10 results
        When the call to the list endpoint is made
        Then the endpoint returns 200
        And the three merch items are returned in a list

    Scenario: Getting an empty list of results
        Given there are three merch items
        And we're looking for page 2 with max 5 results
        When the call to the list endpoint is made
        Then the endpoint returns 200
        And the list is empty

    Scenario: Getting a list sorted by price
        Given there are three merch items
        And they have differing prices
        And we're sorting by price descending
        When the call to the list endpoint is made
        Then the endpoint returns 200
        And the three merch items are returned in a list
        And and they are in price descending order
Feature: Get a merch item
    People should be able to get a piece of merch

    Scenario: Get a merch Item successfully
        Given A merch item exists
        When I call the merch item endpoint with a merch id
        Then The endpoint resturns a 200
        And It's the merch item defined by the merch id

    Scenario: Merch item is not found
        Given A merch item doesn't exists
        When I call the merch item endpoint with a merch id
        Then The endpoint resturns a 404
#language: en

Feature: Add new Address for newly created user
  As a new user
  I want to be able to add new address
  So i can have 1 or more addresses for delivery

  @done
  Scenario Outline: Adding new Address for newly created user
    Given I'm on https://mystore-testlab.coderslab.pl/ logined as new user
    When Clicks on the "Addresses" tile after logging in
    And The address we should be at: "https://mystore-testlab.coderslab.pl/index.php?controller=addresses")
    When Clicks on + Create new address,

    #<country> field is redundant as there is only option to choose and no ability to
    When Fills out the New address form with "<alias>", "<address>", "<city>", "<zip_postal_code>", "<country>", "<phone>"
    Then Checks if the data in the added address is correct..
    When Deletes the above address by clicking Delete
    Then Checks if the address has been deleted.
    Examples:
      | alias  | address               | city    | zip_postal_code | country | phone         |
      | Kirich | 47, Grabiszynska Ave. | Wroclaw | 53505           | Ukraine | +380998887766 |

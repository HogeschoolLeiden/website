Feature: Home page
  I want to automate testing of home page.

  Scenario: Open the root page and check whether the Title is there
    When open the page "" on site
    And query string "iamhere=justToHaveAnd"
    Then the header should read "Title home"

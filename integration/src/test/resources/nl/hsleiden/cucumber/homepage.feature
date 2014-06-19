Feature: Home page
  I want to automate testing of home page.

  Scenario: Open the root page and check whether the Title is there
    Given the host name URL "http://localhost:9207"
    When open the context path "/site/"
    And query string "iamhere=justToHaveAnd"
    Then the header should read "Title home"

@put
Feature: ID-010
  In order to export the information to plan activities
  As a instructor
  I want to consult all the information of supervised activities and the next that I have to be monitored

  Scenario: View the list of activities
    Given the website
    And the database
    When I open the activities page
    Then the system must be displayed all activities that I         have to supervise
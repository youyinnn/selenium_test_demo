Feature: Get
  Testing get request.

  @openWebPage
  Scenario Outline:
    When I open the chrome browser with "<url>"
    Then I should see the webpage render "<result>"

    Examples:
      | url                                                    | result    |
      | http://168.138.68.114:8080/coen6731/public/index.html | correctly |
#      | http://localhost:8080/coen6731/public/index.html       | correctly |

  @sendGetRequest
  Scenario Outline:
    When I generate <validity> random parameters for <number_req> requests
    Then I construct requests with those parameters and send them to api "<api_endpoint>"
    And I should receive <number_resp> responses contain message "<response_text>"

    Examples:
      | validity | number_req | number_resp | response_text                             | api_endpoint                                |
      | 1        | 10         | 10          | Response Code: 200                        | http://168.138.68.114:8080/coen6731/skiers |
      | 0        | 10         | 10          | Request failed with status code 400       | http://168.138.68.114:8080/coen6731/skiers |
      | 0        | 10         | 10          | ResortID or SkierID should be an integer. | http://168.138.68.114:8080/coen6731/skiers |

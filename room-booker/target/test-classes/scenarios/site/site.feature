Feature: Site Information (KI-226 Get Site Information)
  To look for the room information

  Scenario: Get all site information
    Given a site information url "/sites"
    When I execute the sites url
    Then I should get response as a json string array containing the sites data



	
 

Feature: Room Information (KI-226 Get Room Information)
  To look for the room information
 
  Scenario: Get all room information
    Given a room information url "/rooms"
    When I execute the url
    Then I should get response as a json string array containing the rooms data

  Scenario: Get site, building details for location
    Given a site, building for location information url "/sites-building-for-location"
    When I execute the url /sites-building-for-location
    Then I should get response as a json string array containing the location data having the array/list of site and building data

	
 

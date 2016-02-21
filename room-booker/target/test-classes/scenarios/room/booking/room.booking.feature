Feature: Room Booking
  To book a room
 
  Scenario: Add a room Booking
    Given a room booking information
    When I send a user name "Jamie Clark"
    Then a Room Booking must be added and the json format of the response should be displayed



	
 

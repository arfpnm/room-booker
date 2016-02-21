Feature: Get Room Booking by Criteria
   
  Scenario: room booking for building and date range
    Given a room booking details
    When I send a following json which includes building name, start date and end date 
     """
 	 { 
 	 "building" : "Windsor House", 
 	 "start-date" : 1448645740000, 
 	 "end-date" : 1448896981000 
 	 }
 	 """
    Then a Room Booking must be retrieve and the json format of the response should be displayed



	
 

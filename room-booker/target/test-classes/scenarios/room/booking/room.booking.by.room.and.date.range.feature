Feature: Get Room Booking by Room number Criteria
   
  Scenario: room booking for room and date range
    Given a room booking details
    When I send a following json which includes room name, start date and end date 
     """
 	 { 
 		room:22,
	 	start-date:1447750807316,
	 	end-date:1447862617191
 	 }
 	 """
    Then I must be get the json format as response and should be displayed



	
 

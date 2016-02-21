package com.nhs.trust.spring.test.cucumber.room.bookingsteps;
/**
 * @author arif.mohammed
 */
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


@ContextConfiguration("/cucumber.xml")
//@PropertySource("classpath*:test-resource-test.properties")
@EnableWebMvc
@WebAppConfiguration
//@Ignore
public class RoomBookingSteps {
	
	private String url;
	private String response;
	//@Autowired
	//private NuxeoService nuxeoService; 

	@Autowired

	WebApplicationContext context;
	private MockMvc mockMvc;
	
	@Before 
    public void setUp() { 
        mockMvc = MockMvcBuilders 
        .webAppContextSetup(context).build(); 
    } 
		
	@Given("^a room booking information$")
	public void a_room_booking_information() throws Throwable {
		   System.out.println("Start room booking information to add....");
	   
	}

	@When("^I send a user name \"([^\"]*)\"$")
	public void i_send_a_user_name(String userName) throws Throwable {
		MvcResult responseResult = mockMvc.perform(post("/room-booking/add-room-booking/"+userName))
		         .andExpect(status().isOk()).andReturn();
		 		
		response = responseResult.getResponse().getContentAsString();
	}

	@Then("^a Room Booking must be added and the json format of the response should be displayed$")
	public void a_Room_Booking_must_be_added_and_the_json_format_of_the_response_should_be_displayed() throws Throwable {
		System.out.println(response);
		   //Assert.assertTrue(response != null);
		   System.out.println("Completed Successfully...");
	}
	
	
	@Given("^a room booking details$")
	public void a_room_booking_details() throws Throwable {
		  System.out.println("Start room_booking_details to retrieve by building and date range....");
	}
	String json=null;

	@When("^I send a following json which includes building name, start date and end date$")
	public void i_send_a_following_json_which_includes_building_name_start_date_and_end_date(String json) throws Throwable {
		MvcResult responseResult = this.mockMvc.perform(post("/bookings/building/date-range").contentType(MediaType.APPLICATION_JSON).content(json))
		        .andReturn();
		response = responseResult.getResponse().getContentAsString();
	}

	@Then("^a Room Booking must be retrieve and the json format of the response should be displayed$")
	public void a_Room_Booking_must_be_retrieve_and_the_json_format_of_the_response_should_be_displayed() throws Throwable {
	  System.out.println(response);
	}
	
//	@Given("^a room booking details$")
//	public void a_room_booking_details_for_room_id() throws Throwable {
//		  System.out.println("Start room_booking_details for room id, to retrieve by building and date range....");
//	}
	
	@When("^I send a following json which includes room name, start date and end date$")
	public void i_send_a_following_json_which_includes_room_name_start_date_and_end_date(String json) throws Throwable {
		MvcResult responseResult = this.mockMvc.perform(post("/bookings/room/date-range").contentType(MediaType.APPLICATION_JSON).content(json))
		        .andReturn();
		response=responseResult.getResponse().getContentAsString();
	}

	@Then("^I must be get the json format as response and should be displayed$")
	public void i_must_be_get_the_json_format_as_response_and_should_be_displayed() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		 System.out.println(response);
	}


}

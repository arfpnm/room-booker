package com.nhs.trust.spring.test.cucumber.room.infosteps;
/**
 * @author arif.mohammed
 */

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration("/cucumber.xml")
@PropertySource("classpath*:resource-test.properties")
@EnableWebMvc
@WebAppConfiguration
//@Ignore
public class RoomSteps {

	private String url;
	private String response;

	@Autowired
	WebApplicationContext context;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context).build();
	}

	@Given("^a room information url \"([^\"]*)\"$")
	public void a_room_information_url(String url) throws Throwable {
		System.out.println("Start");
		this.url=url;
	}

	@When("^I execute the url$")
	public void i_execute_the_url() throws Throwable {
		//MvcResult responseResult = mockMvc.perform(get("/get-all-rooms"))
		MvcResult responseResult = mockMvc.perform(get(url))
				.andExpect(status().isOk()).andReturn();

		response = responseResult.getResponse().getContentAsString();
	}

	@Then("^I should get response as a json string array containing the rooms data$")
	public void i_should_get_response_as_a_json_string_array_containing_the_rooms_data() throws Throwable {
System.out.println(response);
	}


	@Given("^a site, building for location information url \"([^\"]*)\"$")
	public void a_site_building_for_location_information_url(String url) throws Throwable {
		System.out.println("Start");
		this.url=url;
	}

	@When("^I execute the url /sites-building-for-location$")
	public void i_execute_the_url_sites_building_for_location() throws Throwable {
		MvcResult responseResult = mockMvc.perform(get(url))
				.andExpect(status().isOk()).andReturn();
		response = responseResult.getResponse().getContentAsString();
	}

	@Then("^I should get response as a json string array containing the location data having the array/list of site and building data$")
	public void i_should_get_response_as_a_json_string_array_containing_the_location_data_having_the_array_list_of_site_and_building_data() throws Throwable {
		assertTrue(response != null);
		System.out.println(response);
	}


}

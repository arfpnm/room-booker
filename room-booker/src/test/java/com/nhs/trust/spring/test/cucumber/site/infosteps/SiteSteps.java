package com.nhs.trust.spring.test.cucumber.site.infosteps;
/**
 * @author arif.mohammed
 */

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration("/cucumber.xml")
//@PropertySource("classpath*:test-resource-test.properties")
@EnableWebMvc
@WebAppConfiguration
//@Ignore
public class SiteSteps {

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

	@Given("^a site information url \"([^\"]*)\"$")
	public void a_site_information_url(String arg1) throws Throwable {
		System.out.println("Start Site info");
		this.url=url;
	}

	@When("^I execute the sites url$")
	public void i_execute_the_sites_url() throws Throwable {
		MvcResult responseResult = mockMvc.perform(get("/sites"))
				.andExpect(status().isOk()).andReturn();
		response = responseResult.getResponse().getContentAsString();
	}

	@Then("^I should get response as a json string array containing the sites data$")
	public void i_should_get_response_as_a_json_string_array_containing_the_sites_data() throws Throwable {
			System.out.println(response);
	}
}

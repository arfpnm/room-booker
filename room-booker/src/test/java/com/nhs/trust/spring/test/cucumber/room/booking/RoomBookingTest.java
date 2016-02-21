package com.nhs.trust.spring.test.cucumber.room.booking;
/**
 * @author arif.mohammed
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.Ignore;
import org.junit.runner.RunWith;

@Ignore
@RunWith(Cucumber.class) 
@CucumberOptions(glue = { "com.nhs.trust.spring.test.cucumber.room.bookingsteps" }, features = { "src/test/resources/scenarios/room/booking" })
public class RoomBookingTest {}

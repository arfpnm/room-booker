package com.nhs.trust.spring.test.cucumber.site.info;
/**
 * @author arif.mohammed
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.Ignore;
import org.junit.runner.RunWith;

@Ignore
@RunWith(Cucumber.class) 
@CucumberOptions(glue = { "com.nhs.trust.spring.test.cucumber.site.infosteps" }, features = { "src/test/resources/scenarios/site" })
public class SiteTest {}

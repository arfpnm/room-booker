package com.nhs.trust.spring.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhs.trust.domain.RoomBooking;
import com.nhs.trust.domain.Holiday;

@Ignore
/** Not used - delete this file as its just used for initial quick test **/

@ActiveProfiles("test")
public class SpringTest {

	@Test
	public void callRest() throws IOException{
	}

	//@Test
	public void callRest1(){
		RestTemplate restTemplate = new RestTemplate();
		String url="https://maps.googleapis.com/maps/api/geocode/json?address=Westfield Road,+Bishop Auckland,+DL146AE";
		ResponseEntity<String>  response =
				restTemplate.getForEntity(url, String.class);
		JSONArray resultArray = new JSONObject(response.getBody()).getJSONArray("title");
		Double latitude;
		Double longitude;
		for(int i = 0; i < resultArray .length(); i++)
		{
			JSONObject mainObject = resultArray.getJSONObject(i);
			JSONObject geometry= mainObject.getJSONObject("geometry");
			JSONObject location= geometry.getJSONObject("location");
			latitude = location.getDouble("lat");
			longitude = location.getDouble("lng");
		}
	}

	//@Test
	public void callRest2(){
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8089/TEWVService/buildings/Wessex House/rooms";
			ResponseEntity<String>  response =
					restTemplate.getForEntity(url, String.class);
			System.out.println("responseMessage from sprinmg rest call - title: "+response.getBody());
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//@Test
	public void callRestAddModify(){
		long bookingId=0;
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8089/TEWVService/room-booking/add-update-room-booking";

			String roomBookerToAdd ="{"
					+ "      \"userEmailId\": \"noreply@wtg.co.uk\"," 
					+ "      \"userName\": \"Bishop Auckland\"," 
					+ "      \"location\": \"noreply@wtg.co.uk\"," 
					+ "      \"site\": \"Auckland Park Hospital\"," 
					+ "      \"bookingId\": \"null\"," 
					+ "      \"roomId\": \"65\"," 
					+ "      \"startTime\": \"1450705500000\"," 
					+ "      \"endTime\": \"1450707300000\"," 
					+ "      \"title\": \"Some XYZ Hospital\"," 
					+ "      \"description\": \"Some XYZ Hospital - Premium XYZ Room\"" 
					+"			}";
			ResponseEntity<String>  add_response =
					restTemplate.postForEntity(url, roomBookerToAdd, String.class);
			String jsonResponse = add_response.getBody();
			ObjectMapper objectMapper = new ObjectMapper();
			RoomBooking roomBooking = objectMapper.readValue(jsonResponse, RoomBooking.class);
			bookingId = roomBooking.getBookingId();
			System.out.println("response message after adding the data (Insert) : "+jsonResponse);

			String roomBookerForModification ="{"
					+ "      \"userEmailId\": \"noreply@wtg.co.uk\"," 
					+ "      \"userName\": \"Bishop James\"," 
					+ "      \"location\": \"noreply@wtg.co.uk\"," 
					+ "      \"site\": \"Warren Road Day Centre\"," 
					+ "      \"bookingId\": "+bookingId+","  //Need to pass a valid booking id
					+ "      \"roomId\": \"66\"," 
					+ "      \"startTime\": \"1450705500000\"," 
					+ "      \"endTime\": \"1450707300000\"," 
					+ "      \"title\": \"Warren Road Day Centre Hospital Room\"," 
					+ "      \"description\": \"Warren Road Day Centre - Standard Room\"" 
					+ "			}";
			ResponseEntity<String>  modify_response =
					restTemplate.postForEntity(url, roomBookerForModification, String.class);
			System.out.println("response message after modification : "+modify_response.getBody());
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	//@Test
	public void callRestCancelBooking(){
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8089/TEWVService/room-booking/cancel-booking";
			String bookingIdToCancel ="{"
					+ "     			 \"bookingId\": \"7\"" 
					+ "				 }";
			ResponseEntity<String>  add_response =
					restTemplate.postForEntity(url, bookingIdToCancel, String.class);
			String jsonResponse = add_response.getBody();
			System.out.println("response message after adding the data (Insert) : "+jsonResponse);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void callUKBankHolidays(){
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://www.webcal.fi/cal.php?id=435&format=json&start_year=2016&end_year=2016&tz=London";

			ResponseEntity<String>  add_response =
					restTemplate.postForEntity(url, null, String.class);
			String jsonResponse = add_response.getBody();
			System.out.println("response message after adding the data (Insert) : "+jsonResponse);
			
			
					ObjectMapper mapper = new ObjectMapper();
					List<Holiday> list = mapper.readValue(jsonResponse, 
					    new TypeReference<ArrayList<Holiday>>() {});
					
					
			list.forEach(bankHoliday -> System.out.println(bankHoliday.getDate() +" : " +bankHoliday.getName()));
			
			//bankHolidayList.forEach(bankHoliday -> System.out.println(bankHoliday.getName()));

			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//http://www.webcal.fi/cal.php?id=435&format=json&start_year=2016&end_year=2016&tz=London


}

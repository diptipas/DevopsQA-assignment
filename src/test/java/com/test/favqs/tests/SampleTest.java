package com.test.favqs.tests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.test.favqs.base.BasePage;

import io.restassured.RestAssured;
//import io.restassured.response.Response;

public class SampleTest extends BasePage{

	//private static RestClientWrapper restClient;
	private static String baseUrl;
		
	@BeforeClass
	public void setupTest() throws Exception {

		baseUrl = readingPropertiesFile.getProperty("baseurl");
		
	}
	
	
	@Test
	public void ReqresGetSingleUser() throws Exception {
		
		RestAssured.given().baseUri(baseUrl)
		.when().get("api/users/2")
		.then().assertThat().statusCode(200).log().all();
				
		}
	
	@Test
	public void ReqresSingleUserNotFound() throws Exception {
		
		RestAssured.given().baseUri(baseUrl)
		.when().get("/api/users/23")
		.then().assertThat().statusCode(404).log().all();
	}
	
	@Test	
	public void ReqresGetListofUsers() throws Exception {
		RestAssured.given().baseUri(baseUrl)
		.queryParam("page", 2)
		.when().get("/api/users")
		.then().assertThat().statusCode(200).log().all();
	}
	
	@Test	
	public void ReqresCreateUser() throws Exception {
		RestAssured.given().baseUri(baseUrl)
		.body("{\r\n" + 
				"    \"name\": \"morpheus\",\r\n" + 
				"    \"job\": \"leader\"\r\n" + 
				"}")
		.when().post("/api/users")
		.then().assertThat().statusCode(201).log().all();	
	}
	
	@Test
	public void ReqresUpdateUser() throws Exception {
		RestAssured.given().baseUri(baseUrl)
		.body("{\r\n" + 
				"    \"name\": \"morpheus\",\r\n" + 
				"    \"job\": \"zion resident\"\r\n" + 
				"}")
		.when().put("/api/users/2")
		.then().assertThat().statusCode(200).log().all();
	}
	
}

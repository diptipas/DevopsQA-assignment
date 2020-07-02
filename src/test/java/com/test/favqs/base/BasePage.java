package com.test.favqs.base;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.favqs.utils.ExtentManager;
import com.test.favqs.utils.ReadingPropertiesFile;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BasePage {
	protected static ReadingPropertiesFile readingPropertiesFile = new ReadingPropertiesFile();
	public static ExtentReports extent;
	public static ExtentTest test;
	protected static RequestSpecification httpRequest;
	
	@BeforeSuite
	public static void initializeBrowser() throws MalformedURLException {

		extent = ExtentManager.getInstance("reports/TestExecutionReport.html");

	}
	@BeforeMethod
	public void startTest(Method method) {
		httpRequest = RestAssured.given();

		test = extent.startTest(method.getName());

	}
	@AfterMethod
	public void reportFlush(ITestResult result) {
		System.out.println(result.getMethod().getMethodName());
		if (result.getStatus() == ITestResult.FAILURE)
			test.log(LogStatus.FAIL, result.getThrowable());
		else if (result.getStatus() == ITestResult.SKIP)
			test.log(LogStatus.SKIP, result.getThrowable());
		else
			test.log(LogStatus.PASS, "Test passed");

		extent.flush();
	}
}

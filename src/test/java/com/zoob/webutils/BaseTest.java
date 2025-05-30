package com.zoob.webutils;

import java.lang.reflect.Method;
import java.time.Duration;

import org.testng.annotations.*;
import com.zoob.login.page.LoginPageTestScript;

public class BaseTest {

	protected WebUtil util;

	@BeforeSuite
	public void beforeSuiteMethod() {
		System.out.println("ExtentReports initialized");
		WebUtil.generateReport();
	}

	@BeforeTest
	public void beforeTestMethod() {
		util = WebUtil.getInstance();
		System.out.println("I am before Test Method");
	}

	@BeforeClass
	public void beforeClassMethod() {
		System.out.println("I am before Class Method");
	}

//	@Parameters({ "browser", "username", "password", "colortheme" })
	@BeforeMethod
	public void beforeMethod(Method meth) {
		System.out.println("I am beforeMethod method");
		String tcName = meth.getName();
		util.createReport(tcName);
		util.launchBrowser("chromebrowser");
		util.maxWindow();
		util.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		util.openUrl("https://www.businessghana.com/myaccount/profile/login?redirect=%2Fprofile%2Findex");

	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("I am After Method");
		util.quit(2000);
	}

	@AfterClass
	public void afterClass() {
		System.out.println("I am After Class Method");
		util.flushReport();
	}

	@AfterTest
	public void afterTest() {
		System.out.println("I am After Test Method");
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("Destroyed ExtentReports initialized");
	}

}

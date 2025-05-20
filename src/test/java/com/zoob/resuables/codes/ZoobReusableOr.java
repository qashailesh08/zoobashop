package com.zoob.resuables.codes;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zoob.webutils.WebUtil;

public class ZoobReusableOr {

	public ZoobReusableOr(WebUtil driver) {
		PageFactory.initElements(driver.getDriver(), this);
	}

	@FindBy(xpath = "//span[text()='Sign Up']")
	protected WebElement signUpBT;

	@FindBy(xpath = "//span[text()='Login']")
	protected WebElement loginBT;

	@FindBy(xpath = "//a[text()=' Contact Us']")
	protected WebElement contactLT;
	
	@FindBy(xpath = "//a[text()='Add a listing']")
	protected WebElement addListLT;
	
	@FindBy(xpath = "//ul[@class='mobile-sub wsmenu-list']//child::a[text()='Sports']")
	protected WebElement sportsLT;

}

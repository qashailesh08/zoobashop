package com.zoob.resuables.codes;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zoob.webutils.WebUtil;

import lombok.Getter;

@Getter
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

	@FindBy(xpath = "//ul[@class='mobile-sub wsmenu-list']//a[text()='Directory']")
	private WebElement directoryLT;

	@FindBy(xpath = "//ul[@class='mobile-sub wsmenu-list']//a[text()='Events']")
	private WebElement eventsLT;

	@FindBy(xpath = "//ul[@class='mobile-sub wsmenu-list']//a[text()='Classifieds']")
	private WebElement classifiedsLT;

	@FindBy(xpath = "//ul[@class='mobile-sub wsmenu-list']//a[text()='Jobs']")
	private WebElement jobsLT;

	@FindBy(xpath = "//ul[@class='mobile-sub wsmenu-list']//a[text()='Real Estate']")
	private WebElement realEstateLT;

	@FindBy(xpath = "//ul[@class='mobile-sub wsmenu-list']//a[text()='Autos']")
	private WebElement autosLT;

	@FindBy(xpath = "//ul[@class='mobile-sub wsmenu-list']//a[text()='News']")
	private WebElement newsLT;

	@FindBy(xpath = "//input[@id='searchtext']")
	private WebElement searchBarTB;

	@FindBy(xpath = "//input[@id='searchtext']/parent::div//button[@type='submit']")
	private WebElement searchSubmitBT;
	
	@FindBy(xpath = "//a[text()='Advanced Search']")
	private WebElement advancedSearchLT;

	@FindBy(xpath = "//input[@id='search' and @required='required']")
	private WebElement cmnSearchTB;
	
	@FindBy(xpath = "//ul[@id='search_nav']//button")
	private WebElement cmnSearchButton;
	















}

package com.zoob.signup.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.zoob.webutils.WebUtil;
import lombok.Getter;

@Getter
public class SignUpIndividualAccountOR {

	public SignUpIndividualAccountOR(WebUtil util) {
		PageFactory.initElements(util.getDriver(), this);
	}

	@FindBy(xpath = "//a[text()='Sign Up']")
	private WebElement signUpBT;

	@FindBy(linkText = "Individual Account")
	private WebElement individualAccountLT;

	// Login Deatils
	@FindBy(xpath = "//input[@id='last-name']")
	private WebElement lastNameTB;

	@FindBy(id = "first-name")
	private WebElement firstNameTB;

	@FindBy(id = "username")
	private WebElement profileNameTb;

	@FindBy(id = "email")
	private WebElement emailTB;

	@FindBy(id = "password")
	private WebElement passwordTB;

	@FindBy(id = "confirm-password")
	private WebElement confirmPasswordTB;

	// Personal Details
//
//	@FindBy(xpath = "//option[@value='Male']")
//	private WebElement maleCB;
//
//	@FindBy(xpath = "//option[@value='Female']")
//	private WebElement femaleCB;

	@FindBy(xpath = "//select[@id='gender']")
	private WebElement gendarCB;

	@FindBy(id = "date_of_birth")
	private WebElement DOBTB;

	// Contact Details
	@FindBy(id = "phone")
	private WebElement phoneTB;

	@FindBy(id = "alt-phone")
	private WebElement alternativePhoneTB;

	@FindBy(id = "alt-email")
	private WebElement alternativeEmailTB;

	// Location Details

	@FindBy(id = "other-location")
	private WebElement otherLocationTB;

	@FindBy(xpath = "//select[@id='city']")
	private WebElement locationCB;

	@FindBy(id = "street-address")
	private WebElement streetAddressTB;

	@FindBy(id = "postal-address")
	private WebElement postalAddressTB;

	@FindBy(xpath = "//button[text()='Submit']")
	private WebElement submitBT;

	@FindBy(xpath = "//select[@name='country_id']")
	private WebElement countryCB;

	@FindBy(xpath = "//select[@id='region']")
	private WebElement regionCB;

}

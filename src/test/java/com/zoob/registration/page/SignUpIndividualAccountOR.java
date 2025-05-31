package com.zoob.registration.page;

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

	@FindBy(xpath = "//option[@value='Male']")
	private WebElement maleCB;

	@FindBy(xpath = "//option[@value='Female']")
	private WebElement femaleCB;
	
	@FindBy(id = "date_of_birth")
	private WebElement DOBTB;

}

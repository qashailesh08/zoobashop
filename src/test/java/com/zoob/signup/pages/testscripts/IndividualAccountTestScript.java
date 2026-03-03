package com.zoob.signup.pages.testscripts;

import org.testng.annotations.Test;
import org.testng.annotations.Test;

import com.zoob.resuables.codes.ZoobReusablePage;
import com.zoob.signup.pages.SignUpIndividualAccountOR;
import com.zoob.signup.pages.SignUpIndividualAccountPageWige;
import com.zoob.webutils.BaseTest;

public class IndividualAccountTestScript extends BaseTest {

	@Test
	public void createIndividualAccount() {
		ZoobReusablePage zoobObj = new ZoobReusablePage(util);
		SignUpIndividualAccountPageWige indiObj = new SignUpIndividualAccountPageWige(util);
		SignUpIndividualAccountOR signORObj = new SignUpIndividualAccountOR(util);
		zoobObj.loginBT();
		indiObj.signUpBottun();
		indiObj.individualAccountLT();
		// BusinessGhana Account - Individual Registration
		// Login Details
		indiObj.lastNameTB("Micheil");
		util.stringValidation(signORObj.getLastNameTB(), "Micheil", "Last Name TB");
		indiObj.firstNameTB("Jone");
		util.stringValidation(signORObj.getFirstNameTB(), "Jone", "First Name TB");
		indiObj.profileNameTB("Miecheal_jone");
		util.stringValidation(signORObj.getProfileNameTb(), "Miecheal_jone", "Profile Name TB");
		indiObj.emailTB("micheil856sh@gmail.com");
		util.stringValidation(signORObj.getEmailTB(), "micheil856sh@gmail.com", "Email TB");
		indiObj.passwordTB("934736bdhfi4");
		util.stringValidation(signORObj.getPasswordTB(), "934736bdhfi4", "Password TB");
		indiObj.cnfPassword("934736bdhfi4");
		util.stringValidation(signORObj.getConfirmPasswordTB(), "934736bdhfi4", "Confirm Password TB");
		// Personal Details
		indiObj.selectGendar("Female");
		indiObj.countryCB("78");
		indiObj.DOBTB("23/09/1990");
		util.stringValidation(signORObj.getDOBTB(), "23/09/1990", "DOB TB");
		// Contact Details
		indiObj.phoneTB("7492774927");
		util.stringValidation(signORObj.getPhoneTB(), "7492774927", "Phone TB");
		indiObj.altPhone("8364869426");
		util.stringValidation(signORObj.getAlternativePhoneTB(), "8364869426", "Alternative Phone TB");
		indiObj.altEmail("abcd842@gmail.com");
		util.stringValidation(signORObj.getAlternativeEmailTB(), "abcd842@gmail.com", "Alternative Email TB");
		// Location Details
		indiObj.regionCB("7");
		indiObj.locationCB("Beecham");
		indiObj.otherLocationTB("Japan Central");
		util.stringValidation(signORObj.getOtherLocationTB(), "Japan Central", "Other Location TB");
		indiObj.streetAddressTB("Monaco City");
		util.stringValidation(signORObj.getStreetAddressTB(), "Monaco City", "Street Address TB");
		indiObj.postalAddressTB("94736");
		util.stringValidation(signORObj.getPostalAddressTB(), "94736", "Postal Address TB");
		indiObj.formSubmitBT();
		util.getPageSource("Account not created. Invalid details. Please, try again.");
		util.getPageSource("BusinessGhana Account - Individual Registration");
		util.getPageSource("This value is already in use");

	}

}

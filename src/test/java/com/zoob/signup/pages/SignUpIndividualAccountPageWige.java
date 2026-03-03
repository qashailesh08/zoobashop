package com.zoob.signup.pages;

import com.zoob.webutils.WebUtil;

public class SignUpIndividualAccountPageWige extends SignUpIndividualAccountOR {

	private WebUtil util;

	public SignUpIndividualAccountPageWige(WebUtil util) {
		super(util);
		this.util = util;
	}

	public void signUpBottun() {

		util.click(getSignUpBT(), "Sign Up Button");
	}

	public void individualAccountLT() {
		util.click(getIndividualAccountLT(), "Individual Account Button");
	}

	// Login Details

	public void lastNameTB(String lastName) {
		util.inputValue(getLastNameTB(), lastName, "Last Name TB");
	}

	public void firstNameTB(String firstName) {
		util.inputValue(getFirstNameTB(), firstName, "First Name TB");
	}

	public void profileNameTB(String userName) {
		util.inputValue(getProfileNameTb(), userName, "Profile Name TB");
	}

	public void emailTB(String email) {
		util.inputValue(getEmailTB(), email, "Email TB");
	}

	public void passwordTB(String pass) {
		util.inputValue(getPasswordTB(), pass, "Password TB");
	}

	public void cnfPassword(String cmfPass) {
		util.inputValue(getConfirmPasswordTB(), cmfPass, "Confirm Password TB");
	}

	// Personal Details

	public void DOBTB(String dob) {
		util.inputValue(getDOBTB(), dob, "DOB TB");
	}

//	public void maleCB(String byValue) {
//		util.selectByValue(getMaleCB(), byValue, "Male CB");
//	}
//
//	public void femaleCB(String byValue) {
//		util.selectByValue(getFemaleCB(), byValue, "female CB");
//	}

	public void selectGendar(String gendar) {
		util.selectByValue(getGendarCB(), gendar, "Gendar CB");
	}

	public void countryCB(String valueCountry) {
		util.selectByValue(getCountryCB(), valueCountry, "Country CB");
	}
	// Contact Details

	public void phoneTB(String phone) {
		util.inputValue(getPhoneTB(), phone, "Phone TB");
	}

	public void altPhone(String altPhone) {
		util.inputValue(getAlternativePhoneTB(), altPhone, "Alternative Phone TB");
	}

	public void altEmail(String altEmail) {
		util.inputValue(getAlternativeEmailTB(), altEmail, "Alternative Email TB");
	}

	// Location Details

	public void regionCB(String valueRegion) {
		util.selectByValue(getRegionCB(), valueRegion, "Region CB");
	}

	public void locationCB(String visibleText) {
		util.selectByVisibleText(getLocationCB(), visibleText, "Location TB");
	}

	public void otherLocationTB(String otherLocation) {
		util.inputValue(getOtherLocationTB(), otherLocation, "Other Location TB");
	}

	public void streetAddressTB(String streetAddress) {
		util.inputValue(getStreetAddressTB(), streetAddress, "Street Address TB");
	}

	public void postalAddressTB(String postalAddress) {
		util.inputValue(getPostalAddressTB(), postalAddress, "Postal Address TB");
	}

	public void formSubmitBT() {
		util.click(getSubmitBT(), "Submit Button");
	}

}

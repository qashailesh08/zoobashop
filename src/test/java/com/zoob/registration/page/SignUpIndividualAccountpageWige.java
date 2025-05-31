package com.zoob.registration.page;

import com.zoob.webutils.WebUtil;

public class SignUpIndividualAccountpageWige extends SignUpIndividualAccountOR {

	private WebUtil util;

	public SignUpIndividualAccountpageWige(WebUtil util) {
		super(util);
		this.util = util;
	}

	public void signUpBottun() {

		util.click(getSignUpBT(), "Sign Up Button");
	}

	public void individualAccountLT() {
		util.click(getIndividualAccountLT(), "Individual Account Button");
	}

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

	public void DOBTB(String dob) {
		util.inputValue(getDOBTB(), dob, "DOB TB");
	}

	public void maleCB(String byValue) {
		util.selectByValue(getMaleCB(), byValue, "Male CB");
	}

	public void femaleCB(String byValue) {
		util.selectByValue(getFemaleCB(), byValue, "female CB");
	}
}

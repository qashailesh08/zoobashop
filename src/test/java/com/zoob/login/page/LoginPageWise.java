package com.zoob.login.page;

import com.zoob.webutils.WebUtil;

public class LoginPageWise extends LoginPageOr {

	protected WebUtil util;

	public LoginPageWise(WebUtil util) {
		super(util);
		this.util = util;
	}

	public void enterEmail(String email) {
		util.inputValue(getEmailTB(), email, "Email Text Box");
	}

	public void enterPassword(String password) {
		util.inputValue(getPasswordTB(), password, "Password Text Box");
	}

	public void LoginButton() {
		util.click(getLoginBT(), "Login Button");
	}

	public void errorsms(String errorsms) {
		util.getPageSource(errorsms);
	}
}

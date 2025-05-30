package com.zoob.login.page;

import org.testng.annotations.Test;

import com.zoob.webutils.BaseTest;

public class LoginPageTestScript extends BaseTest {

	@Test
	public void inValidLogin() {
		LoginPageWise loginObj = new LoginPageWise(util);
		loginObj.enterEmail("qa.shailesh08@gmail.com");
		loginObj.enterPassword("0974396329");
		loginObj.LoginButton();
		loginObj.errorsms("Invalid usrname or password, try again");
		util.getPageSource("Don't have an account?");
	}
}

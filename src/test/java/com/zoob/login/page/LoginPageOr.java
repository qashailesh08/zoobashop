package com.zoob.login.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.zoob.webutils.WebUtil;
import lombok.Getter;
@Getter
public class LoginPageOr {

	public LoginPageOr(WebUtil util) {
		PageFactory.initElements(util.getDriver(), this);
	}

	@FindBy(xpath = "//input[@name='email']")
	protected WebElement emailTB;

	@FindBy(name = "password")
	protected WebElement passwordTB;

	@FindBy(xpath = "//input[@value='Login']")
	protected WebElement loginBT;
}

package com.zoob.login.module;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class zoobSignUpScenario {
	@Test
	public void signupinotzs() {
		// Launch ChromeBrowser
		WebDriver driver = new ChromeDriver();
		// Maximize window
		driver.manage().window().maximize();
		// Say driver to wait for 10 second to load the page contents
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		// launch URL
		driver.get("https://www.businessghana.com");
		// do Sign up
		driver.findElement(By.xpath("//span[text()='Sign Up']")).click();
		// select your account type 'Individual Account'
		driver.findElement(By.xpath("//a[text()='Individual Account']")).click();
		// Login Details
		// Last Name
		WebElement weLastNameTB = driver.findElement(By.id("last-name"));
		weLastNameTB.clear();
		weLastNameTB.sendKeys("Patel");
		// First Name
		WebElement weFirstNameTB = driver.findElement(By.xpath("//input[@name='first_name']"));
		weFirstNameTB.clear();
		weFirstNameTB.sendKeys("Shailesh");
		// User Name [Profile Name]
		WebElement weUserNameTB = driver.findElement(By.id("username"));
		weUserNameTB.clear();
		weUserNameTB.sendKeys("Shailesh Kumar");
		// Email
		WebElement WeEmailTB = driver.findElement(By.id("email"));
		WeEmailTB.clear();
		WeEmailTB.sendKeys("engineershailesh226@gmail.com");
		// Password
		WebElement wePasswordTB = driver.findElement(By.id("password"));
		wePasswordTB.clear();
		wePasswordTB.sendKeys("zoobshailesh08");
		// Confirm Password
		WebElement weCMFPassword = driver.findElement(By.id("confirm-password"));
		weCMFPassword.clear();
		weCMFPassword.sendKeys("zoobshailesh08");

	}
}

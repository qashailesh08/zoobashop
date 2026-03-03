package com.zoob.common.practice;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class ZoobAutomation {

	@Test
	public void createIndividualAcc() {
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
		driver.get("https://www.businessghana.com/myaccount/profile/register-individual");
		Select selObj;
		// Gendar
		WebElement weGendar = driver.findElement(By.xpath("//select[@name='gender']"));
		selObj = new Select(weGendar);
		selObj.selectByValue("Male");
		// Country
		WebElement weSelCountry = driver.findElement(By.xpath("//select[@name='country_id']"));
		selObj = new Select(weSelCountry);
		selObj.selectByVisibleText("INDIA");
		// Location Details
		WebElement weRegion = driver.findElement(By.xpath("//select[@id='region']"));
		selObj = new Select(weRegion);
		selObj.selectByValue("5");
		// Location
//		WebElement weLocation=driver.findElement(By.xpath("//select[@id='city']"));
//		selObj=new Select(weLocation);
//		selObj.selectByValue("5");
//		@ No Option implemeted here.

	}
}

package com.zoob.webutils;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtil {
	protected WebDriver driver;
	protected Actions act;
	protected WebDriverWait driverWait;

	public WaitUtil(WebDriver driver, int timeouts) {
		this.driver = driver;
		this.driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeouts));

	}

	public void waitForAlert() {
		try {
			driverWait.until(ExpectedConditions.alertIsPresent());
		} catch (TimeoutException e) {
			System.out.println("No alert appeared within wait time.");
		} catch (UnhandledAlertException e) {
			Alert alert = driver.switchTo().alert();
			alert.accept(); // or alert.dismiss()
		} catch (NoAlertPresentException e) {
			System.out.println("Alert not present: " + e.getMessage());
		} catch (RuntimeException r) {
			r.getMessage();
		}
	}

	public void visibilityOfElementLocated(By locator) {
		try {
			driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (NoSuchElementException e) {
			driverWait = new WebDriverWait(driver, Duration.ofMinutes(10));
		} catch (StaleElementReferenceException e) {
			driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (ElementNotInteractableException e) {
			driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			e.getMessage();
		}
	}

	// 1. Wait until element is clickable
	public WebElement elementToBeClickable(By locator) {
		try {
			return driverWait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (TimeoutException e) {
			System.out.println("Timeout: Element not clickable - " + locator);
		} catch (Exception e) {
			System.out.println("Error in elementToBeClickable: " + e.getMessage());
		}
		return null;
	}

	// 2. Wait until element is selected
	public boolean elementToBeSelected(By locator) {
		try {
			return driverWait.until(ExpectedConditions.elementToBeSelected(locator));
		} catch (TimeoutException e) {
			System.out.println("Timeout: Element not selected - " + locator);
		} catch (Exception e) {
			System.out.println("Error in elementToBeSelected: " + e.getMessage());
		}
		return false;
	}

	// 3. Wait until frame is available and switch to it
	public void frameToBeAvailableAndSwitchToIt(By locator) {
		try {
			driverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
			System.out.println("Switched to frame: " + locator);
		} catch (TimeoutException e) {
			System.out.println("Timeout: Frame not available - " + locator);
		} catch (NoSuchFrameException e) {
			System.out.println("No such frame: " + locator);
		} catch (Exception e) {
			System.out.println("Error in frameToBeAvailableAndSwitchToIt: " + e.getMessage());
		}
	}

	// 4. Wait until text is present in the element located by the given locator
	public boolean textToBePresentInElementLocated(By locator, String text) {
		try {
			return driverWait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
		} catch (TimeoutException e) {
			System.out.println("Timeout: Text '" + text + "' not present in element - " + locator);
		} catch (Exception e) {
			System.out.println("Error in textToBePresentInElementLocated: " + e.getMessage());
		}
		return false;
	}
}

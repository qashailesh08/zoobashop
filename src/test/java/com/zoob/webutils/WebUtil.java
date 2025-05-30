package com.zoob.webutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;

public class WebUtil {
	public WebDriver driver = null;
	private static ExtentReports exReportObj;
	private ExtentTest exTestObj = null;
	private static WebUtil utilObj = null;
	private Actions actObj = null;

	private static Select sel;
	private static ThreadLocal<WebDriver> poolDriver = new ThreadLocal<>();

	public WebDriver getDriver() {
		driver = poolDriver.get();
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		poolDriver.set(driver);
	}

//	public static void removeTL() {
//		poolDriver.remove();
//	}

	/*
	 * This private constructor stop to create multiple object creation of this
	 * class , this allowed only one time user can create an object of this class ,
	 * that is known as `Singleton` class
	 * 
	 * @parameter: no @parameter
	 * 
	 * @return: constructor having no any return type even void
	 */
	private WebUtil() {

	}
	/*
	 * By this method we are getting WebUtility class object .
	 * 
	 * @parameter: no @parameter
	 * 
	 * @return: WebUtility
	 */

	public static WebUtil getInstance() {
		if (utilObj == null) {
			utilObj = new WebUtil();
		}

		return utilObj;
	}

	/*
	 * By this method we can implements Scenario name and we can also create a new
	 * file , where all test case will be reported.
	 * 
	 * @parameter: String
	 * 
	 * @return: no return type
	 */
	public synchronized static void generateReport() {
//		if (exReportObj == null) {
		exReportObj = new ExtentReports();
//		}
		File filesource = new File("Reports");
		if (!filesource.exists()) {
			filesource.mkdir();
		}
		DateFormat dfObj = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss a");
		String timeStamp = dfObj.format(new Date());
		ExtentSparkReporter sparkReport = new ExtentSparkReporter("Reports\\" + "ZoobShop.html" + "," + timeStamp);
		exReportObj.attachReporter(sparkReport);

	}

	private static ThreadLocal<ExtentTest> poolReport = new ThreadLocal<ExtentTest>();

	public ExtentTest getExtentTest() {
		exTestObj = poolReport.get();
		return exTestObj;
	}

	public void createReport(String testCaseName) {
		if (exReportObj == null) {
			generateReport();
		}

//		if (exTestObj == null) {
		poolReport.set(exReportObj.createTest(testCaseName));
		exTestObj = getExtentTest();
//		}

	}

	/*
	 * if we want to launch browser by user then this method is more suitable for
	 * launching browser. And by taking input from user we can launch browser baesd
	 * on customer choice.
	 * 
	 * @ parameter: no parameter
	 * 
	 * @ return: no return type
	 */
	public void launchBrowser(String browserName) {
		try {
			switch (browserName) {
			case "chromebrowser":
				poolDriver.set(new ChromeDriver());
				getExtentTest().log(Status.INFO, browserName + ": launched succesfully");
				break;
			case "firebrowser":
				poolDriver.set(new FirefoxDriver());
				getExtentTest().log(Status.INFO, browserName + ": launched succesfully");
				break;
			case "edgebrowser":
				poolDriver.set(new EdgeDriver());
				getExtentTest().log(Status.INFO, browserName + ": launched succesfully");
				break;
			case "safaribrowser":
				poolDriver.set(new SafariDriver());
				getExtentTest().log(Status.INFO, browserName + ": launched succesfully");

			}
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Failed To Launched Browser");
			throw e;
		}

	}

	/*
	 * By launchUrl method we can launch any application by that app url, for this
	 * we have to pass application url in this method parameter
	 * 
	 * @parameter: String
	 * 
	 * @return: no return
	 * 
	 * 
	 */
	public void openUrl(String url) {
		try {
			getDriver().get(url);
			getExtentTest().log(Status.INFO, url + ": launched succesfully");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not launch : " + url);
			throw e;
		}
	}

	/*
	 * # By This method we can take screen shot of any particullar element. also
	 * with full information
	 * 
	 * @ parameter: String
	 * 
	 * @ return: no return
	 * 
	 */
	public void addcapture(String elemetName) {
		getExtentTest().addScreenCaptureFromPath(getSnapShote(elemetName));
		getExtentTest().log(Status.INFO, "Screenshot took succesfully");
	}

	/*
	 * By this method we can take screen shot of any particular element
	 * 
	 * @parameter: String
	 * 
	 * @return: String
	 * 
	 */
	public String getSnapShote(String elementName) {
//		DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh_mm_ss a");
//		String timestamp = df.format(new Date());
//		TakesScreenshot ss = (TakesScreenshot) driver;
//		File sourceOfImage = ss.getScreenshotAs(OutputType.FILE);
//		File folder = new File("snapShotes");
//		if (!folder.exists()) {
//			System.out.println(folder.mkdir());
//			getExtentTest().log(Status.INFO, "Directory created succesfully");
//		}
//		File destfolderObj = new File("snapShotes\\" + elementName + "" + timestamp + ".png");
//		// png , jpg, jpeg , bmp, gifs
//		try {
//			Files.copy(sourceOfImage, destfolderObj);
//			getExtentTest().log(Status.INFO, "ScreenShote took succesfully of: " + elementName);
//		} catch (IOException e) {
//			getExtentTest().log(Status.FAIL, "Did not take screenshot ");
//			e.printStackTrace();
//		}
//		String myPath = destfolderObj.getAbsolutePath();
//		return myPath;
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh_mm_ss a");
		String timestamp = df.format(new Date());
		TakesScreenshot ss = (TakesScreenshot) driver;
		File sourceOfImage = ss.getScreenshotAs(OutputType.FILE);

		// Create folder if it doesn't exist
		File folder = new File("snapShotes");
		if (!folder.exists()) {
			folder.mkdir();
			getExtentTest().log(Status.INFO, "Directory created successfully");
		}

		// Sanitize and shorten elementName
		String safeElementName = driver.getTitle().replaceAll("[^a-zA-Z0-9-_\\.]", "_");
		if (safeElementName.length() > 50) {
			safeElementName = safeElementName.substring(0, 50);
		}

		File destfolderObj = new File("snapShotes" + File.separator + safeElementName + "_" + timestamp + ".png");

		try {
			Files.copy(sourceOfImage, destfolderObj);
			getExtentTest().log(Status.INFO, "Screenshot taken successfully of: " + safeElementName);
		} catch (IOException e) {
			getExtentTest().log(Status.FAIL, "Did not take screenshot");
			e.printStackTrace();
		}

		String myPath = destfolderObj.getAbsolutePath();
		return myPath;

	}
	/*
	 * Dimension class methods
	 */

	public <X> void getScreenShotASWebElement(WebElement web, String elementName, String DirectoryName) {
		SimpleDateFormat date = new SimpleDateFormat("dd-mm-yyyy , hh-mm,sss - a");
		String timestump = date.format(new Date());
		try {

			File sourcOfIamge = web.getScreenshotAs(OutputType.FILE);
			File destinationOfIame = new File("" + DirectoryName + "" + elementName + " " + timestump + ".png");
			if (destinationOfIame.exists() == false) {
				System.out.println(destinationOfIame.mkdir());
				getExtentTest().log(Status.INFO, "Directory craeted succesfully");
			}
			try {
				Files.copy(sourcOfIamge, destinationOfIame);
				getExtentTest().log(Status.INFO, "ScreenShot took succesfully of: " + elementName);
			} catch (IOException e) {
				Files.copy(sourcOfIamge, destinationOfIame);
				getExtentTest().log(Status.INFO,
						"ScreenShot took succesfully of: " + elementName + " after handling IOExcpetion");
			}
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not take ScreenShot of: " + elementName);
		}
	}

	/*
	 * # By this method we can verify String actual value with expected value .
	 * 
	 * @ parameter: String , String
	 * 
	 * @ return: no return
	 *
	 */
	public void stringValidation(String actulaValue, String excpectedValue, String elementName) {
		if (actulaValue.equals(excpectedValue)) {
			getExtentTest().log(Status.PASS,
					"actulaValue: '" + actulaValue + "' matched with excpectedValue: " + excpectedValue);
		} else {
			addcapture(elementName);
			getExtentTest().log(Status.FAIL,
					"actulaValue: '" + actulaValue + "' not matched with excpectedValue: '" + excpectedValue);
		}
	}

	/*
	 * # By this method if we want to verify actual value with expected value we can
	 * use this (only for int). and for this, this method need two type parameter
	 * 
	 * @parameter: int , int
	 * 
	 * @return: no return
	 * 
	 */
	public void intValidation(int actulaIndex, int expectedIndex) {
		if (actulaIndex == expectedIndex) {
			getExtentTest().log(Status.INFO, "matched actual: " + actulaIndex + " , expected: " + expectedIndex);
		} else {
			getExtentTest().log(Status.WARNING, "not matched actual: " + actulaIndex + " , expected: " + expectedIndex);
		}
	}

	// WebDriver class methods
	/*
	 * # This is a method of JavaScriptExecutor and hear I did down casting of
	 * JavaScriptExecutor with WebDriver Interface.and after that I called
	 * JavaScriptExecutor method that is 'executeScript' . by this method we can
	 * perform any actions(such as sendKeys, click , clear and on..) by pass is code
	 * in this this method parameter
	 * 
	 * @Param: String jsCode , WebElement return: Object
	 * 
	 * 
	 */

	public Object executeScript(String jsCode, WebElement element) {
		JavascriptExecutor exc = (JavascriptExecutor) getDriver();
		Object obj = exc.executeScript(jsCode, element);
		return obj;
	}

	/*
	 * By this method we can flush executed script report.
	 * 
	 * @parameter: no parameter
	 * 
	 * @return: no return type
	 */
	public void flushReport() {
		exReportObj.flush();
		getExtentTest().log(Status.INFO, "Test Case Flushed");
	}

	/* JavaScriptExecutor methods */
	///////////////
	/*
	 * #By jsScrollToElement method we can performed scroll action on any element.
	 * JS work internally on HTML (Hyper Text Mark up language) code . the final
	 * solution is the JavaScriptExecutor
	 * 
	 * @param: By
	 * 
	 * @return: no return
	 * 
	 */
	public void jsScrollToElement(WebElement web, String elementName) {
		try {
			jsMouseOver(web, elementName);
			executeScript("arguments[0].scrollIntoView(true);", web);
			getExtentTest().log(Status.INFO, " js scroll actions performed succesfully on-[" + elementName + "]-" + "");
		} catch (IllegalArgumentException e) {
			getExtentTest().log(Status.FAIL, "Please set driver");
		} catch (JavascriptException e) {
			jsMouseOver(web, elementName);
			executeScript("arguments[0].scrollIntoView(true);", web);
			getExtentTest().log(Status.INFO, "jsScrollToELement action performed succesfully on-[" + elementName + "]-"
					+ " , after hadling JavascriptException");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not perform jsScrollToElement action");
		}

	}

	/*
	 * # By jsClick method we can perform click action on spesific any element. for
	 * this we #have to two type parameter first that element WebElemet Object ,On
	 * which element #we want to perform click actions .
	 * 
	 * @parameter: By , String
	 * 
	 * @return: no return
	 * 
	 */

	public void jsClick(WebElement web, String elementName) {

		try {
			jsMouseOver(web, elementName);
			executeScript("arguments[0].click()", web);
			getExtentTest().log(Status.INFO, "jsClick action performed succesfully on-[" + elementName + "]-");
		} catch (JavascriptException e) {
			jsMouseOver(web, elementName);
			executeScript("arguments[0].click()", web);
			getExtentTest().log(Status.INFO, "jsClick action performed succesfully on-[" + elementName + "]-"
					+ " ' after handling JavascriptExcpetion");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not perform jsClick action ");
			throw e;
		}
	}

	/*
	 * #By jsSendKeys method we can send any input in the text box , for this, this
	 * #method need two type parameter ,first js sendKeys code , and in second
	 * parameter we #have to pass WebElement Object where we want to send value.
	 * 
	 * @Param:String , By
	 * 
	 * @return:no return
	 */
	public void jssendKeys(WebElement web, String input, String elementName) {
		try {
			jsMouseOver(web, elementName);
			executeScript("arguments[0].value ='" + input + "';", web);
			getExtentTest().log(Status.INFO, "jssendKeys action performed succesfully on-[" + elementName + "]-");
		} catch (JavascriptException e) {
			jsMouseOver(web, elementName);
			executeScript("arguments[0].value ='" + input + "';", web);
			getExtentTest().log(Status.INFO, "jssendKeys action performed succesfully on-[" + elementName + "]-"
					+ " , after handling JavascriptException");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not perform jssendKeys action ");
			throw e;
		}
	}

	/*
	 * # By jsClear method we can clear any values from the text box . for this in
	 * #first we have to pass js clear code in this method parameter,and in second
	 * parameter #we have to pass that element WebElement Object, where we want to
	 * perform #clear action
	 * 
	 * @parameter: String , By
	 * 
	 * @return: Object
	 */
	public Object jsClear(WebElement web, String elementName) {
		Object obj = null;
		try {
			jsMouseOver(web, elementName);
			executeScript("arguments[0].value ='';", web);
			getExtentTest().log(Status.INFO, "jsClear action performed succesfully");
		} catch (JavascriptException e) {
			jsMouseOver(web, elementName);
			executeScript("arguments[0].value ='';", web);
			getExtentTest().log(Status.INFO, "jsClear action performed succesfully after handling JavascriptExcpetion");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not perform jsClear action");
			throw e;
		}
		return obj;
	}

	/*
	 * If selenium getText method is not working , then we should go for javaScript
	 * and using javascript getText code we can find text.
	 * 
	 * @parameter: By , String
	 * 
	 * @return: Object
	 */
	public Object jsGetText(WebElement web, String elementName) {
		Object obj = null;
		try {
			jsMouseOver(web, elementName);
			obj = executeScript("return arguments[0].innerText;", web);
			getExtentTest().log(Status.INFO, "jsGetText action performed succesfully on-[" + elementName + "]-");
		} catch (JavascriptException e) {
			jsMouseOver(web, elementName);
			executeScript("return arguments[0].innerText;", web);
			getExtentTest().log(Status.INFO, "jsGetText action performed succesfully on-[" + elementName + "]-"
					+ " after hanlidng JavascriptException");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not perform jsGetTest action on-[" + elementName + "]-");
			throw e;
		}
		return obj;
	}

	/* Actions class Java script code */
	/*
	 * #By jsMouseOver method we can perform mouse over action on any spesific
	 * #element. for this we have to pass that element WebElement Object
	 * 
	 * @parameter: By , String
	 * 
	 * @return: Object
	 * 
	 */
	public Object jsMouseOver(WebElement web, String elementName) {
		Object obj = null;
		try {
			obj = executeScript("var element = arguments[0];"
					+ "var mouseEventObj = document.createEvent('MouseEvents');"
					+ "mouseEventObj.initEvent( 'mouseover', true, true );" + "element.dispatchEvent(mouseEventObj);",
					web);
			getExtentTest().log(Status.INFO, "jsMouseOver action performed succesfully on-[" + elementName + "]-");
		} catch (JavascriptException e) {
			executeScript("var element = arguments[0];" + "var mouseEventObj = document.createEvent('MouseEvents');"
					+ "mouseEventObj.initEvent( 'mouseover', true, true );" + "element.dispatchEvent(mouseEventObj);",
					web);
			getExtentTest().log(Status.INFO,
					"jsMouseOver action performed succesfully after handling JavascriptException");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not performe jsMouseOver action on-[" + elementName + "]-");
			throw e;
		}
		return obj;

	}

	/*
	 * # By this method we can perform Double click action on any particular element
	 * #. for this we have to pass that element WebElement Object in this method
	 * parameter.
	 * 
	 * @parameter:By , String
	 * 
	 * @return: Object
	 * 
	 * 
	 */
	public Object jsDoubleClick(WebElement web, String elementName) {

		Object obj = null;
		try {
			jsMouseOver(web, elementName);
			obj = executeScript("var evt = document.createEvent('MouseEvents');"
					+ "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
					+ "arguments[0].dispatchEvent(evt);", web);
			getExtentTest().log(Status.INFO, "jsDoubleClick action performed succesfully");
		} catch (IllegalArgumentException e) {
			getExtentTest().log(Status.INFO, "Please Set Driver");
			throw e;
		} catch (NullPointerException e) {
			getExtentTest().log(Status.INFO, "Variable has null value");
			throw e;

		} catch (JavascriptException e) {
			jsMouseOver(web, elementName);
			doubleClick(web, elementName);
			executeScript("var evt = document.createEvent('MouseEvents');"
					+ "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
					+ "arguments[0].dispatchEvent(evt);", web);
			getExtentTest().log(Status.INFO, "jsDoubleClick action performed succesfully");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not perform jsDoubleClick action");
			throw e;
		}
		return obj;
	}

	/*
	 * #By this methos we can perform right click action.for this we have to find
	 * #that element WebElement object in this method parameter.
	 * 
	 * @parameter: By ,String
	 * 
	 * @return: no return
	 * 
	 */
	public void jsContextClick(WebElement web, String elementName) {

		actObj = new Actions(driver);
		actObj.contextClick(web).build().perform();

	}

	/*
	 * By this we can get current URL of that application which will opened
	 * 
	 * @parameter: no argu..
	 * 
	 * @return: String
	 * 
	 * 
	 */

	public String getCurrentURL() {
		String currentURL;
		try {
			currentURL = driver.getCurrentUrl();
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "URL not found");
			throw e;
		}
		return currentURL;
	}

	/*
	 * By this method we can search any element based on that element locator from
	 * the page but at one type we can search only one element . and if locator will
	 * wrong then will throw 'NoSuchElementException' and if locator Syntex will
	 * wrong then 'InvalidSelectorException'.
	 * 
	 * @parameter: By , String
	 * 
	 * @return: WebElement
	 */
//	public WebElement searchElement(WebElement web, String elementName) {
//		WebElement weElement = null;
//		try {
//			weElement = driver.findElement(locator);
//			exTest.log(Status.INFO, "Element found succesfully by locator: " + locator);
//		} catch (NullPointerException e) {
//			exTest.log(Status.FAIL, "Variable has null value");
//			throw e;
//		} catch (NoSuchElementException e) {
//			weElement = driver.findElement(locator);
//			exTest.log(Status.INFO, "Element found succesfully by locator: " + locator);
//		} catch (InvalidSelectorException e) {
//			exTest.log(Status.FAIL, locator + " Syntex is wrong");
//			throw e;
//		}
//		return weElement;
//	}

	/*
	 * By this method we can search more element at one time . and if locator will
	 * wrong then it will never throw 'NoSuchElementException' but if locator syntex
	 * will wrong then will throw 'InvalidSelectorException'.
	 * 
	 * @parameter: By String
	 * 
	 * @return: List<WebElement>
	 * 
	 */
//	public List<WebElement> searchElements(WebElement web, String elementName) {
//		List<WebElement> liWe = null;
//		try {
//			liWe = driver.findElements(locator);
//			exTest.log(Status.INFO, elementName + " found succesfully by locator: " + locator);
//		} catch (NullPointerException e) {
//			liWe = driver.findElements(locator);
//			exTest.log(Status.INFO, elementName + " found after handling NullPointerException");
//		} catch (StaleElementReferenceException e) {
//			exTest.log(Status.INFO, elementName + " found after handling StaleElementReferenceException");
//		} catch (InvalidSelectorException e) {
//			exTest.log(Status.INFO, elementName + " found after handling InvalidSelectorException");
//		}
//		return liWe;
//	}

	/*
	 * # By this method we can find size of any elements.for this we have to pass
	 * ,that element xPath.By this we can also know that how many element present on
	 * the page.
	 * 
	 * @return: int
	 * 
	 * @parameter: By , String
	 * 
	 */
	public int size(List<WebElement> web, String elementName) {
		int size = 0;
		try {
			size = web.size();
			getExtentTest().log(Status.INFO, "Totle size found succesfully");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not find size");
			throw e;
		}

		return size;
	}

	public void tableDatas(List<WebElement> web, String elementName) {
		String text = null;
		try {
			for (int i = 0; i < web.size(); i++) {
				WebElement we = web.get(i);
				text = we.getText();
				System.out.println(text);
			}
		} catch (Exception e) {
			System.out.println("Unable to find table data of- " + elementName);
			throw e;
		}
	}
	/*
	 * # By this method we can close our present opened window.But Still May Raise
	 * ,Exception After Closing The Window . such as 'SocketException'.
	 * 
	 * @return: no return
	 * 
	 * @parameter: no argu..
	 */

	public void closeWindow() {
		driver.close();
	}

	/*
	 * # By this method we can quit our corrent opened window. quit() will close
	 * corrent window and won't throw any Exceptoin.
	 * 
	 * @return: no return type
	 * 
	 * @parameter: long
	 */
	public void quit(long time) {

		try {
			Thread.sleep(time);
			getExtentTest().log(Status.INFO, "Browser Closed");
		} catch (InterruptedException e) {
			getExtentTest().log(Status.FAIL, "Did'n close browser");
			e.printStackTrace();
		}

		getDriver().quit();
//		removeTL();
	}
	/*
	 * @ Frame Handling methods
	 */
	/*
	 * By this method we can hanlde frame based on the frame id or name .
	 * 
	 * @ parameter: String
	 * 
	 * @ return: WebDriver
	 */

	public WebDriver handleFrame(String nameOrID) {
		WebDriver webDriver = null;
		try {
			webDriver = driver.switchTo().frame(nameOrID);
			getExtentTest().log(Status.INFO, "driver focus switched on frame succesfully");
		} catch (NoSuchFrameException e) {
			webDriver = driver.switchTo().frame(nameOrID);
			getExtentTest().log(Status.INFO,
					"driver focus switched on frame succesfully after handling 'NoSuchFrameException'");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not switch driver focus on the window");
			throw e;
		}
		return webDriver;
	}

	/*
	 * # By this we can handle any frame based on the frame index for the , first we
	 * have to switch driver focus usning 'switchTo()' and the select any frame
	 * based on the Index.
	 * 
	 * @retur: WebDriver
	 * 
	 * @parameter: int
	 */
	public WebDriver handleFrame(int index) {
		WebDriver webDriver = null;
		try {
			webDriver = driver.switchTo().frame(index);
			getExtentTest().log(Status.INFO, "driver focus switched on frame succesfully");
		} catch (NoSuchFrameException e) {
			webDriver = driver.switchTo().frame(index);
			getExtentTest().log(Status.INFO,
					"driver focus switched on frame succesfully after handling 'NoSuchFrameException'");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not switch driver focus on the window");
			throw e;
		}
		return webDriver;
	}

	/*
	 * By this method we can hanlde frame by passing that frame WebElement Object.
	 * 
	 * @parameter: By , String
	 * 
	 * @return: WebDriver
	 */
	public WebDriver handleFrame(WebElement web, String frameName) {
		WebDriver webDriver = null;
		try {
			webDriver = driver.switchTo().frame(web);
			getExtentTest().log(Status.INFO, "driver focus switched on frame succesfully");
		} catch (NoSuchFrameException e) {
			webDriver = driver.switchTo().frame(web);
			getExtentTest().log(Status.INFO,
					"driver focus switched on frame succesfully after handling 'NoSuchFrameException'");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not switch driver focus on the window");
			throw e;
		}
		return webDriver;
	}

	/*
	 * If we have opened many winodw and want to work on the main window then by
	 * this methodd we can switch driver focus on the main window directily.
	 * 
	 * @parameter: no parameter
	 * 
	 * @return: WebDriver
	 */
	public WebDriver defaultContent() {
		WebDriver webDriver = null;
		try {
			webDriver = driver.switchTo().defaultContent();
			getExtentTest().log(Status.INFO, "driver focus switched succesfully on the main window");
		} catch (IllegalArgumentException e) {
			getExtentTest().log(Status.WARNING, "Please set driver");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not switch driver focus on the main window ");
			throw e;
		}
		return webDriver;
	}

	/*
	 * If we want find current page source of the page then we can use it to find
	 * the current page source of the page .
	 * 
	 * @parameter: no parameter
	 * 
	 * @return: String
	 */
	public String getPageSource(String actualText) {
		String pageSource = null;
		try {
			pageSource = driver.getPageSource();
			if (pageSource.contains(actualText)) {
				getExtentTest().log(Status.PASS, "Page source contains text- " + actualText);
			} else {
				getExtentTest().log(Status.FAIL, "Page source not contains text- " + actualText);
				getSnapShote(pageSource);
			}

		} catch (IllegalArgumentException e) {
			getExtentTest().log(Status.WARNING, "Please set driver");
			getSnapShote(pageSource);
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not Find page source");
			getSnapShote(pageSource);
			throw e;
		}
		return pageSource;
	}

	/*
	 * By this method we can get opened window hanlde value. and by hanlde value we
	 * can switch driver focus on that window eassily.
	 * 
	 * @parameter: no parameter
	 * 
	 * @return: String
	 * 
	 */
	public String getWindowHandle() {
		String window = null;
		try {
			window = driver.getWindowHandle();
			getExtentTest().log(Status.INFO, "Window hanlde value found succesfully");
		} catch (IllegalArgumentException e) {
			getExtentTest().log(Status.WARNING, "Please set driver");

			throw e;
		} catch (NoSuchWindowException e) {
			window = driver.getWindowHandle();
			getExtentTest().log(Status.INFO,
					"Window hanlde value found succesfully after handling 'NoSuchWindowException'");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not Find Window hanlde value");
			throw e;
		}
		return window;
	}

	/*
	 * If we want to set window in fullScreen then by this method we can do it .
	 * 
	 * @parameter: no parameter
	 * 
	 * @return: no return type
	 */
	public void fullScreen() {
		try {
			driver.manage().window().fullscreen();
			getExtentTest().log(Status.INFO, "Window set on fullScreen Succesfully");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not set window on fullscreen");
			throw e;
		}
	}

	/*
	 * By this method we can set window in minimize .
	 * 
	 * @parameter: no parameter:
	 * 
	 * @return: no return type
	 */
	public void minimize() {
		try {
			driver.manage().window().minimize();
			getExtentTest().log(Status.INFO, "Window minimized Succesfully");
		} catch (IllegalArgumentException e) {
			getExtentTest().log(Status.FAIL, "Please set driver");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not minimize window");
			throw e;
		}
	}

	/*
	 * If we want to maximize window then by this method we can do it .
	 * 
	 * @parameter: no parameter
	 * 
	 * @return: no return type
	 */
	public void maxWindow() {
		try {
			poolDriver.get().manage().window().maximize();
			getExtentTest().log(Status.INFO, "Window maximized Succesfully");
		} catch (IllegalArgumentException e) {
			getExtentTest().log(Status.FAIL, "Please set driver");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not maximize window");
			throw e;
		}
	}

	/*
	 * By this method we can hanle multiple window's by these window hanlde value.
	 * 
	 * @ parameter: no parameter
	 * 
	 * @ return: no return type
	 */
	public void getWindowHandldes(String expectedWinTitle) {
		try {
			Set<String> listWind = driver.getWindowHandles();
			for (String singleWindow : listWind) {
				driver.switchTo().window(singleWindow);
				String actualWinTitle = driver.getTitle();
				if (actualWinTitle.equals(expectedWinTitle)) {
					break;
				}
			}
		} catch (NoSuchWindowException e) {
			Set<String> listWind = driver.getWindowHandles();
			for (String singleWindow : listWind) {
				driver.switchTo().window(singleWindow);
				String actualWinTitle = driver.getTitle();
				if (actualWinTitle.equals(expectedWinTitle)) {
					getExtentTest().log(Status.PASS,
							" [ " + actualWinTitle + " ] matched with [ " + expectedWinTitle + " ]");
					break;
				}
			}
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not find windowes");
			throw e;
		}
	}

	/*
	 * By this method we can send input in any specific text box and to send value
	 * in the text box , first we will have to find that element locator.
	 * 
	 * @parameter: By , String , String
	 * 
	 * @return: no return type
	 */

	public void inputValue(WebElement web, String input, String elementName) {

		try {
			clear(web, elementName);
			web.sendKeys(input);
			getExtentTest().log(Status.INFO, " sendKeys actions performe succefully on-[" + elementName + "]-");
		} catch (ElementClickInterceptedException e) {
			jssendKeys(web, input, elementName);
			getExtentTest().log(Status.INFO,
					" after handling ElementClickInterceptedException jsSendKeys actions performed succesfully on: "
							+ elementName);
			addcapture(elementName);
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, " Did not perform sendKeys action on-[ " + elementName + " ]" + "");
			throw e;
		}
	}

	/*
	 * By this method we can perform click action on any specific element by passing
	 * that element locator.
	 * 
	 * @parameter: By , String
	 * 
	 * @return: no return type
	 */

	public void click(WebElement web, String elementName) {

		try {
			web.click();
			getExtentTest().log(Status.INFO, " click actions performed succesfully on-[" + elementName + "]-");
		} catch (ElementNotInteractableException e) {
			jsClick(web, elementName);
			getExtentTest().log(Status.WARNING,
					" after handling ElementClickInterceptedException jsClick actions performed succesfully on: "
							+ elementName);
			addcapture(elementName);
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Failed to perform click action on-[" + elementName + "]-");
			throw e;
		}

	}

	/*
	 * By clear method we can clear any input from the text box.
	 * 
	 * @parameter: By , String
	 * 
	 * @return: no return
	 */
	public void clear(WebElement web, String elementName) {
		try {
			web.clear();
			getExtentTest().log(Status.INFO, "clear actions performed succesfully on: " + elementName);
		} catch (ElementClickInterceptedException e) {
			jsClear(web, elementName);
			getExtentTest().log(Status.INFO,
					"jsClear actions performed succesfully after handling ElementClickInterceptedException on: "
							+ elementName);
			addcapture(elementName);
		} catch (Exception e) {
			addcapture(elementName);
			getExtentTest().log(Status.FAIL, "Did not perform clear action on: " + elementName);
			throw e;
		}

	}

	public String getText(WebElement we, String elementName) {
		String text = null;
		try {
			text = we.getText();
			getExtentTest().log(Status.INFO, "Text Found Of -: " + elementName);
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Text not Found Of -: " + elementName);
			getSnapShote(elementName);
			throw e;
		}

		return text;
	}

	/*
	 * @By getLoccation we can get current element location
	 * 
	 * @Param By locater
	 * 
	 * @getLocation method return Point class Object
	 * 
	 * 
	 */

	public void getLocation(WebElement web, String elementName) {

		try {

			int x = web.getLocation().getX();
			int y = web.getLocation().getY();
			getExtentTest().log(Status.INFO, "location found of: " + elementName + " : x: " + x + " , y:" + y);
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not found location of: " + elementName);
			throw e;
		}

	}

	/* Point class method */
	/*
	 * With help of this method we can find any element size
	 * 
	 * @parameter: By , String
	 * 
	 * @return: no return
	 */
	public void getSize(WebElement web, String elementName) {

		try {

			int height = web.getSize().getHeight();
			int width = web.getSize().getWidth();
			getExtentTest().log(Status.INFO,
					"Size found of: " + elementName + " height: " + height + " , width: " + width);
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not find size of: " + elementName);
			throw e;
		}

	}

	/*
	 * Actions class methods
	 */
	/*
	 * By this method we can perform click action on any particullar element
	 * 
	 * @parameter: By , String
	 * 
	 * @return: Actions
	 */
	public void clickActions(WebElement web, String elementName) {
		actObj = new Actions(driver);
		try {

			actObj.click(web).build().perform();
			;
			getExtentTest().log(Status.INFO, "Actions click action performed succesfully");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not perform Actions click action");
			throw e;
		}
	}

	/*
	 * By this method we can perform click and hold action . this will click till
	 * mouse focus will chnage
	 * 
	 * @parameter: By , String
	 * 
	 * @return: Actions
	 * 
	 */
	public void clickAndHold(WebElement web, String elementName) {
		actObj = new Actions(driver);

		try {
			actObj.clickAndHold(web).build();
			getExtentTest().log(Status.INFO, "Actions clickAndHold action performed succesfully");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not perform Actions clickAndHold action");
			throw e;
		}
	}

	/*
	 * By this method we can perform right click action on any particullar element
	 * which you want
	 * 
	 * @parameter: By , String
	 * 
	 * @return: Actions
	 */
	public void contextClick(WebElement web, String elementName) {
		actObj = new Actions(driver);

		try {

			actObj.contextClick(web).build().perform();
			getExtentTest().log(Status.INFO, "Actions contextClick action performed succesfully");
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not perform Actions contextClick action");
			throw e;
		}
	}

	/*
	 * By this method we can send input in the text box
	 * 
	 * @parameter: By , String , String
	 * 
	 * @return: Actions
	 * 
	 */
	public void sendKeys(WebElement web, String input, String elementName) {
		actObj = new Actions(driver);
		try {
			actObj.sendKeys(web, input).build().perform();
			;
			getExtentTest().log(Status.INFO, "Actions sendKeys action performed succesfully");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not perform Actions sendKeys action");
			throw e;
		}
	}

	/*
	 * By this method we can perform double click action on any particullar element
	 * which you want.
	 * 
	 * @parameter: By , String
	 * 
	 * @return: Actions
	 */
	public void doubleClick(WebElement web, String elementName) {
		actObj = new Actions(driver);

		try {
			actObj.doubleClick(web).build().perform();
			;
			getExtentTest().log(Status.INFO, " doubleClick action performed succesfully on-[" + elementName + "]-");
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Failed to perform doubleClick Action on-[" + elementName + "]-");
			throw e;
		}
	}

	/*
	 * By this method we can change mouse focus on any element
	 * 
	 * @parameter: By , String
	 * 
	 * @return: Actions
	 */
	public void moveToElement(WebElement web, String elementName) {
		actObj = new Actions(getDriver());

		try {
			actObj.moveToElement(web).build().perform();
			getExtentTest().log(Status.INFO, "Mouse Over action performed succesfully on-[" + elementName + "]-");
		} catch (StaleElementReferenceException e) {
			actObj.moveToElement(web).build().perform();
			getExtentTest().log(Status.INFO, "Mouse Over action performed succesfully on-[" + elementName + "]-"
					+ " after handling 'StaleElementReferenceException'");
		} catch (ElementClickInterceptedException e) {
			jsMouseOver(web, elementName);
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not perform Mouse Over action on-[" + elementName + "]-");
			throw e;
		}
	}

	/*
	 * By this method we can change any element location as you want
	 * 
	 * @parameter: By ,String,By,String return: Actions
	 * 
	 * @return: Actions
	 */
	// @dragAndDrop
	public void dragAndDrop(WebElement sourceLocator, String sourceName, WebElement targetLocator, String targetName) {
		actObj = new Actions(driver);

		try {
			actObj.dragAndDrop(sourceLocator, targetLocator).build().perform();
			getExtentTest().log(Status.INFO, "Actions dragAndDrop action performed succesfully");
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (NullPointerException np) {
			getExtentTest().log(Status.FAIL, "NullPointerException occured while performing dragAndDrop");
			throw np;
		} catch (Exception e) {
			getExtentTest().log(Status.INFO, "Did not perform Actions dragAndDrop action");
			throw e;
		}
	}

	/*
	 * By this method we can perform release action , for example if we hold any
	 * element and we want to release it any other place , by this method we can do
	 * this
	 * 
	 * @parameter: By , String
	 * 
	 * @return: Actions
	 */
	public void release(WebElement web, String elementName) {
		actObj = new Actions(driver);

		try {
			actObj.release(web).build().perform();
			;
			getExtentTest().log(Status.INFO, "Actions release action performed succesfully on-[" + elementName + "]-");
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			getExtentTest().log(Status.INFO, "Did not perform Actions release action");
			throw e;
		}
	}

	/*
	 * By this method we can perform scroll action such as if we want to scroll up
	 * and down , by this method we can do
	 * 
	 * @parameter: By , String
	 * 
	 * @return: Actions
	 */
	public void scrollToElement(WebElement web, String elementName) {
		actObj = new Actions(driver);
		try {
			actObj.scrollToElement(web).build().perform();
			;
			getExtentTest().log(Status.INFO,
					"Actions scrollToElement action performed succesfully on-[" + elementName + "]-");
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			getExtentTest().log(Status.INFO, "Did not perform Actions scrollToElement action");
			throw e;
		}
	}

	/*
	 * # By selectByValue method we can select any value in the dropdwon based on
	 * the attribute value of the dropdown element . for this we have to pass that
	 * drop down WebElement Object
	 * 
	 * @Param: By
	 * 
	 * @return: no return
	 * 
	 */
	public void selectByValue(WebElement web, String value, String elementName) {

		Select sel = new Select(web);
		try {
			sel.selectByValue(value);
			getExtentTest().log(Status.INFO, "Element selected succesfully By selectByValue");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not selected value by selectByValue");
			throw e;

		}
	}
	/*
	 * # By selectByVisisbleText method we can select any value in the dropdwon
	 * Based on what ever text is visible on the UI page. for this we have to pass
	 * that dropdown WebElemement Object in the Select class Constructor
	 * 
	 * @Param: By
	 * 
	 * @reurn: no return
	 * 
	 */

	public void selectByVisibleText(WebElement web, String visiblevlaue, String elementName) {

		Select sel = new Select(web);
		try {
			sel.selectByVisibleText(visiblevlaue);
			getExtentTest().log(Status.INFO, "Element selected succesfully By selectByVisibleText");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not selected value by selectByVisibleText");
			throw e;
		}

	}
	/*
	 * # By selectByIndex methhod we can select any value in the dropdown by passing
	 * index of that element.for this we have to pass that dropdown WebElemement
	 * Object in the Select class Constructor
	 * 
	 * @ Index will start from 0
	 * 
	 * @Param: By
	 * 
	 * @return: no return
	 * 
	 */

	public void selectByIndex(WebElement web, int index, String elementName) {

		Select sel = new Select(web);
		try {
			sel.selectByIndex(index);
			getExtentTest().log(Status.INFO, "Element selected succesfully By selectByIndex");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not selected value by selectByIndex");
			throw e;
		}

	}
	/*
	 * # By deselectByValue method we can deselect value from the dropdown Based on
	 * attribute value of the dropdown element.for this we have to pass that
	 * dropdown WebElemement Object in the Select class Constructor
	 * 
	 * @Param: By
	 * 
	 * @return: no return
	 */

	public void deselectByValue(WebElement web, String value, String elementName) {

		Select sel = new Select(web);
		try {
			sel.deselectByValue(value);
			getExtentTest().log(Status.INFO, "Element deselected  By deselectByValue");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not deselected value by deselectByValue");
			throw e;

		}
	}
	/*
	 * # By deselectByVisibleText method we can deselect value from the dropdown
	 * Based on what ever text is visible on UI.for this we have to pass that
	 * dropdown WebElemement Object in the Select class Constructor
	 * 
	 * @ Param: By
	 * 
	 * @return: no return
	 * 
	 * 
	 */

	public void deselectByVisibleText(WebElement web, String visiblevlaue, String elementName) {

		Select sel = new Select(web);
		try {
			sel.deselectByVisibleText(visiblevlaue);
			getExtentTest().log(Status.INFO, "element deselected succesfully by deselectByVisibleText");
		} catch (Exception e) {
			getExtentTest().log(Status.INFO, "Did not deselected value by deselectByVisibleText");
		}

	}
	/*
	 * # By delectByIndex method we can deselect value from the dropdown Based On
	 * the dropdown Index.for this we have to pass that dropdown WebElemement Object
	 * in the Select class Constructor
	 * 
	 * @Param: By
	 * 
	 * @return: no return
	 * 
	 */

	public void deselectByIndex(WebElement web, int index, String elementName) {
		try {

			Select sel = new Select(web);
			sel.deselectByIndex(index);
			getExtentTest().log(Status.INFO, "element deselected succesfully by deselectByIndex");
		} catch (Exception e) {
			getExtentTest().log(Status.INFO, "Did not deselected value by deselectByIndex");
		}

	}

	/*
	 * # By deselectAll method we can deselect All that selected value form the
	 * dropdwon which are already selected.for this we have to pass that dropdown
	 * WebElemement Object in the Select class Constructor
	 * 
	 * @Note: it will work only for multiple selection dropdown
	 * 
	 * @Param: By Return: no return
	 * 
	 */
	public void deselectAll(WebElement web, String elementName) {

		Select sel = new Select(web);
		try {
			sel.deselectAll();
			getExtentTest().log(Status.INFO, "deselectedAll actions performed succesfully on-[" + elementName + "]-");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "doesn't perform deselectAll action on-[" + elementName + "]-");
			throw e;
		}

	}

	/*
	 * # By this method we can know which Option is selected in the dropdown.for
	 * this we have to pass that dropdown WebElemement Object in the Select class
	 * Constructor
	 * 
	 * @ Param: By
	 * 
	 * @return: String
	 */
	public String getdropdownSelectedOption(WebElement web, String elementName) {

		Select sel = new Select(web);
		String strText = sel.getFirstSelectedOption().getText();
		return strText;
	}
	/*
	 * # By this method we can know how many options are selected in the
	 * dropdown.for this we have to pass that dropdown WebElemement Object in the
	 * Select class Constructor
	 * 
	 * @ Param: By
	 * 
	 * @return: List<String>
	 */

	public List<String> getAllSelectedOption(WebElement web, String elementName) {

		Select sel = new Select(web);
		List<WebElement> liWe = sel.getAllSelectedOptions();
		int count = liWe.size();
		List<String> arrList = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			WebElement weElem = liWe.get(i);
			String strOptions = weElem.getText();
			arrList.add(strOptions);
		}
		return arrList;
	}

	/*
	 * @By this method we can get all options of dropdown.for this we have to pass
	 * that dropdown WebElemement Object in the Select class Constructor
	 * 
	 * @Param: WebElement , String
	 * 
	 * @Return: List<String>
	 */

	public List<String> getOptions(WebElement web, String elementName) {
		Select sel = new Select(web);
		List<WebElement> listWeb = sel.getOptions();
		List<String> arrList = new ArrayList<String>();
		int countElement = listWeb.size();
		for (int i = 0; i < countElement; i++) {
			String strText = listWeb.get(i).getText();
			arrList.add(strText);
		}
		return arrList;
	}

	/*
	 * # By this method we can know that dropdown is Single Selection dropdown Or
	 * Mulitple selection dropdown- if dropdown will single selection then true Else
	 * false.for this we have to pass that dropdown WebElemement Object in the
	 * Select class Constructor
	 * 
	 * @Param: WebElement , String
	 * 
	 * @return: boolean
	 */

	public void isMultiple(WebElement web, String elementName) {
		sel = new Select(web);

		try {
			boolean bln = sel.isMultiple();
			if (bln == true) {
				getExtentTest().log(Status.INFO,
						"dropdown is Single Multi selection dropdown of-[" + elementName + "]-");
			} else {
				getExtentTest().log(Status.INFO,
						"dropdown is Single Single selection dropdownof-[" + elementName + "]-");
			}
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not work isMultiple ");
			throw e;
		}
	}

	/*
	 * # By this method we can select any value in the dropdown whether dropdwon
	 * #values are stable or not else value has spaces.for this we have to pass that
	 * #dropdown WebElemement Object in the Select class Constructor
	 * 
	 * @Param: By
	 * 
	 * @return: no return
	 * 
	 */
	public void selectPartialValueByIndex(WebElement web, String elementName, String partialTextToSelect) {
		List<String> liWebEle = getOptions(web, elementName);
		int countSize = liWebEle.size();
		for (int i = 0; i < countSize; i++) {
			String ddText = liWebEle.get(i);
			if (ddText.contains(partialTextToSelect)) {
				countSize = i;
				break;
			}
		}
		selectByIndex(web, countSize, elementName);
		getExtentTest().log(Status.INFO, "Value selected in dropdown succesfully ");
	}

	///////////////////////// Explicitly Waits////////////////////
	/*
	 * By this method we can pause script execution for some time until the element
	 * is clearly visible on the UI page.
	 * 
	 * @parameter: By , String , String
	 * 
	 * @return: no return type
	 */
	public void waitforElementVisibilty(WebElement web, String elementName) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOfAllElements(web));
			getExtentTest().log(Status.INFO, "Exiplicitly wait applyed succesfully on- " + elementName);
		} catch (TimeoutException e) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
			wait.until(ExpectedConditions.visibilityOfAllElements(web));
			getExtentTest().log(Status.INFO, "Exiplicitly wait applyed succesfully after handling 'TimeoutException'");
		} catch (Exception e) {
			getExtentTest().log(Status.INFO, "Did'n apply Exiplicitly wait on - " + elementName);
		}
	}

	/*
	 * By this method we can pause script execution for some time unitl the element
	 * is not present on the UI page.
	 * 
	 * @parameter: By , String , String
	 * 
	 * @return: no return type
	 */
	public void waitforTextToBePresentinElement(WebElement web, String text, String elementName) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
			wait.until(ExpectedConditions.textToBePresentInElement(web, text));
			getExtentTest().log(Status.INFO, "Exiplicitly wait applyed succesfully on- " + elementName);
		} catch (TimeoutException e) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
			wait.until(ExpectedConditions.textToBePresentInElement(web, text));
			getExtentTest().log(Status.INFO, "Exiplicitly wait applyed succesfully after handling 'TimeoutException'");
		} catch (Exception e) {
			getExtentTest().log(Status.INFO, "Did'n apply Exiplicitly wait on - " + elementName);
			throw e;
		}
	}

	/*
	 * By this method we can pause our script execution for some time until the
	 * element is not to be Clickable. Then on that element we can perform action .
	 * 
	 * @parameter: By , String
	 * 
	 * @return: no return type
	 */
	public void waitforElementToBeClickable(WebElement web, String elementName) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
			wait.until(ExpectedConditions.elementToBeClickable(web));
			getExtentTest().log(Status.INFO, "Exiplicitly wait applyed succesfully on- " + elementName);
		} catch (TimeoutException e) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
			wait.until(ExpectedConditions.elementToBeClickable(web));
			getExtentTest().log(Status.INFO, "Exiplicitly wait applyed succesfully after handling 'TimeoutException'");
		} catch (Exception e) {
			getExtentTest().log(Status.INFO, "Did'n apply Exiplicitly wait on - " + elementName);
			throw e;
		}
	}

	/*
	 * By this method we can pause our script execution for some time until the
	 * element is not to be Selectable. Then on that element we can perform action .
	 * 
	 * @parameter: By , String
	 * 
	 * @return: no return type
	 */
	public void waitforElementToBeSelected(WebElement web, String elementName) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
			wait.until(ExpectedConditions.elementToBeSelected(web));
			getExtentTest().log(Status.INFO, "Exiplicitly wait applyed succesfully on- " + elementName);
		} catch (TimeoutException e) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
			wait.until(ExpectedConditions.elementToBeSelected(web));
			getExtentTest().log(Status.INFO, "Exiplicitly wait applyed succesfully after handling 'TimeoutException'");
		} catch (Exception e) {
			getExtentTest().log(Status.INFO, "Did'n apply Exiplicitly wait on - " + elementName);
			throw e;
		}
	}

	/*
	 * By this method we can check whether check box is checked or not.
	 * 
	 */
	public void checkAllCheckBoxes(List<WebElement> web, String elementName) {
		try {
			for (int i = 0; i < web.size(); i++) {
				Thread.sleep(450);
				WebElement we = web.get(i);
				if (!we.isSelected()) {
					we.click();
				}
			}
			getExtentTest().log(Status.INFO, "All check boxes selected successfully of : " + elementName);
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i < web.size(); i++) {
				WebElement we = web.get(i);
				if (!we.isSelected()) {
					we.click();
				}
			}
			getExtentTest().log(Status.INFO, "All check boxes selected successfully of : " + elementName
					+ " after handling 'StaleElementReferenceException'");
		} catch (ElementClickInterceptedException e) {
			for (int i = 0; i < web.size(); i++) {
				WebElement we = web.get(i);
				if (!we.isSelected()) {
					jsClick(we, elementName);
				}
			}
			getExtentTest().log(Status.INFO, "All check boxes selected successfully of : " + elementName
					+ " after handling 'ElementClickInterceptedException'");
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not checked check boxes of : " + elementName);
		}

	}

	public void unCheckAllCheckBoxes(List<WebElement> web, String elementName) {
		try {
			for (int i = 0; i < web.size(); i++) {
				Thread.sleep(450);
				WebElement we = web.get(i);
				if (we.isSelected()) {
					we.click();
				}
			}
			getExtentTest().log(Status.INFO, "All check boxes unchecked successfully of : " + elementName);
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i < web.size(); i++) {
				WebElement we = web.get(i);
				if (we.isSelected()) {
					we.click();
				}
			}
			getExtentTest().log(Status.INFO, "All check boxes unchecked successfully of : " + elementName
					+ " after handling 'StaleElementReferenceException'");
		} catch (ElementClickInterceptedException e) {
			for (int i = 0; i < web.size(); i++) {
				WebElement we = web.get(i);
				if (we.isSelected()) {
					jsClick(we, elementName);
				}
			}
			getExtentTest().log(Status.INFO, "All check boxes unchecked successfully of : " + elementName
					+ " after handling 'ElementClickInterceptedException'");
		} catch (Exception e) {

			getExtentTest().log(Status.FAIL, "Did not unchecked check boxes of : " + elementName);
		}

	}

	/*
	 * By this method we can handle only browser alerts. for accepting we have to
	 * say only ' ok ' an for dismiss we have to say only ' no '.
	 * 
	 * @param: String
	 * 
	 * @return: no return type
	 */
	public void handleAlert(String yesORno) {
		if (yesORno.contains("ok")) {
			driver.switchTo().alert().accept();
			getExtentTest().log(Status.INFO, "Alert Accepted");
		} else if (yesORno.contains("no")) {
			driver.switchTo().alert().dismiss();
			getExtentTest().log(Status.INFO, "Alert Dismissed");
		}
	}

	/*
	 * If we want to apply wait for a specific element then we can use this method.
	 * this method till wait which time you will be given.
	 * 
	 * @param: long
	 * 
	 * @return: no return type
	 */
	public void staticWait(long time) {
		try {
			Thread.sleep(time);
			getExtentTest().log(Status.INFO, "Static wait applyed successfully");
		} catch (InterruptedException e) {
			getExtentTest().log(Status.FAIL, "Static wait Did not apply");
			e.printStackTrace();
		}
	}

	/*
	 * When ever we are not able to perform scroll by Actions class methods then we
	 * should go for this method by this method we can scroll down.
	 * 
	 * @parameter: int
	 * 
	 * @return: no return type
	 * 
	 */
	public void scrollDown(int scroll) {

		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0," + scroll + ")");
			getExtentTest().log(Status.INFO, "Scroll down action performed successfully");
		} catch (JavascriptException e) {
			throw e;
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Failed to performed Scroll down action");
			throw e;
		}

	}

	/*
	 * When ever we are not able to perform scroll by Actions class methods then we
	 * should go for this method by this method we can scrollup.
	 * 
	 * @parameter: int
	 * 
	 * @return: no return type
	 * 
	 */
	public void scrollUp(int scroll) {

		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0," + -scroll + ")");
			getExtentTest().log(Status.INFO, "Scroll up action performed successfully");
		} catch (JavascriptException e) {
			throw e;
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Failed to performed Scroll up action");
			throw e;
		}

	}

	public void elementToBeClickeable(WebElement element) {
		WebDriverWait webWait = new WebDriverWait(driver, Duration.ofSeconds(30));
		webWait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public void logPass(String actaul, String expected, String elementName) {
		getExtentTest().log(Status.PASS, "Actual Value" + " [ " + actaul + " ] " + "Matched With Expected Value " + "[ "
				+ expected + " ]" + " Of:-" + elementName);
	}

	public void logFail(String actaul, String expected, String elementName) {
		getExtentTest().log(Status.FAIL, "Actual Value" + " [ " + actaul + " ] " + "Not Matched With Expected Value "
				+ "[ " + expected + " ]" + " Of:-" + elementName);
		addcapture(elementName);
	}

	public void logPass(boolean actaul, boolean expected, String elementName) {
		getExtentTest().log(Status.PASS, "Actual Value" + " [ " + actaul + " ] " + "Matched With Expected Value " + "[ "
				+ expected + " ]" + " Of:-" + elementName);
	}

	public void logFail(boolean actaul, boolean expected, String elementName) {
		getExtentTest().log(Status.FAIL, "Actual Value" + " [ " + actaul + " ] " + "Not Matched With Expected Value "
				+ "[ " + expected + " ]" + " Of:-" + elementName);
		addcapture(elementName);
	}

	/*
	 * #By this method we can validate that element tag name , in the param we have
	 * to pass the expected tag name and actual tag name will be fetched
	 * automatically by 'getTagName' method.
	 * 
	 * @Param: WebElement , String , String
	 * 
	 * @return: no return type
	 * 
	 */
	public void verifyElementTagName(WebElement web, String expectedTagName, String element) {

		try {
			String tagName = web.getTagName();
			if (tagName.equals(expectedTagName)) {
				getExtentTest().log(Status.INFO,
						tagName + " :- matched with -: " + expectedTagName + " of : " + element);
			} else {
				getExtentTest().log(Status.FAIL,
						tagName + " :- not matched with -: " + expectedTagName + " of : " + element);
				getSnapShote(element);
			}
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Tagname not found of: " + element);
			getSnapShote(element);
			throw e;
		}
	}

	// |******************** Hear is Verification Related Methods**************
	public void verifyPasswordIsMasked(WebElement web) {
		String actValue = web.getAttribute("type");
		if (actValue.equals("password")) {
			getExtentTest().log(Status.PASS, "Password is masked Of Password text box");
		} else {
			getExtentTest().log(Status.FAIL, "Password is not masked of Password textbox");
		}
	}

	/*
	 * By this method we can validate selected option in the drop down , where
	 * option will be selected , by this method we can verify
	 * 
	 * @Parameter: WebElement web, String expectedValue, String elementName
	 * 
	 * @return: no return type
	 */
	public void verifyFirstSelectedOption(WebElement web, String expectedValue, String elementName) {

		Select sel = new Select(web);
		String actText = sel.getFirstSelectedOption().getText();

		if (actText.equals(expectedValue)) {
			logPass(actText, expectedValue, elementName);
		} else {
			logFail(actText, expectedValue, elementName);
		}

	}

	/*
	 * If we want to verify that current window title is matching with expected
	 * window title then we can use this method to verify the title of the window .
	 * To passing expected title in the method parameter.
	 * 
	 * @parameter: WebElement web,String expectedTitle, String elementName
	 * 
	 * @return: no return type
	 */
	public void verifyTitle(WebElement web, String expectedTitle, String elementName) {

		try {
			String actTitle = getDriver().getTitle();
			if (actTitle.equals(expectedTitle)) {
				logPass(actTitle, expectedTitle, elementName);
			} else {
				logFail(actTitle, expectedTitle, elementName);
			}
		} catch (IllegalArgumentException e) {
			getExtentTest().log(Status.FAIL, "Please set driver");
			getSnapShote(elementName);
			throw e;
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not find Title");
			getSnapShote(elementName);
			throw e;
		}
	}

	/*
	 * By getText method we can get any element inner text.by passing that element
	 * locator.
	 * 
	 * @parameter:WebElement web, String expectedIT, String elementName
	 * 
	 * @return: no return
	 * 
	 */
	public void verifyInnerText(WebElement web, String expectedIT, String elementName) {
		String strText = null;
		try {
			strText = web.getText();
			if (strText.equals(expectedIT)) {
				logPass(strText, expectedIT, elementName);
			} else {
				logFail(strText, expectedIT, elementName);
			}
		} catch (StaleElementReferenceException e) {
			strText = web.getText();
			if (strText.equals(expectedIT)) {
				logPass(strText, expectedIT, elementName);
			} else {
				logFail(strText, expectedIT, elementName);
			}
			getSnapShote(elementName);
			throw e;

		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not found text of the element");
			getSnapShote(elementName);
			throw e;
		}
	}

	/*
	 * By isEnabled method we can check that text box , button .. are enable or not
	 * on UI page. by passing that element locator
	 * 
	 * @parameter: WebElement web, boolean expection, String elementName
	 * 
	 * @return: void
	 */
	public void verifyIsEnabled(WebElement web, boolean expection, String elementName) {
		try {
			boolean actual = web.isEnabled();
			if (actual == expection) {
				logPass(actual, expection, elementName);
			} else {
				logFail(actual, expection, elementName);
			}
		} catch (StaleElementReferenceException e) {
			boolean actual = web.isEnabled();
			if (actual == expection) {
				logPass(actual, expection, elementName);
			} else {
				logFail(actual, expection, elementName);
			}
			addcapture(elementName);
			throw e;
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Did not Verify By verifyIsEnabled method");
			addcapture(elementName);
			throw e;
		}
	}

	/*
	 * By isDisplayed method we can validate that element is is visible on UI page
	 * or not.
	 * 
	 * @parameter: By, String
	 * 
	 * @return: boolean
	 */
	public void verifyDisplayed(WebElement web, boolean expection, String elementName) {
		try {
			boolean blDisplayedStatus = web.isDisplayed();
			if (blDisplayedStatus == expection) {
				logPass(elementName, elementName, elementName);
			} else {
				logFail(blDisplayedStatus, expection, elementName);
			}
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Unable to validate by verifyDisplayed method");
			getSnapShote(elementName);
			throw e;
		}
	}

	/*
	 * By isSelected method we can verify that element/check box is selected or not
	 * but at ones we can validate only one element by this method.
	 * 
	 * @parameter: By, String
	 * 
	 * @return: boolean
	 */
	public boolean verifySelected(WebElement web, boolean expection, String elementName) {
		boolean actual;
		try {
			actual = web.isSelected();
			if (actual == expection) {
				logPass(actual, expection, elementName);
			} else {
				logFail(actual, expection, elementName);
			}
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "Unable to validate by isSelected method");
			getSnapShote(elementName);
			throw e;
		}
		return actual;
	}

	/*
	 * By getAttribute method we can get any element attribute value by passing that
	 * element attribute name in this method parameter.
	 * 
	 * @parameter: By ,String , String
	 * 
	 * @return: String
	 * 
	 */
	public void verifyAttribute(WebElement web, String expectedValue, String attributeName, String elementName) {

		try {
			String actualValue = web.getAttribute(attributeName);
			if (actualValue.equals(expectedValue)) {
				logPass(actualValue, expectedValue, elementName);
			} else {
				logFail(actualValue, expectedValue, elementName);
			}

		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, "attribute value not found of: " + elementName);
			getSnapShote(elementName);
			throw e;
		}
	}

	// Apache POI

//	public static void getExcelData() {
//		Map<String, String> mapData = null;
//		try {
//			InputStream is = new FileInputStream("ExcelData\\Login.xlsx");
//			Workbook wook = new XSSFWorkbook(is);
//			Sheet sheetObj = wook.getSheet("Login");
//			int countR = sheetObj.getLastRowNum();
////			Row rowObj = sheetObj.getRow(0);
//			for (int i = 0; i < countR; i++) {
//				Row rowObj = sheetObj.getRow(i);
//				int countCell = rowObj.getLastCellNum();
//				for (int j = 0; j < countCell; j++) {
//					Cell cellObj = rowObj.getCell(j);
//					System.out.println(cellObj.getStringCellValue());
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}

}

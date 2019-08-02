/*
  Class to initialize all page methods for the actions available
  under that page. All scripts must call the respective methods from the respective
  pages to achieve any action.

  @author 360Logica
 * @since 1.0
 *
 * @version 1.0
 */
package in.amazon.selenium.core;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.common.base.Function;

public abstract class BasePage {

	protected static final int DEFAULT_WAIT_4_ELEMENT = 30;
//	protected static final int DEFAULT_WAIT_4_PAGE = 15;
	protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
//	protected static WebDriverWait ajaxWait;
	protected WebDriver driver;
//	protected String title;
	protected long timeout = 30;

//	static String resultPath;
//	public static ExtentTest test;
//	public static BaseTest baseTest;
//	protected DateCalendar dateCalendarRef = new DateCalendar();
	private WebElement lastElem = null;

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Move to Particular Element
	 * 
	 * @param element
	 */
	public void moveToElement(WebElement element) {
		waitForElement(element);
		Actions actn = new Actions(driver);
		actn.moveToElement(element).build().perform();
		javascripctHilightingElement(element);
		reportInfo();
		unhighLightElement();
	}

	/**
	 * Click on Particular Element
	 * 
	 * @param element
	 */
	public void clickOn(WebElement element) {
		moveToElement(element);
		element.click();
	}

	/**
	 * Capture screenshot
	 */
	public static void reportInfo() {
		try {
			BaseTest.captureScreenshots();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function: Highlight the WebElement and capture screenshot of the event
	 * 
	 * @param WebElement
	 * 
	 */
	public void javascripctHilightingElement(WebElement webElement) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 3px solid red;')", webElement);
		lastElem = webElement;
	}

	/**
	 * Function: UnHighlight the WebElement and capture screenshot of the event
	 * 
	 * @param WebElement
	 * 
	 */
	public void unhighLightElement() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 0px;');", lastElem);

	}

	/** Input text as string */
	public void inputText(WebElement element, String text) {
		waitForWebElementEnable(element, 10);
		try {
			element.clear();
		} catch (NoSuchElementException e) {
			_normalWait(2000);
			element.clear();
		}
		element.sendKeys(text);
	}

	/**
	 * Wait for webelement enable
	 * 
	 * @param element
	 */
	public WebElement waitForWebElementEnable(WebElement webElement, int timeOutInSeconds) {
		try {
			new FluentWait<WebElement>(webElement).withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
					.pollingEvery(10, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
					.ignoring(ElementNotVisibleException.class).ignoring(ElementNotInteractableException.class)
					.ignoring(TimeoutException.class).ignoring(ElementNotFoundException.class)
					.until(new Function<WebElement, Boolean>() {
						@Override
						public Boolean apply(WebElement element) {
							return element.isDisplayed();
						}
					});

		} catch (Exception e) {

		}
		return null;
	}

	/** Wait for element to be present */
	public void waitForElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/** normal wait for thread. */
	public void _normalWait(long timeOut) {
		try {
			Thread.sleep(timeOut);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/** Return driver instance */
	public WebDriver getDriver() {
		return driver;
	}

	/** Find webelement */
	public WebElement findElement(By by) {
		WebElement foundElement;

		if (driver instanceof ChromeDriver || driver instanceof InternetExplorerDriver) {
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (int millis = 0; millis < 3000; millis = millis + 200) {
			try {
				foundElement = driver.findElement(by);
				return foundElement;
			} catch (Exception e) {
				// Utils.hardWaitMilliSeconds(200);
			}
		}
		return null;
	}

	/** Use assert by page title */
	public void assertByPageTitle(String TitleToBeVerified) {
		boolean flag = false;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			if (driver instanceof ChromeDriver || driver instanceof InternetExplorerDriver
					|| driver instanceof FirefoxDriver) {
				Thread.sleep(3000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		wait.until(ExpectedConditions.titleContains(TitleToBeVerified));

		if (driver.getTitle().contains(TitleToBeVerified)) {
			flag = true;
		}

		Assert.assertTrue(flag);
	}

	/** Wait for element to be clickable */

	public WebElement waitForElementClickable(WebElement webElement, int timeOutInSeconds) {
		WebElement element;
		try {
			// setWaitTimeToZero(driver);
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			element = wait.until(ExpectedConditions.elementToBeClickable(webElement));

			// setWaitTime(driver, DEFAULT_WAIT_4_ELEMENT);
			return element;

		} catch (Exception e) {
		}
		return null;
	}

	/** Wait for element to be present by web element */
	public WebElement waitForElementPresent(WebElement webElement, int timeOutInSeconds) {
		WebElement element;
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			element = wait.until(ExpectedConditions.visibilityOf(webElement));
			return element;
		} catch (Exception e) {

		}
		return null;
	}

	/** Verify that element is present */
	public Boolean isElementPresent(WebElement element) {
		waitForElement(element);
		return element.isDisplayed();
	}

	/** Verify that element is Displayed */
	public boolean isElementDisplayed(WebElement element) {
		boolean flag = false;
		try {
			if (element.isDisplayed())
				;
			flag = true;
		} catch (Exception e) {
		}
		return flag;
	}

	/**
	 * @param toBeHovered
	 * @param toBeClicked
	 * @return
	 */
	public WebDriver hoverOverElementAndClick(WebElement toBeHovered, WebElement toBeClicked) {
		Actions builder = new Actions(driver);
		builder.moveToElement(toBeHovered).build().perform();
		waitForElementPresent(toBeClicked, DEFAULT_WAIT_4_ELEMENT);
		toBeClicked.click();
		waitForPageLoaded();
		return driver;
	}

	/**
	 * Wait for page to load
	 * 
	 * @param driver
	 */
	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = driver -> ((JavascriptExecutor) driver)
				.executeScript("return document.readyState").equals("complete");
		Wait<WebDriver> wait = new WebDriverWait(driver, 20);
		wait.until(expectation);
	}

	/** Store text from a locator */
	public String getText(WebElement element) {
		Assert.assertTrue(element.isDisplayed(), "Element Locator :" + element + " Not found");
		return element.getText();
	}

	/** open a new tab and invoke url */
	public String openApplicationInNewWindowTab(String appURL) {
		String parent = null, child;
		_normalWait(2000);
		try {
			Robot robObj = new Robot();
			robObj.keyPress(KeyEvent.VK_CONTROL);
			robObj.keyPress(KeyEvent.VK_T);
			robObj.keyRelease(KeyEvent.VK_T);
			robObj.keyRelease(KeyEvent.VK_CONTROL);
			_normalWait(1000);
			Set<String> windows = getDriver().getWindowHandles();
			Iterator<String> iterator = windows.iterator();
			parent = iterator.next();
			child = iterator.next();
			getDriver().switchTo().window(child);
			getDriver().navigate().to(appURL);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return parent;
	}

	/**
	 * @return
	 * @function: Get any attribute value of an element
	 * @param: Webelement
	 *             and Attribute name
	 * 
	 */
	public String getAttributeValueOfElement(WebElement element, String attributeName) {
		isElementPresent(element);
		String attributeValue = element.getAttribute(attributeName);
		return attributeValue;
	}

	/**
	 * Convert Sting value to Integer value
	 * 
	 */
	public int parseStringValueIntoInteger(String StringToBeEntered) {
		int result = Integer.parseInt(StringToBeEntered);
		return result;
	}

	/*
	 * This method going to perform enter action one the webelement
	 * 
	 * @param element
	 */

	public void performEnter(WebElement element) {
		element.sendKeys(Keys.ENTER);
	}

	/**
	 * Switch To window
	 * 
	 * @throws InterruptedException
	 */
	public void switchToWindow(String Win) throws InterruptedException {
		Thread.sleep(2000);
		getDriver().switchTo().window(Win);
	}

}

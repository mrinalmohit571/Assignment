package in.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import in.amazon.selenium.core.BasePage;
import in.amazon.utilities.Constants;

public class YahooLoginPage extends BasePage {

	public YahooLoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "input[id='login-username']")
	private WebElement emailInput;

	@FindBy(css = "input[id='login-signin']")
	private WebElement nextButton;

	@FindBy(xpath = "//a[contains(text(),'Yes, sign me')]")
	private WebElement signOutButton;

	/**
	 * verify Yahoo login Page Displayed
	 */
	public void verifyYahoologinPageDisplayed() {
		waitForPageLoaded();
		try {
			assertByPageTitle(Constants.pageTitle_YahoologinPage);
			reportInfo();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * Enter Registered Email
	 */
	public void enterEmail(String EnterRegisteredEmail) {
		waitForElementPresent(emailInput, 5);
		inputText(emailInput, EnterRegisteredEmail);
		reportInfo();

	}

	/**
	 * Proceed By Entering Email
	 */
	public YahooPasswordPage proceedByEnteringEmail() {
		performEnter(emailInput);
		return PageFactory.initElements(driver, YahooPasswordPage.class);
	}

	/**
	 * Click on sign Out Button
	 */
	public YahooHomePage clickOnSignOutButton() {
		try {
			clickOn(signOutButton);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return PageFactory.initElements(driver, YahooHomePage.class);
	}

}

package in.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import in.amazon.selenium.core.BasePage;
import in.amazon.utilities.Constants;

public class YahooPasswordPage extends BasePage {

	public YahooPasswordPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "input[id='login-passwd']")
	private WebElement passwordInput;

	@FindBy(css = "input[id='login-signin']")
	private WebElement signInButton;

	/**
	 * verify Yahoo Password Page Displayed
	 */
	public void verifyYahooPasswordPageDisplayed() {
		waitForPageLoaded();
		assertByPageTitle(Constants.pageTitle_YahooPasswordPage);
		reportInfo();
	}

	/**
	 * Enter Password
	 */
	public void enterPassword(String EnterPassword) {
		waitForElementPresent(passwordInput, 5);
		inputText(passwordInput, EnterPassword);
		reportInfo();
	}

	/**
	 * Proceed By Entering Password
	 */
	public YahooHomePage proceedByEnteringPassword() {
		performEnter(passwordInput);
		return PageFactory.initElements(driver, YahooHomePage.class);
	}

}

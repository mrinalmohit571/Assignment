package in.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import in.amazon.selenium.core.BasePage;
import in.amazon.utilities.Constants;

public class AmazonLoginPage extends BasePage {

	public AmazonLoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "input[id='ap_email']")
	private WebElement emailInput;

	@FindBy(css = "input[id='continue']")
	private WebElement continueButton;

	/**
	 * verify Amazon Sign In Page Displayed
	 */
	public void verifyAmazonSignInPageDisplayed() {
		waitForPageLoaded();
		assertByPageTitle(Constants.pageTitle_AmazonSignInPage);
		reportInfo();
	}

	/**
	 * Enter Text Under Search Box
	 */
	public void enterTextUnderSearchBox(String EnterRegisteredEmail) {
		waitForElementPresent(emailInput, 5);
		inputText(emailInput, EnterRegisteredEmail);
		reportInfo();
	}

	/**
	 * Click on continue Button
	 */
	public <T> T clickOnContinueButton(final Class<T> className) {
		clickOn(continueButton);
		return PageFactory.initElements(driver, className);
	}

}

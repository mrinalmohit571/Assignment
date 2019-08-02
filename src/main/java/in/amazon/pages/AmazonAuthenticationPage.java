package in.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import in.amazon.selenium.core.BasePage;
import in.amazon.utilities.Constants;

public class AmazonAuthenticationPage extends BasePage {

	public AmazonAuthenticationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "input[name='code']")
	private WebElement enterOTPInput;

	@FindBy(xpath = "//span[contains(text(),'Continue')]/preceding-sibling::input[@type='submit']")
	private WebElement continueButton;

	/**
	 * verify Amazon Authentication Page Displayed
	 */
	public void verifyAmazonAuthenticationPageDisplayed() {
		waitForPageLoaded();
		assertByPageTitle(Constants.pageTitle_AmazonAuthenticationPage);
		reportInfo();
	}

	/**
	 * Enter OTP
	 */
	public void enterOTP(String EnterOTP) {
		waitForElementPresent(enterOTPInput, 5);
		inputText(enterOTPInput, EnterOTP);
		reportInfo();
	}

	/**
	 * click On Continue Button
	 */
	public HomePage clickOnContinueButton() {
		clickOn(continueButton);
		return PageFactory.initElements(driver, HomePage.class);
	}

}

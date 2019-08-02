package in.amazon.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import in.amazon.selenium.core.BasePage;
import in.amazon.utilities.Constants;

public class YahooMailPage extends BasePage {

	public YahooMailPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[@title='Amazon Authentication']/ancestor::a[@data-test-id ='message-list-item'][@data-test-read='false']")
	private List<WebElement> unreadAuthenticationMails;

	@FindBy(css = "div[data-test-id='message-view-body']")
	private WebElement mailBody;

	@FindBy(xpath = "//p[contains(text(),'To authenticate, please use the following One Time Password (OTP)')]/following-sibling::p")
	private WebElement OTP;

	@FindBy(css = "label[role='presentation']")
	private WebElement accountInfoDropdown;

	@FindBy(xpath = "//span[contains(text(),'Sign out')]/parent::a")
	private WebElement signOutButton;

	/**
	 * verify Yahoo Mail Page Displayed
	 */
	public void verifyYahooMailPageDisplayed() {
		waitForPageLoaded();
		assertByPageTitle(Constants.pageTitle_YahooMailPage);
		reportInfo();
	}

	/**
	 * verify a new Authentication Mail received
	 */
	public void verifyAuthenticationMailRecieved() {
		boolean flag = false;
		if (unreadAuthenticationMails.size() > 0) {
			moveToElement(unreadAuthenticationMails.get(0));
			flag = true;
		}
		Assert.assertTrue(flag);
	}

	/**
	 * click On First Unread Authentication Mail
	 */
	public YahooMailPage clickOnFirstUnreadAuthenticationMail() {
		clickOn(unreadAuthenticationMails.get(0));
		return PageFactory.initElements(driver, this.getClass());
	}

	/**
	 * verify Email Body is Opened
	 */
	public void verifyEmailBodyIsOpened() {
		moveToElement(mailBody);
		Assert.assertTrue(isElementDisplayed(mailBody));
	}

	/**
	 * get OTP from mail
	 * 
	 * @return
	 * 
	 * @return
	 */
	public String getOTPFromMail() {
		waitForElement(OTP);
		String otpNumber = getText(OTP);
		return otpNumber;
	}

	/**
	 * Log out from yahoo account
	 */
	public YahooLoginPage logOutYahoo() {
		clickOn(accountInfoDropdown);
		clickOn(signOutButton);
		return PageFactory.initElements(driver, YahooLoginPage.class);
	}

}

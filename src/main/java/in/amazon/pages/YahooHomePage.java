package in.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import in.amazon.selenium.core.BasePage;
import in.amazon.utilities.Constants;

public class YahooHomePage extends BasePage {

	public YahooHomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "a[id ='uh-mail-link']")
	private WebElement emailLink;

	/**
	 * verify Yahoo Home Page Displayed
	 */
	public void verifyYahooHomePageDisplayed() {
		waitForPageLoaded();
		assertByPageTitle(Constants.pageTitle_YahooHomePage);
		reportInfo();
	}

	/**
	 * Click on email Link
	 */
	public YahooMailPage clickOnEmailLink() {
		clickOn(emailLink);
		return PageFactory.initElements(driver, YahooMailPage.class);
	}

}

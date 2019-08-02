package in.amazon.pages;

import org.openqa.selenium.WebDriver;

import in.amazon.selenium.core.BasePage;
import in.amazon.utilities.Constants;

public class AmazonLoggoutPage extends BasePage {

	public AmazonLoggoutPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * verify Amazon logout Page Displayed
	 */
	public void verifyAmazonlogoutPageDisplayed() {
		waitForPageLoaded();
		assertByPageTitle(Constants.pageTitle_logoutPage);
		reportInfo();
	}

}

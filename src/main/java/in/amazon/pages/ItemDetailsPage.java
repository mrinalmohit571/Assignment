package in.amazon.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import in.amazon.selenium.core.BasePage;
import in.amazon.utilities.Constants;

public class ItemDetailsPage extends BasePage {

	public ItemDetailsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//*[@title='Add to Shopping Cart']")
	private WebElement addToCartButton;

	/**
	 * verify All Detailed Item Pages Displayed In Multiple Tabs
	 * 
	 * @throws InterruptedException
	 */
	public void verifyAllDetailedItemPagesDisplayedInMultipleTabs(List<String> windowList) throws InterruptedException {
		for (int i = 1; i < windowList.size(); i++) {
			switchToWindow(windowList.get(i));
			waitForPageLoaded();
			_normalWait(1000);
			assertByPageTitle(Constants.pageTitle_ItemDetailsPage);
			reportInfo();
		}
	}

	/**
	 * click On Add To Cart Button For Each Item
	 * 
	 * @throws InterruptedException
	 */
	public void clickOnAddToCartButtonForEachItem(List<String> windowList) throws InterruptedException {
		for (int i = 1; i < windowList.size(); i++) {
			switchToWindow(windowList.get(i));
			clickOn(addToCartButton);
		}
	}

}

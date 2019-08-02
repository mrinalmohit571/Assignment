package in.amazon.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import in.amazon.selenium.core.BasePage;
import in.amazon.utilities.Constants;

public class ShoppingCartPage extends BasePage {

	public ShoppingCartPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@data-name='Active Items']/div")
	private List<WebElement> itemListInShoppingBag;

	@FindBy(css = "input[value='Delete']")
	private List<WebElement> deleteButtonForitemListInShoppingBag;

	/**
	 * verify Shopping cart Pages Displayed
	 */
	public void verifyShoppingCartPagedisplayed() {

		waitForPageLoaded();
		assertByPageTitle(Constants.pageTitle_ItemDetailsPage);
		reportInfo();

	}

	/**
	 * verify Added Items In Cart
	 */
	public void verifyAddedItemsInCart(List<String> EnterListOfBestSellerItemsLink) {
		boolean flag = false;
		if (itemListInShoppingBag.size() > 0 && itemListInShoppingBag.size() == EnterListOfBestSellerItemsLink.size()) {
			flag = true;
		}
		Assert.assertTrue(flag);
		reportInfo();
	}

	/**
	 * Clear all items from cart
	 */
	public void clearitemsFromCart() {
		if (deleteButtonForitemListInShoppingBag.size() > 0) {
			for (WebElement delete : deleteButtonForitemListInShoppingBag) {
				clickOn(delete);
			}

		}

	}

}

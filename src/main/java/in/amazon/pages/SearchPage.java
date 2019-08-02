package in.amazon.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import in.amazon.selenium.core.BasePage;
import in.amazon.utilities.Constants;

public class SearchPage extends BasePage {

	public SearchPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[contains(text(),'Best seller')]/ancestor::div[@class='sg-row']/following-sibling::div[@class='sg-row']//h2/a")
	private List<WebElement> bestSelleritemsInSearchedList;

	@FindBy(xpath = "//ul[@class='a-pagination']/li[@class='a-selected']/a")
	private WebElement currentPage;

	@FindBy(xpath = "//ul[@class='a-pagination']//a[contains(text(),'1')]")
	private WebElement firstPage;


	/**
	 * verify Search Page Displayed For Item
	 */
	public void verifySearchedItemPageDisplayed() {
		waitForPageLoaded();
		_normalWait(1000);
		assertByPageTitle(Constants.pageTitle_SearchPage);
		reportInfo();
	}

	/**
	 * click On First Page If Not Displayed
	 */
	public void clickOnFirstPageIfNotDisplayed() {
		moveToElement(currentPage);
		if (parseStringValueIntoInteger(getText(currentPage).trim()) != Constants.PageNo_FirstPage) {
			clickOn(firstPage);
		}
	}

	/**
	 * Get List Of Best Seller Items Displayed
	 * 
	 * @return
	 *
	 */
	public ArrayList<String> getListOfBestSellerItemsDisplayed() {
		boolean flag = false;
		ArrayList<String> itemLinks = new ArrayList<String>();
		if (bestSelleritemsInSearchedList.size() > 0) {
			flag = true;
			for (WebElement item : bestSelleritemsInSearchedList) {
				moveToElement(item);
				String link = getAttributeValueOfElement(item, "href");
				itemLinks.add(link.replaceFirst("https://www.amazon.in", ""));
			}
		}
		if (flag == false) {
			Assert.assertTrue(flag, "No Best Seller Item has been found.");
		}
		return itemLinks;
	}

	/**
	 * click And Open All Best Seller Items Displayed
	 */
	public ItemDetailsPage clickAndOpenAllBestSellerItemsDisplayed(List<String> EnterListOfBestSellerItemsLink) {
		boolean flag = false;
		if (EnterListOfBestSellerItemsLink.size() > 0) {
			flag = true;
			for (String itemlink : EnterListOfBestSellerItemsLink) {
				WebElement item = findElement(By.xpath("//h2/a[@href='" + itemlink + "']"));
				clickOn(item);
			}
		}
		Assert.assertTrue(flag, "Bestseller list is not available.");
		return PageFactory.initElements(driver, ItemDetailsPage.class);
	}


}

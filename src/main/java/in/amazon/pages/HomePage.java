package in.amazon.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import in.amazon.selenium.core.BasePage;
import in.amazon.utilities.Constants;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[contains(text(),'Hello. Sign in')]/parent::a")
	private WebElement loginButton;

	@FindBy(xpath = "//input[@id = 'twotabsearchtextbox']")
	private WebElement searchBoxInput;

	@FindBy(xpath = "//input[@type='submit'][@value = 'Go']")
	private WebElement searchButton;

	@FindBy(css = "a[id='nav-cart']")
	private WebElement shoppingCartButton;

	@FindBy(css = "a[id='nav-link-accountList']")
	private WebElement accountDropDown;

	@FindBy(css = "a[id='nav-item-signout']")
	private WebElement signOut;

	/**
	 * verify Amazon Home Page Displayed
	 */
	public void verifyAmazonHomePageDisplayed() {
		waitForPageLoaded();
		assertByPageTitle(Constants.pageTitle_HomePage);
		reportInfo();
	}

	/**
	 * Open Email In Next Tab
	 * 
	 * @return
	 */
	public List<Object> openEmailInNextTab(String EmailURl, final Class <?> className) {
		String parentPageid = openApplicationInNewWindowTab(EmailURl);
		return Arrays.asList(parentPageid, PageFactory.initElements(driver, className));
	}

	/**
	 * Click on login Button
	 */
	public AmazonLoginPage clickOnLoginButton() {
		clickOn(loginButton);
		return PageFactory.initElements(driver, AmazonLoginPage.class);
	}

	/**
	 * Verify Search box displayed
	 */
	public void verifySearchBoxDisplayed() {
		waitForElementClickable(searchBoxInput, 3);
		Assert.assertTrue(isElementDisplayed(searchBoxInput));
		moveToElement(searchBoxInput);
	}

	/**
	 * Enter Text Under Search Box
	 */
	public void enterTextUnderSearchBox(String EnterStringToBeSearched) {
		waitForElementPresent(searchBoxInput, 5);
		inputText(searchBoxInput, EnterStringToBeSearched);
		reportInfo();
	}

	/**
	 * Click on search button
	 */
	public SearchPage clickOnSearchButton() {
		clickOn(searchButton);
		return PageFactory.initElements(driver, SearchPage.class);
	}

	/**
	 * click On Shopping Cart Button
	 */
	public ShoppingCartPage clickOnShoppingCartButton() {
		clickOn(shoppingCartButton);
		return PageFactory.initElements(driver, ShoppingCartPage.class);
	}

	/**
	 * click On Shopping Cart Button
	 */
	public AmazonLoggoutPage logOut() {
		hoverOverElementAndClick(accountDropDown, signOut);
		return PageFactory.initElements(driver, AmazonLoggoutPage.class);
	}

}

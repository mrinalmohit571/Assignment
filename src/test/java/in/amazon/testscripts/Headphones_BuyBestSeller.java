package in.amazon.testscripts;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import in.amazon.pages.AmazonAuthenticationPage;
import in.amazon.pages.AmazonLoginPage;
import in.amazon.pages.YahooLoginPage;
import in.amazon.selenium.core.BaseTest;
import in.amazon.selenium.core.Configuration;
import in.amazon.selenium.dataproviders.DataProviders;

public class Headphones_BuyBestSeller extends BaseTest {

	protected String emailUrl, AmazonPageWin, headphones, NewPassword, RegisteredEmailId, incorrectData = "*", OTP;

	protected List<Object> parentPageAndEmailPage;
	protected List<String> AllWin, BestSellerItemsLink;

	@Factory(dataProvider = "Browsers", dataProviderClass = DataProviders.class)
	public Headphones_BuyBestSeller(String browser) {
		super(browser);
	}

	@BeforeMethod
	public void getTestData() throws Exception {

		System.setProperty("className", getClass().getSimpleName());

		emailUrl = Configuration.readApplicationFile("yahooUrl");
		headphones = RegressionTestData.getProperty("Headphones");

		reportLog("PR1: Login to Amazon and Clearing Cart items as a Pre-requisites");

		reportLog("PR2: Verify Application is launched and 'Amazon Home' screen is displayed.");
		homePage.verifyAmazonHomePageDisplayed();

		reportLog("PR3: Open Email In Next Tab");
		parentPageAndEmailPage = homePage.openEmailInNextTab(emailUrl, YahooLoginPage.class);
		AmazonPageWin = (String) parentPageAndEmailPage.get(0);
		yahooLoginPage = (YahooLoginPage) parentPageAndEmailPage.get(1);

		reportLog("PR4: verify Yahoo login Page Displayed");
		yahooLoginPage.verifyYahoologinPageDisplayed();

		reportLog("PR5: Enter Registered Email");
		yahooLoginPage.enterEmail(UserEmail);

		reportLog("PR6: Proceed By Entering Email");
		yahooPasswordPage = yahooLoginPage.proceedByEnteringEmail();

		reportLog("PR7: verify Yahoo Password Page Displayed");
		yahooPasswordPage.verifyYahooPasswordPageDisplayed();

		reportLog("PR8: Enter Password for yahoo account.");
		yahooPasswordPage.enterPassword(Password_Yahoo);

		reportLog("PR9: Proceed By Entering Password");
		yahooHomePage = yahooPasswordPage.proceedByEnteringPassword();

		reportLog("PR10: verify Yahoo Home Page Displayed");
		yahooHomePage.verifyYahooHomePageDisplayed();

		reportLog("PR11: Switch to parent window and verify Amazon Home Page Displayed");
		switchToWindow(AmazonPageWin);
		homePage.verifyAmazonHomePageDisplayed();

		reportLog("PR12: Click on login Button");
		amazonLoginPage = homePage.clickOnLoginButton();

		reportLog("PR13: verify Amazon Sign In Page Displayed");
		amazonLoginPage.verifyAmazonSignInPageDisplayed();

		reportLog("PR14: Enter registered email Under Search Box");
		amazonLoginPage.enterTextUnderSearchBox(UserEmail);

		reportLog("PR15: Click on continue Button");
		amazonLoginPage.clickOnContinueButton(AmazonLoginPage.class);

		reportLog("PR16: verify Amazon Sign In Page Displayed");
		amazonLoginPage.verifyAmazonSignInPageDisplayed();

		reportLog("PR17: Click on continue Button");
		amazonAuthenticationPage = amazonLoginPage.clickOnContinueButton(AmazonAuthenticationPage.class);

		reportLog("PR18: verify Amazon Authentication Page Displayed");
		amazonAuthenticationPage.verifyAmazonAuthenticationPageDisplayed();

		reportLog("PR19: switch To Child Window and verify Inbox Page Displayed.");
		AmazonPageWin = switchToChildWindow();
		yahooHomePage.verifyYahooHomePageDisplayed();

		reportLog("PR20: Click on email Link");
		yahooMailPage = yahooHomePage.clickOnEmailLink();

		reportLog("PR21: verify Yahoo Mail Page Displayed");
		yahooMailPage.verifyYahooMailPageDisplayed();

		reportLog("PR22: verify a new Authentication Mail received");
		yahooMailPage.verifyAuthenticationMailRecieved();

		reportLog("PR23: Click On First Unread Authentication Mail");
		yahooMailPage.clickOnFirstUnreadAuthenticationMail();

		reportLog("PR24: verify Yahoo Mail Page Displayed");
		yahooMailPage.verifyYahooMailPageDisplayed();

		reportLog("PR25: verify Email Body is Opened");
		yahooMailPage.verifyEmailBodyIsOpened();

		reportLog("PR26: get OTP from mail");
		OTP = yahooMailPage.getOTPFromMail();

		reportLog("PR27: Log out from yahoo account");
		yahooMailPage.logOutYahoo();

		reportLog("PR28: verify Yahoo login Page Displayed");
		yahooLoginPage.verifyYahoologinPageDisplayed();

		reportLog("PR29: Click on sign Out Button");
		yahooLoginPage.clickOnSignOutButton();

		reportLog("PR30: verify Yahoo Home Page Displayed");
		yahooHomePage.verifyYahooHomePageDisplayed();

		reportLog("PR31: Switch to parent window and verify Amazon Authentication Page Displayed");
		switchParentWindowByClosingChild(AmazonPageWin);
		amazonAuthenticationPage.verifyAmazonAuthenticationPageDisplayed();

		reportLog("PR32: Enter OTP");
		amazonAuthenticationPage.enterOTP(OTP);

		reportLog("PR33: click On Continue Button");
		amazonAuthenticationPage.clickOnContinueButton();

		reportLog("PR34: verify Amazon Home Page Displayed");
		homePage.verifyAmazonHomePageDisplayed();

		reportLog("PR35: click On Shopping Cart Button");
		shoppingCartPage = homePage.clickOnShoppingCartButton();

		reportLog("PR36: verify Shopping cart Pages Displayed");
		shoppingCartPage.verifyShoppingCartPagedisplayed();

		reportLog("PR37: Clear all items from cart");
		shoppingCartPage.clearitemsFromCart();

	}

	/**
	 * ====================================================================================================================
	 * Test Case Name: Buy best seller Headphones
	 * ====================================================================================================================
	 * 
	 * @throws Exception
	 */

	@Test(description = "Buy best seller Headphones", groups = { "" })
	public void headphones_BuyBestSeller() throws Exception {

		reportLog("1: Verify Search box displayed.");
		homePage.verifySearchBoxDisplayed();

		reportLog("2: Enter text as '" + headphones + "' in search box.");
		homePage.enterTextUnderSearchBox(headphones);

		reportLog("3: Click on Search Button.");
		searchPage = homePage.clickOnSearchButton();

		reportLog("4: Verify Searched Item page displayed.");
		searchPage.verifySearchedItemPageDisplayed();

		reportLog("5: click On First Page If Not Displayed");
		searchPage.clickOnFirstPageIfNotDisplayed();

		reportLog("6: Get List Of Best Seller Items Displayed");
		BestSellerItemsLink = searchPage.getListOfBestSellerItemsDisplayed();

		reportLog("7: click And Open All Best Seller Items Displayed");
		itemDetailsPage = searchPage.clickAndOpenAllBestSellerItemsDisplayed(BestSellerItemsLink);

		reportLog("8: verify All the Detailed Item Pages Displayed In Multiple Tabs");
		AllWin = getAllWindowsOpenInUI();
		itemDetailsPage.verifyAllDetailedItemPagesDisplayedInMultipleTabs(AllWin);

		reportLog("9: click On Add To Cart Button For Each Item");
		itemDetailsPage.clickOnAddToCartButtonForEachItem(AllWin);

		reportLog("10: verify Search Page Displayed For Item");
		switchToWindow(AmazonPageWin);
		searchPage.verifySearchedItemPageDisplayed();

		reportLog("11: click On Shopping Cart Button");
		homePage.clickOnShoppingCartButton();

		reportLog("12: verify Shopping cart Pages Displayed");
		shoppingCartPage.verifyShoppingCartPagedisplayed();

		reportLog("13: verify Added Items In Cart.");
		shoppingCartPage.verifyAddedItemsInCart(BestSellerItemsLink);

		reportLog("14: Logged out.");
		homePage.logOut();

	}

}
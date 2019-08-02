/*
  Class to initialize all application page objects and manage WebDriver browser
  object. Each and every test script class must extend this. This class does
  not use any of the Selenium APIs directly, and adds support to make this
  framework tool independent.
 */
package in.amazon.selenium.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import in.amazon.pages.AmazonAuthenticationPage;
import in.amazon.pages.AmazonLoginPage;
import in.amazon.pages.HomePage;
import in.amazon.pages.ItemDetailsPage;
import in.amazon.pages.SearchPage;
import in.amazon.pages.ShoppingCartPage;
import in.amazon.pages.YahooHomePage;
import in.amazon.pages.YahooLoginPage;
import in.amazon.pages.YahooMailPage;
import in.amazon.pages.YahooPasswordPage;
import in.amazon.selenium.enums.DriverType;
import in.amazon.selenium.report.ScreenRecorder;
import in.amazon.utilities.UserClaims;
import in.amazon.utilities.Utilities;
import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseTest extends ScreenRecorder implements UserClaims {

	public static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
	private static final String BREAK_LINE = "\n";
	public static ExtentTest test;
	public static ExtentReports extent;

	// pages object initialization
	protected HomePage homePage;
	protected AmazonLoginPage amazonLoginPage;
	protected AmazonAuthenticationPage amazonAuthenticationPage;
	protected SearchPage searchPage;
	protected YahooLoginPage yahooLoginPage;
	protected YahooPasswordPage yahooPasswordPage;
	protected YahooHomePage yahooHomePage;
	protected YahooMailPage yahooMailPage;
	protected ItemDetailsPage itemDetailsPage;
	protected ShoppingCartPage shoppingCartPage;

	private static String browserType, applicationUrl, quitBrowser, LaunchBrowser, ChromeBrowser, FirefoxBrowser,
			IEBrowser;
	private static WebDriver driver;
	protected static Properties RegressionTestData;

	public static String resultPath;

	@SuppressWarnings("static-access")
	public BaseTest(String browser) {
		this.browserType = browser;
	}

	@BeforeSuite
	public void before() throws Exception {
		// Create Result repository for report.
		String timeStamp = getFormattedTimeStamp().replace("-", "").replace(":", "").replace(".", "");
		String path = Utilities.getPath();
		resultPath = path + "/Result/Suite_" + timeStamp;
		new File(resultPath).mkdirs();
		extent = new ExtentReports(resultPath + "/CustomReport.html", true);
		quitBrowser = Configuration.readApplicationFile("closeAndQuitBrowser");

		if (System.getProperty("browser") != null) {
			browserType = System.getProperty("browser");

		}

		if (browserType == null || browserType.equals("true")) {
			if (ChromeBrowser.trim().equals("true")) {
				LaunchBrowser = "Chrome";
			} else if (FirefoxBrowser.trim().equals("true")) {
				LaunchBrowser = "Firefox";
			} else if (IEBrowser.trim().equals("true")) {
				LaunchBrowser = "IE";
			} else {
				throw new Exception("Please pass a valid browser type value");
			}
		}

	}

	@SuppressWarnings("static-access")
	@BeforeMethod

	public void setUp(Method method) throws Exception {

		if (browserType == null || browserType.equalsIgnoreCase("")) {
			browserType = LaunchBrowser;
		}
		
		this.applicationUrl = Configuration.readApplicationFile("amazonURL");

		if (DriverType.Firefox.toString().toLowerCase().equals(browserType.toLowerCase())) {
			WebDriverManager.firefoxdriver().setup();
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			driver = new FirefoxDriver();
		} else if (DriverType.IE.toString().toLowerCase().equals(browserType.toLowerCase())) {

			WebDriverManager.iedriver().setup();

			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability("nativeEvents", false);
			driver = new InternetExplorerDriver(capabilities);
		} else if (DriverType.Chrome.toString().toLowerCase().equals(browserType.toLowerCase())) {

			WebDriverManager.chromedriver().setup();

			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			options.addArguments("disable-infobars");
			options.addArguments("--no-sandbox");
			options.setExperimentalOption("prefs", chromePrefs);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(cap);

		} else {
			throw new Exception("Please pass a valid browser type value");
		}

		/**
		 * Maximize window
		 */
		driver.manage().window().maximize();

		/**
		 * Delete cookies and set timeout
		 */
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		/**
		 * Open application URL
		 */
		getWebDriver().navigate().to(applicationUrl);

		/**
		 * Initialize the reporting object
		 */
		setReportTest(method);
		homePage = PageFactory.initElements(getWebDriver(), HomePage.class);

		/**
		 * Set property file
		 */

		RegressionTestData = Configuration.readTestData("RegressionTestData");

	}

	@AfterMethod(alwaysRun = true)
	public void afterMainMethod(ITestResult result) throws IOException, InterruptedException {
		if (result.getStatus() == ITestResult.FAILURE) {
			try {
				test.log(LogStatus.FAIL, "Failed test step is: " + result.getName() + " => " + getMessage());
				test.log(LogStatus.FAIL, result.getThrowable());
				captureScreenshot(result);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (!(result.getStatus() == (ITestResult.SUCCESS) || result.getStatus() == (ITestResult.FAILURE))) {
			try {
				test.log(LogStatus.SKIP, "Skipped Test Case is: " + result.getName() + " => " + getMessage());
				captureScreenshot(result);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		extent.endTest(test);
		if (quitBrowser.equals("true")) {
			driver.quit();
		}

	}

	public void setReportTest(Method method) {
		System.setProperty("testcaseName", method.getName().toString());
		test = extent.startTest(method.getName(), this.getClass().getName());
		test.assignCategory(this.getClass().getSimpleName());

	}

	@AfterSuite(alwaysRun = true)
	public void tearDownSuite() throws IOException {
		boolean direExists = false;

		File dest = new File(Utilities.getPath() + "/target/");
		File resultSourceFolder = new File(Utilities.getPath() + "/Result");

		extent.flush();

		// Copy report to target directory
		File source = new File(resultPath + "/CustomReport.html");
		if (dest.exists()) {
			try {
				FileUtils.cleanDirectory(dest);
			} catch (Exception e) {

			}
		}

		File ExtentReportsource = new File(resultPath);
		File ExtentReportDest = new File(Utilities.getPath() + "/target/ExtentReports/");
		if (ExtentReportDest.exists()) {
			ExtentReportDest.deleteOnExit();
		}
		if (!(ExtentReportDest.exists())) {
			ExtentReportDest.mkdir();
			direExists = true;
			if (direExists == true) {

				try {
					FileUtils.copyDirectory(ExtentReportsource, ExtentReportDest);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		try {
			FileUtils.cleanDirectory(resultSourceFolder);
		} catch (Exception e) {

		}

		String timeStamp = getFormattedTimeStamp().replace("-", "").replace(":", "").replace(".", "").replaceAll(" ",
				"");
		String sourcePath = Utilities.getPath() + "/target/";
		File sourceDir = new File(sourcePath + "/surefire/");
		try {
			FileUtils.deleteDirectory(sourceDir);
		} catch (Exception e) {

		}

		// Copy Archive Reports on Desktop
		File reportPath_Dekstop = new File(
				System.getProperty("user.home") + "/Desktop/ArchiveReports_Mrinal/AmazonShopping_" + timeStamp + "/");
		if (!(reportPath_Dekstop.exists())) {
			reportPath_Dekstop.mkdirs();
			try {
				FileUtils.copyDirectory(ExtentReportDest, reportPath_Dekstop);
			} catch (IOException e2) {

			}

		}
	}

	public WebDriver getWebDriver() {
		return driver;
	}

	/**
	 * Get All windows open in UI
	 *
	 * @return: Parent window name
	 * @throws InterruptedException
	 */
	public List<String> getAllWindowsOpenInUI() throws InterruptedException {
		Thread.sleep(2000);
		List<String> WinList = new ArrayList<>();
		Set<String> windows = getWebDriver().getWindowHandles();
		Iterator<String> iterator = windows.iterator();
		for (int i = 0; i <= windows.size() - 1; i++) {
			String win = iterator.next();
			WinList.add(win);
		}
		return WinList;
	}

	/**
	 * Handle child windows
	 *
	 * @return: Parent window name
	 * @throws InterruptedException
	 */
	public String switchToChildWindow() throws InterruptedException {
		Thread.sleep(2000);
		Set<String> windows = getWebDriver().getWindowHandles();
		Iterator<String> iterator = windows.iterator();
		String parent = iterator.next();
		String child = iterator.next();
		getWebDriver().switchTo().window(child);
		return parent;
	}

	/** close child window */
	public void switchParentWindowByClosingChild(String Win) {
		driver.close();
		getWebDriver().switchTo().window(Win);
	}

	/**
	 * Switch To window
	 * 
	 * @throws InterruptedException
	 */
	public void switchToWindow(String Win) throws InterruptedException {
		Thread.sleep(2000);
		getWebDriver().switchTo().window(Win);
	}

	/**
	 * Capturing screenshot once script is failed
	 * 
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws BadElementException
	 */
	public static Image captureScreenshot(ITestResult result)
			throws BadElementException, MalformedURLException, IOException {
		String fileName = System.getProperty("className");
		String screen = "";
		try {
			String screenshotName = Utilities.getFileName(fileName);
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String path = resultPath + "/" + fileName;
			new File(path).mkdirs();
			screen = path + "/" + "Failed_" + screenshotName + ".png";
			File screenshotLocation = new File(screen);
			FileUtils.copyFile(screenshot, screenshotLocation);
			Thread.sleep(1500);
			InputStream is = new FileInputStream(screenshotLocation);
			byte[] imageBytes = IOUtils.toByteArray(is);
			Thread.sleep(2000);
			String base64 = Base64.getEncoder().encodeToString(imageBytes);

			if (result.getStatus() == ITestResult.FAILURE) {
				test.log(LogStatus.FAIL, "Failed_" + fileName + " \n Snapshot below: "
						+ test.addBase64ScreenShot("data:image/png;base64," + base64));
				test.addScreenCapture(screen);
			}
			if (result.getStatus() == ITestResult.SKIP) {
				test.log(LogStatus.SKIP, "Skipped_" + fileName + " \n Snapshot below: "
						+ test.addBase64ScreenShot("data:image/png;base64," + base64));
				test.addScreenCapture(screen);
			}
			if (!(result.getStatus() == (ITestResult.SUCCESS) || result.getStatus() == (ITestResult.FAILURE))) {
				test.log(LogStatus.SKIP, "Skipped_" + fileName + " \n Snapshot below: "
						+ test.addBase64ScreenShot("data:image/png;base64," + base64));
				test.addScreenCapture(screen);
			}
			imageBytes = null;
			Runtime.getRuntime().gc();
			System.gc();
			base64 = null;
			Runtime.getRuntime().gc();
			System.gc();

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return Image.getInstance(screen);
	}

	/**
	 * Capturing screenshot after every step.
	 */
	public static Image captureScreenshots() throws BadElementException, MalformedURLException, IOException {
		String fileName = System.getProperty("className");
		String screen = "";
		try {

			String screenshotName = Utilities.getFileName(fileName);
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String path = resultPath + "/" + fileName;
			new File(path).mkdirs();
			screen = path + "/" + screenshotName + ".png";
			File screenshotLocation = new File(screen);
			FileUtils.copyFile(screenshot, screenshotLocation);
			Thread.sleep(2000);
			test.log(LogStatus.PASS, test.addScreenCapture(fileName + "/" + screenshotName + ".png"));
			Runtime.getRuntime().gc();
			System.gc();
		} catch (Exception e) {
			screen = captureScreenCasts();
		}
		return Image.getInstance(screen);
	}

	/**
	 * Report logs
	 *
	 * @param :
	 *            message
	 * @throws DocumentException
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws BadElementException
	 */
	public void reportLog(String msg) {
		message = msg;

		test.log(LogStatus.PASS, message);

		message = BREAK_LINE + message;
		logger.info("Message: " + message);
		Reporter.log(message);
	}

	static String message = "";

	public static String getMessage() {
		return message;
	}

	/**
	 * @return
	 * @function: Get formatted Time stamp
	 * 
	 */
	public String getFormattedTimeStamp() {

		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu HH:mm:ss");
		String formatDateTime = currentTime.format(formatter);
		return formatDateTime;

	}

}
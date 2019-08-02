package in.amazon.selenium.report;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.relevantcodes.extentreports.LogStatus;

import in.amazon.selenium.core.BaseTest;
import in.amazon.utilities.Constants;
import in.amazon.utilities.DateCalendar;
import in.amazon.utilities.Utilities;

public class ScreenRecorder extends Constants {

	// private static WebDriver driver;

	public static String imgPath;

	public static String captureScreenCasts() {
		String fileName = System.getProperty("className");
		String screenshotName = Utilities.getFileName(fileName);
		String path = BaseTest.resultPath + "/" + fileName;
		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			imgPath = path + "/" + screenshotName + ".png";
			Robot robot = null;
			robot = new Robot();
			BufferedImage img = robot.createScreenCapture(new Rectangle(screenSize)); // 1366, 768
			ImageIO.write(img, "png", new File(imgPath));
			BaseTest.test.log(LogStatus.PASS, fileName + "_" + "\n Snapshot below: "
					+ BaseTest.test.addScreenCapture(fileName + "/" + screenshotName + ".png"));
			return imgPath;
		} catch (Exception ex) {
		}
		return imgPath;
	}

}

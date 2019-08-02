package in.amazon.selenium.dataproviders;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import in.amazon.selenium.core.Configuration;

public class DataProviders {

	@DataProvider(name = "Browsers")
	public static Object[][] getBrowseDetails() throws Exception {

		List<String> browserData = new ArrayList<>();

		String chrome = Configuration.readApplicationFile("Chrome");
		if (chrome.toLowerCase().equals("true")) {
			browserData.add("Chrome");
		}

		String ie = Configuration.readApplicationFile("IE");
		if (ie.toLowerCase().equals("true")) {
			browserData.add("IE");
		}

		String firefox = Configuration.readApplicationFile("Firefox");
		if (firefox.toLowerCase().equals("true")) {
			browserData.add("Firefox");
		}

		Object[][] browsers = new Object[browserData.size()][1];
		int i = 0;
		for (String data : browserData) {
			browsers[i][0] = data;
			i++;
		}
		return browsers;
	}
}

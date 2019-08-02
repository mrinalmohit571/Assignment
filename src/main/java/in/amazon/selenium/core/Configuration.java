package in.amazon.selenium.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import in.amazon.utilities.Utilities;

public class Configuration {

	private String fileName;

	public Configuration(String fileName) {
		this.fileName = fileName;
	}

	public static String readApplicationFile(String key) throws Exception {
		String value;
		try {
			Properties prop = new Properties();
			File f = new File(Utilities.getPath() + "//config.properties");
			if (f.exists()) {
				prop.load(new FileInputStream(f));
				value = prop.getProperty(key);
			} else {
				throw new Exception("File not found");
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Failed to read from application.properties file.");
			throw ex;
		}
		if (value == null) {
			throw new Exception("Key not found in properties file");
		}
		return value;
	}

	public static Properties readTestData(String fileName) throws Exception {
		Properties prop = new Properties();
		try {
			File f = new File(Utilities.getPath() + "//src//test//resources//testdata//" + fileName + ".properties");
			if (f.exists()) {
				prop.load(new FileInputStream(f));
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not found " + fileName);
		}
		return prop;
	}

}

package in.amazon.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateCalendar {

	public static String dateTimeFileName() {
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu HH:mm:ss");
		return currentTime.format(formatter).replace("-", "").replace(":", "").replace(".", "").replaceAll(" ", "");
	}

}

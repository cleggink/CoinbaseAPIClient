package com.Zeus.CoinbaseAdvClientAPI;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PrintTime {

	public PrintTime() {
	}

	public void now(Integer plusMinutes) {

		String now = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		while(now.length() < 27) {
			now = String.join("", now, " ");
		}
		System.out.print(now + ": ");
	}
}

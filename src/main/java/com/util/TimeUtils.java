package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {	
	

	public static String convertToReadableTime(long millis) {
		long second = (millis / 1000) % 60;
		long minute = (millis / (1000 * 60)) % 60;
		long hour = (millis / (1000 * 60 * 60)) % 24;

		String time = String.format("%02d:%02d:%02d:%d", hour, minute, second, millis);
		return time;
	}
	
	public static String convertToReadableFormat(long millis) {		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");    
		Date resultdate = new Date(System.currentTimeMillis());		
		return sdf.format(resultdate);
	}

}

package com.djk.komhunter.utils;

public class StravaTimeUtil {
	
	public static Integer timeStampToSeconds(String timestamp) throws Exception {
		if(timestamp == null) {
			return null;
		}
	    if(timestamp.endsWith("s")) {
	        return Integer.parseInt(timestamp.substring(0, timestamp.length() - 1));
	    }
	    else if (timestamp.contains(":")) {
	        String[] bits = timestamp.split(":");
	        if(bits.length == 2) {
	        	return Integer.parseInt(bits[0]) * 60 + Integer.parseInt(bits[1]);
	        } else if(bits.length == 3) {
	        	return Integer.parseInt(bits[0]) * 3600 + Integer.parseInt(bits[1]) * 60 + Integer.parseInt(bits[2]);
	        }
	    }
	    
	    throw new Exception("Timestamp in unrecognised format: " + timestamp);
	}

}

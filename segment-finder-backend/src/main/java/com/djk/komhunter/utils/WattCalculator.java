package com.djk.komhunter.utils;

public class WattCalculator {
	private static final float ROLLING_RESISTANCE_COEFFICIENT = 0.0005f;
	private static final float WIND_REISTANCE_COEFFICIENT = 0.3f;
	private static final float AIR_DENSITY = 1.226f;
	private static final float GRAVITY = 9.8f;
	/**
	 * See https://theclimbingcyclist.com/gradients-and-cycling-how-much-harder-are-steeper-climbs/
	 * @return
	 * @throws Exception 
	 */
	public static float calculateWatts(String timeStamp, float distance, float gradient) throws Exception {
		Integer time = StravaTimeUtil.timeStampToSeconds(timeStamp);
		if(time == null) {
			return 0f;
		}
		float velocity = distance / StravaTimeUtil.timeStampToSeconds(timeStamp);
		return (ROLLING_RESISTANCE_COEFFICIENT * 70 * velocity) +
				(WIND_REISTANCE_COEFFICIENT * velocity * velocity * velocity * AIR_DENSITY) +
				(GRAVITY * velocity * 70 * gradient / 100);
	}

}

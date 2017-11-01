package local.iot.devices.currencypuller.utils;

import java.util.concurrent.TimeUnit;

public class DateTimeUtil
{
	public static String parseUptime(long millis)
	{
		long diff = millis;
		long days = TimeUnit.MILLISECONDS.toDays(millis);
		diff -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(diff);
		diff -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
		diff -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
		return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
	}
}

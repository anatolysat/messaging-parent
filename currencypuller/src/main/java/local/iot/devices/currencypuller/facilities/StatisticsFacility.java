package local.iot.devices.currencypuller.facilities;

public class StatisticsFacility
{
	private static StatisticsFacility INSTANCE;

	private long applicationStartTime = System.currentTimeMillis();

	private StatisticsFacility()
	{

	}

	public static StatisticsFacility getInstance()
	{
		if (INSTANCE == null)
			INSTANCE = new StatisticsFacility();
		return INSTANCE;
	}

	public long getApplicationStartTime()
	{
		return applicationStartTime;
	}
}

package local.iot.devices.currencypuller.core.tests;

import static org.junit.Assert.fail;

import org.junit.Test;

import local.iot.devices.currencypuller.utils.DateTimeUtil;

public class UtilsTests
{
	@Test
	public void testParseUptime()
	{
		try
		{
			Long timestamp = 24 * 60 * 60 * 1000 * 2L;
			timestamp += 3 * 60 * 60 * 1000L;
			timestamp += 4 * 60 * 1000;
			timestamp += 5 * 1000;
			System.out.println(DateTimeUtil.parseUptime(timestamp));
		} catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
}

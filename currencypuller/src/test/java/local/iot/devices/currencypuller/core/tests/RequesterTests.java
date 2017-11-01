package local.iot.devices.currencypuller.core.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import local.iot.devices.commons.model.CurrencyRate;
import local.iot.devices.commons.util.JsonMapper;
import local.iot.devices.currencypuller.Requester;

public class RequesterTests
{
	@Test
	public void testGet()
	{
		try
		{
			String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
			Requester requester = new Requester();
			List<CurrencyRate> list = requester.get(url);
			System.out.println(list);
		} catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
}

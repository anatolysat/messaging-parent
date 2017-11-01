package local.iot.devices.currencypuller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import local.iot.devices.commons.model.CurrencyRate;
import local.iot.devices.currencypuller.facilities.CountersFacility;
import local.iot.devices.currencypuller.facilities.CountersFacility.Counter;
import local.iot.devices.currencypuller.facilities.LoggerFacility;

public class Requester
{
	private LoggerFacility logger;
	private CountersFacility counters;

	private Client client = ClientBuilder.newClient();

	public Requester()
	{
		logger = LoggerFacility.getInstance();
		counters = CountersFacility.getInstance();
	}

	public List<CurrencyRate> get(String url)
	{
		counters.increment(Counter.CURRENCY_API_CALL);
		List<CurrencyRate> rates = new ArrayList<>();
		try
		{
			rates.addAll(Arrays.asList(client.target(url).request().get(CurrencyRate[].class)));
		} catch (Exception e)
		{
			counters.increment(Counter.ERROR);
			logger.error(e.getMessage(), e);
		}
		return rates;
	}

	public void stopClient()
	{
		client.close();
	}
}

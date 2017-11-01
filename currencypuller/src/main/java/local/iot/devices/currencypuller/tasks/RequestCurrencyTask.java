package local.iot.devices.currencypuller.tasks;

import java.util.List;

import local.iot.devices.commons.execution.AsyncListener;
import local.iot.devices.commons.execution.AsyncTask;
import local.iot.devices.commons.model.CurrencyRate;
import local.iot.devices.currencypuller.Requester;
import local.iot.devices.currencypuller.facilities.LoggerFacility;

public class RequestCurrencyTask extends AsyncTask<List<CurrencyRate>>
{
	private Requester requester;
	private String currencyApiUrl;

	public RequestCurrencyTask(Requester requester, String currencyApiUrl, AsyncListener<List<CurrencyRate>> listener)
	{
		super(listener);
		this.requester = requester;
		this.currencyApiUrl = currencyApiUrl;
	}

	@Override
	public void execute()
	{
		try
		{
			List<CurrencyRate> list = requester.get(currencyApiUrl);
			listener.process(list);
		} catch (Exception e)
		{
			listener.errorOccured(e);
		}
	}
}

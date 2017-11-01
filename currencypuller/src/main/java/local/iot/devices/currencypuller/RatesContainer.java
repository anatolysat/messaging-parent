package local.iot.devices.currencypuller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import local.iot.devices.commons.execution.AsyncListener;
import local.iot.devices.commons.model.CurrencyRate;
import local.iot.devices.currencypuller.facilities.LoggerFacility;

public class RatesContainer implements AsyncListener<List<CurrencyRate>>
{
	private LoggerFacility logger;

	private ConcurrentLinkedQueue<CurrencyRate> pendingQueue = new ConcurrentLinkedQueue<>();
	private List<CurrencyRate> currentRates;

	public RatesContainer()
	{
		logger = LoggerFacility.getInstance();
	}

	@Override
	public void process(List<CurrencyRate> result)
	{
		List<CurrencyRate> changedRates = getChangedRates(result);
		if (!changedRates.isEmpty())
		{
			currentRates = result;
			pendingQueue.addAll(changedRates);
		}
	}

	public CurrencyRate pollPending()
	{
		return pendingQueue.poll();
	}

	@Override
	public void errorOccured(Exception e)
	{
		logger.error(e.getMessage(), e);
	}

	public List<CurrencyRate> getCurrentRates()
	{
		return currentRates;
	}

	private List<CurrencyRate> getChangedRates(List<CurrencyRate> list)
	{
		List<CurrencyRate> result = new ArrayList<>();
		if (currentRates == null || currentRates.size() != list.size())
			result = list;
		else
		{
			for (int i = 0; i < list.size(); i++)
			{
				CurrencyRate rate = list.get(i);
				CurrencyRate curr = currentRates.get(i);
				if (!rate.equals(curr))
				{
					result.add(rate);
					break;
				}
			}
		}
		return result;
	}
}

package local.iot.devices.currencypuller.rest.model;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import local.iot.devices.commons.interfaces.Printable;

public class Counters implements Printable
{
	private List<CounterValue> counters;

	public Counters()
	{
	}

	public Counters(List<CounterValue> counters)
	{
		this.counters = counters;
	}

	@Override
	public String stringify()
	{
		return ReflectionToStringBuilder.toString(this);
	}

	public List<CounterValue> getCounters()
	{
		return counters;
	}

	public void setCounters(List<CounterValue> counters)
	{
		this.counters = counters;
	}
}

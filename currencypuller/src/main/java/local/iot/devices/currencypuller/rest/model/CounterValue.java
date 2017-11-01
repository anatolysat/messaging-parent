package local.iot.devices.currencypuller.rest.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import local.iot.devices.commons.interfaces.Printable;

public class CounterValue implements Printable
{
	private String name;
	private Long value;

	public CounterValue()
	{
	}

	public CounterValue(String name, Long value)
	{
		this.name = name;
		this.value = value;
	}

	@Override
	public String stringify()
	{
		return ReflectionToStringBuilder.toString(this);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getValue()
	{
		return value;
	}

	public void setValue(Long value)
	{
		this.value = value;
	}
}

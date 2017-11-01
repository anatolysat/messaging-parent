package local.iot.devices.currencypuller.rest.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import local.iot.devices.commons.interfaces.Printable;

@SuppressWarnings("serial")
public class ObjectResponse extends GenericJsonResponse
{
	private Object value;

	public ObjectResponse()
	{
		super();
	}

	public ObjectResponse(String status, String message, Object value)
	{
		super(status, message);
		this.value = value;
	}

	@Override
	public String stringify()
	{
		return ReflectionToStringBuilder.toString(this);
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}
}

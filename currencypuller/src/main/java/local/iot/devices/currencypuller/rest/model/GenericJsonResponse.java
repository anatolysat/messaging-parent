package local.iot.devices.currencypuller.rest.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import local.iot.devices.commons.interfaces.Printable;

public class GenericJsonResponse implements Serializable, Printable
{
	private static final long serialVersionUID = 2153090754124547989L;

	private String status;
	private String message;

	public GenericJsonResponse()
	{
	}

	public GenericJsonResponse(String status, String message)
	{
		this.status = status;
		this.message = message;
	}

	@Override
	public String stringify()
	{
		return ReflectionToStringBuilder.toString(this);
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}

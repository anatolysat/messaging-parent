package local.iot.devices.currencypuller.rest.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import local.iot.devices.commons.interfaces.Printable;

public class GenericJsonRequest implements Serializable, Printable
{
	private static final long serialVersionUID = 6434062963627022298L;

	@Override
	public String stringify()
	{
		return ReflectionToStringBuilder.toString(this);
	}
}

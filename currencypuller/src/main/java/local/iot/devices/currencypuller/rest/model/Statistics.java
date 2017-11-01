package local.iot.devices.currencypuller.rest.model;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import local.iot.devices.commons.interfaces.Printable;
import local.iot.devices.currencypuller.tasks.MqttState;
import local.iot.devices.currencypuller.utils.DateTimeUtil;

public class Statistics implements Printable
{
	private String appStartTime;
	private String uptime;
	private MqttState mqttState;
	private Counters counters;

	public Statistics()
	{
	}

	private Statistics(String appStartTime, String uptime, Counters counters, MqttState mqttState)
	{
		this.appStartTime = appStartTime;
		this.uptime = uptime;
		this.counters = counters;
		this.mqttState = mqttState;
	}

	@Override
	public String stringify()
	{
		return ReflectionToStringBuilder.toString(this);
	}

	public String getAppStartTime()
	{
		return appStartTime;
	}

	public void setAppStartTime(String appStartTime)
	{
		this.appStartTime = appStartTime;
	}

	public MqttState getMqttState()
	{
		return mqttState;
	}

	public void setMqttState(MqttState mqttState)
	{
		this.mqttState = mqttState;
	}

	public Counters getCounters()
	{
		return counters;
	}

	public void setCounters(Counters counters)
	{
		this.counters = counters;
	}

	public String getUptime()
	{
		return uptime;
	}

	public void setUptime(String uptime)
	{
		this.uptime = uptime;
	}

	public static class Builder
	{
		private String appStartTime;
		private String uptime;
		private MqttState mqttState;
		private Counters counters;

		public Builder appStartTime(long timestamp)
		{
			this.appStartTime = new Date(timestamp).toString();
			return this;
		}

		public Builder uptime(long timestamp)
		{
			this.uptime = DateTimeUtil.parseUptime(timestamp);
			return this;
		}

		public Builder counters(Counters counters)
		{
			this.counters = counters;
			return this;
		}

		public Builder mqttState(MqttState mqttState)
		{
			this.mqttState = mqttState;
			return this;
		}

		public Statistics build()
		{
			return new Statistics(appStartTime, uptime, counters, mqttState);
		}
	}
}

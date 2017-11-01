package local.iot.devices.currencypuller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Level;

import local.iot.devices.commons.interfaces.Printable;

public class Config implements Printable
{
	private String mqttServerHost;
	private int mqttServerPort;
	private int mqttClientThreads;
	private String mqttCleintThreadPoolName;
	private int mqttClientTerminateTimeout;
	private String mqttUsername;
	private String mqttPassword;
	private int currencyPollPeriod;
	private TimeUnit currencyPollTimeUnit;
	private String currencyApiUrl;
	private int coreThreads;
	private int coreExecutorTerminateTimeout;
	private int mqttReconnectDelay;
	private TimeUnit mqttReconnectTimeUnit;
	private String mqttClientId;
	private String mqttTopicPrefix;
	private int mqttPublishWindow;
	private int mqttPublishInterval;
	private TimeUnit mqttPublishTimeUnit;
	private Level loggerLevel;
	private String apiUrl;

	public Config()
	{
	}

	private Config(String mqttServerHost, int mqttServerPort, int mqttClientThreads, String mqttCleintThreadPoolName, int mqttClientTerminateTimeout, String mqttUsername, String mqttPassword,
			int currencyPollPeriod, TimeUnit currencyPollTimeUnit, String currencyApiUrl, int coreThreads, int coreExecutorTerminateTimeout, int mqttReconnectDelay, TimeUnit mqttReconnectTimeUnit,
			String mqttClientId, String mqttTopicPrefix, int mqttPublishWindow, int mqttPublishInterval, TimeUnit mqttPublishTimeUnit, Level loggerLevel, String apiUrl)
	{
		this.mqttServerHost = mqttServerHost;
		this.mqttServerPort = mqttServerPort;
		this.mqttClientThreads = mqttClientThreads;
		this.mqttCleintThreadPoolName = mqttCleintThreadPoolName;
		this.mqttClientTerminateTimeout = mqttClientTerminateTimeout;
		this.mqttUsername = mqttUsername;
		this.mqttPassword = mqttPassword;
		this.currencyPollPeriod = currencyPollPeriod;
		this.currencyPollTimeUnit = currencyPollTimeUnit;
		this.currencyApiUrl = currencyApiUrl;
		this.coreThreads = coreThreads;
		this.coreExecutorTerminateTimeout = coreExecutorTerminateTimeout;
		this.mqttReconnectDelay = mqttReconnectDelay;
		this.mqttReconnectTimeUnit = mqttReconnectTimeUnit;
		this.mqttClientId = mqttClientId;
		this.mqttTopicPrefix = mqttTopicPrefix;
		this.mqttPublishWindow = mqttPublishWindow;
		this.mqttPublishInterval = mqttPublishInterval;
		this.mqttPublishTimeUnit = mqttPublishTimeUnit;
		this.loggerLevel = loggerLevel;
		this.apiUrl = apiUrl;
	}

	@Override
	public String stringify()
	{
		return ReflectionToStringBuilder.toString(this);
	}

	public static Config load()
	{
		Properties properties = new Properties();
		try (InputStream input = Config.class.getResourceAsStream("/config.properties"))
		{
			properties.load(input);
		} catch (IOException e)
		{
			throw new IllegalStateException(e.getMessage(), e);
		}

		String mqttServerHost = null, mqttCleintThreadPoolName = null, mqttUsername = null, mqttPassword = null, currencyApiUrl = null, mqttClientId = null, mqttTopicPrefix = null, apiUrl = null;
		Integer mqttServerPort = null, mqttClientThreads = null, mqttClientTerminateTimeout = null, currencyPollPeriod = null, coreThreads = null, coreExecutorTerminateTimeout = null,
				mqttReconnectDelay = null, mqttPublishWindow = null, mqttPublishInterval = null;
		TimeUnit currencyPollTimeUnit = null, mqttReconnectTimeUnit = null, mqttPublishTimeUnit = null;
		Level loggerLevel = null;

		mqttServerHost = properties.getProperty("mqttServerHost");
		if (mqttServerHost == null)
			throw new IllegalArgumentException("INVALID PROPERTY: mqttServerHost");

		mqttServerPort = parseIntProperty("mqttServerPort", properties, 1000, 65535);
		if (mqttServerPort == null)
			throw new IllegalArgumentException("INVALID PROPERTY: mqttServerPort");

		mqttClientThreads = parseIntProperty("mqttClientThreads", properties, 1, 1000);
		if (mqttClientThreads == null)
			throw new IllegalArgumentException("INVALID PROPERTY: mqttClientThreads");

		mqttCleintThreadPoolName = properties.getProperty("mqttCleintThreadPoolName");
		if (mqttCleintThreadPoolName == null)
			throw new IllegalArgumentException("INVALID PROPERTY: mqttCleintThreadPoolName");

		mqttClientTerminateTimeout = parseIntProperty("mqttClientTerminateTimeout", properties, 0, Integer.MAX_VALUE);
		if (mqttClientTerminateTimeout == null)
			throw new IllegalArgumentException("INVALID PROPERTY: mqttClientTerminateTimeout");

		mqttUsername = properties.getProperty("mqttUsername");

		mqttPassword = properties.getProperty("mqttPassword");

		currencyPollPeriod = parseIntProperty("currencyPollPeriod", properties, 1, Integer.MAX_VALUE);
		if (currencyPollPeriod == null)
			throw new IllegalArgumentException("INVALID PROPERTY: currencyPollPeriod");

		String unitValue = properties.getProperty("currencyPollTimeUnit");
		if (unitValue != null)
			currencyPollTimeUnit = TimeUnit.valueOf(unitValue);
		if (currencyPollTimeUnit == null)
			throw new IllegalArgumentException("INVALID PROPERTY: currencyPollTimeUnit");

		currencyApiUrl = properties.getProperty("currencyApiUrl");
		if (currencyApiUrl == null)
			throw new IllegalArgumentException("INVALID PROPERTY: currencyApiUrl");

		coreThreads = parseIntProperty("coreThreads", properties, 1, 1000);
		if (coreThreads == null)
			throw new IllegalArgumentException("INVALID PROPERTY: coreThreads");

		coreExecutorTerminateTimeout = parseIntProperty("coreExecutorTerminateTimeout", properties, 1, Integer.MAX_VALUE);
		if (coreExecutorTerminateTimeout == null)
			throw new IllegalArgumentException("INVALID PROPERTY: coreExecutorTerminateTimeout");

		mqttReconnectDelay = parseIntProperty("mqttReconnectDelay", properties, 1, Integer.MAX_VALUE);
		if (mqttReconnectDelay == null)
			throw new IllegalArgumentException("INVALID PROPERTY: mqttReconnectDelay");

		String mqttReconnectTimeUnitValue = properties.getProperty("mqttReconnectTimeUnit");
		if (mqttReconnectTimeUnitValue != null)
			mqttReconnectTimeUnit = TimeUnit.valueOf(mqttReconnectTimeUnitValue);
		if (mqttReconnectTimeUnit == null)
			throw new IllegalArgumentException("INVALID PROPERTY: mqttReconnectTimeUnit");

		mqttClientId = properties.getProperty("mqttClientId");
		if (mqttClientId == null)
			throw new IllegalArgumentException("INVALID PROPERTY: mqttClientId");

		mqttTopicPrefix = properties.getProperty("mqttTopicPrefix");
		if (mqttTopicPrefix == null)
			throw new IllegalArgumentException("INVALID PROPERTY: mqttTopicPrefix");

		mqttPublishWindow = parseIntProperty("mqttPublishWindow", properties, 1, Integer.MAX_VALUE);
		if (mqttPublishWindow == null)
			throw new IllegalArgumentException("INVALID PROPERTY: mqttPublishWindow");

		mqttPublishInterval = parseIntProperty("mqttPublishInterval", properties, 1, Integer.MAX_VALUE);
		if (mqttPublishInterval == null)
			throw new IllegalArgumentException("INVALID PROPERTY: mqttPublishInterval");

		String mqttPublishTimeUnitValue = properties.getProperty("mqttPublishTimeUnit");
		if (mqttPublishTimeUnitValue != null)
			mqttPublishTimeUnit = TimeUnit.valueOf(mqttPublishTimeUnitValue);
		if (mqttPublishTimeUnit == null)
			throw new IllegalArgumentException("INVALID PROPERTY: mqttReconnectTimeUnit");

		String loggerLevelValue = properties.getProperty("loggerLevel");
		if (loggerLevelValue != null)
			loggerLevel = Level.toLevel(loggerLevelValue);
		if (loggerLevel == null)
			throw new IllegalArgumentException("INVALID PROPERTY: loggerLevel");

		apiUrl = properties.getProperty("apiUrl");
		if (apiUrl == null)
			throw new IllegalArgumentException("INVALID PROPERTY: apiUrl");

		return new Config(mqttServerHost, mqttServerPort, mqttClientThreads, mqttCleintThreadPoolName, mqttClientTerminateTimeout, mqttUsername, mqttPassword, currencyPollPeriod, currencyPollTimeUnit,
				currencyApiUrl, coreThreads, coreExecutorTerminateTimeout, mqttReconnectDelay, mqttReconnectTimeUnit, mqttClientId, mqttTopicPrefix, mqttPublishWindow, mqttPublishInterval,
				mqttPublishTimeUnit, loggerLevel, apiUrl);
	}

	private static Integer parseIntProperty(String name, Properties properties, int min, int max)
	{
		Integer result = null;
		String value = properties.getProperty(name);
		if (value != null)
		{
			try
			{
				result = Integer.parseInt(value);
				if (result != null && result < min || result > max)
					throw new IllegalArgumentException(name + " property must be in range from " + min + " to " + max + ". value:" + result);
			} catch (NumberFormatException e)
			{
				throw new IllegalArgumentException("invalid int property format for property " + name);
			}
		}

		return result;
	}

	public String getMqttServerHost()
	{
		return mqttServerHost;
	}

	public void setMqttServerHost(String mqttServerHost)
	{
		this.mqttServerHost = mqttServerHost;
	}

	public int getMqttServerPort()
	{
		return mqttServerPort;
	}

	public void setMqttServerPort(int mqttServerPort)
	{
		this.mqttServerPort = mqttServerPort;
	}

	public int getMqttClientThreads()
	{
		return mqttClientThreads;
	}

	public void setMqttClientThreads(int mqttClientThreads)
	{
		this.mqttClientThreads = mqttClientThreads;
	}

	public String getMqttCleintThreadPoolName()
	{
		return mqttCleintThreadPoolName;
	}

	public void setMqttCleintThreadPoolName(String mqttCleintThreadPoolName)
	{
		this.mqttCleintThreadPoolName = mqttCleintThreadPoolName;
	}

	public int getMqttClientTerminateTimeout()
	{
		return mqttClientTerminateTimeout;
	}

	public void setMqttClientTerminateTimeout(int mqttClientTerminateTimeout)
	{
		this.mqttClientTerminateTimeout = mqttClientTerminateTimeout;
	}

	public String getMqttUsername()
	{
		return mqttUsername;
	}

	public void setMqttUsername(String mqttUsername)
	{
		this.mqttUsername = mqttUsername;
	}

	public String getMqttPassword()
	{
		return mqttPassword;
	}

	public void setMqttPassword(String mqttPassword)
	{
		this.mqttPassword = mqttPassword;
	}

	public int getCurrencyPollPeriod()
	{
		return currencyPollPeriod;
	}

	public void setCurrencyPollPeriod(int currencyPollPeriod)
	{
		this.currencyPollPeriod = currencyPollPeriod;
	}

	public TimeUnit getCurrencyPollTimeUnit()
	{
		return currencyPollTimeUnit;
	}

	public void setCurrencyPollTimeUnit(TimeUnit currencyPollTimeUnit)
	{
		this.currencyPollTimeUnit = currencyPollTimeUnit;
	}

	public String getCurrencyApiUrl()
	{
		return currencyApiUrl;
	}

	public void setCurrencyApiUrl(String currencyApiUrl)
	{
		this.currencyApiUrl = currencyApiUrl;
	}

	public int getCoreThreads()
	{
		return coreThreads;
	}

	public void setCoreThreads(int coreThreads)
	{
		this.coreThreads = coreThreads;
	}

	public int getCoreExecutorTerminateTimeout()
	{
		return coreExecutorTerminateTimeout;
	}

	public void setCoreExecutorTerminateTimeout(int coreExecutorTerminateTimeout)
	{
		this.coreExecutorTerminateTimeout = coreExecutorTerminateTimeout;
	}

	public int getMqttReconnectDelay()
	{
		return mqttReconnectDelay;
	}

	public void setMqttReconnectDelay(int mqttReconnectDelay)
	{
		this.mqttReconnectDelay = mqttReconnectDelay;
	}

	public TimeUnit getMqttReconnectTimeUnit()
	{
		return mqttReconnectTimeUnit;
	}

	public void setMqttReconnectTimeUnit(TimeUnit mqttReconnectTimeUnit)
	{
		this.mqttReconnectTimeUnit = mqttReconnectTimeUnit;
	}

	public String getMqttClientId()
	{
		return mqttClientId;
	}

	public void setMqttClientId(String mqttClientId)
	{
		this.mqttClientId = mqttClientId;
	}

	public String getMqttTopicPrefix()
	{
		return mqttTopicPrefix;
	}

	public void setMqttTopicPrefix(String mqttTopicPrefix)
	{
		this.mqttTopicPrefix = mqttTopicPrefix;
	}

	public int getMqttPublishWindow()
	{
		return mqttPublishWindow;
	}

	public void setMqttPublishWindow(int mqttPublishWindow)
	{
		this.mqttPublishWindow = mqttPublishWindow;
	}

	public int getMqttPublishInterval()
	{
		return mqttPublishInterval;
	}

	public void setMqttPublishInterval(int mqttPublishInterval)
	{
		this.mqttPublishInterval = mqttPublishInterval;
	}

	public TimeUnit getMqttPublishTimeUnit()
	{
		return mqttPublishTimeUnit;
	}

	public void setMqttPublishTimeUnit(TimeUnit mqttPublishTimeUnit)
	{
		this.mqttPublishTimeUnit = mqttPublishTimeUnit;
	}

	public Level getLoggerLevel()
	{
		return loggerLevel;
	}

	public void setLoggerLevel(Level loggerLevel)
	{
		this.loggerLevel = loggerLevel;
	}

	public String getApiUrl()
	{
		return apiUrl;
	}

	public void setApiUrl(String apiUrl)
	{
		this.apiUrl = apiUrl;
	}
}

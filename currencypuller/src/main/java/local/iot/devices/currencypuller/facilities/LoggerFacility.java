package local.iot.devices.currencypuller.facilities;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LoggerFacility
{
	private static LoggerFacility INSTANCE;

	private Logger logger = Logger.getLogger(LoggerFacility.class);

	private LoggerFacility(Level level)
	{
		this.logger.setLevel(level);
	}

	public static LoggerFacility getInstance(Level level)
	{
		if (INSTANCE == null)
			INSTANCE = new LoggerFacility(level);
		return INSTANCE;
	}

	public static LoggerFacility getInstance()
	{
		if (INSTANCE == null)
			throw new IllegalStateException(LoggerFacility.class.getSimpleName() + " was not previously isntanciated");

		return INSTANCE;
	}

	public void debug(String message, Exception e)
	{
		if (logger.isDebugEnabled())
			logger.debug(message, e);
	}

	public void debug(String message)
	{
		if (logger.isDebugEnabled())
			logger.debug(message);
	}

	public void trace(String message, Exception e)
	{
		if (logger.isTraceEnabled())
			logger.trace(message, e);
	}

	public void trace(String message)
	{
		if (logger.isTraceEnabled())
			logger.trace(message);
	}

	public void info(String message, Exception e)
	{
		if (logger.isInfoEnabled())
			logger.info(message, e);
	}

	public void info(String message)
	{
		if (logger.isInfoEnabled())
			logger.info(message);
	}

	public void warn(String message, Exception e)
	{
		logger.warn(message, e);
	}

	public void warn(String message)
	{
		logger.warn(message);
	}

	public void error(String message, Exception e)
	{
		logger.error(message, e);
	}

	public void error(String message)
	{
		logger.error(message);
	}
}

package local.iot.devices.currencypuller.rest;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import local.iot.devices.currencypuller.App;
import local.iot.devices.currencypuller.Config;
import local.iot.devices.currencypuller.RatesContainer;
import local.iot.devices.currencypuller.facilities.CountersFacility;
import local.iot.devices.currencypuller.facilities.LoggerFacility;
import local.iot.devices.currencypuller.facilities.StatisticsFacility;
import local.iot.devices.currencypuller.rest.model.GenericJsonResponse;
import local.iot.devices.currencypuller.rest.model.ObjectResponse;
import local.iot.devices.currencypuller.rest.model.Statistics;

@Path("api")
@Singleton
public class RestController
{
	private App app;
	private LoggerFacility logger;

	public RestController(App app)
	{
		this.app = app;
		this.logger = LoggerFacility.getInstance();
	}

	@GET
	@Path("statistics")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericJsonResponse getStatistics()
	{
		try
		{
			StatisticsFacility statisticsFacility = app.getStatisticsFacility();
			Statistics.Builder builder = new Statistics.Builder();
			builder.appStartTime(statisticsFacility.getApplicationStartTime());
			builder.uptime(statisticsFacility.getApplicationStartTime());
			builder.mqttState(app.getMqttState());
			builder.counters(app.getCountersFacility().getCounters());

			Statistics statistics = builder.build();
			return new ObjectResponse(Constants.SUCCESS, null, statistics);
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return new GenericJsonResponse(Constants.ERROR, e.getMessage());
		}
	}

	@GET
	@Path("counters")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericJsonResponse getCounters()
	{
		try
		{
			CountersFacility countersFacility = app.getCountersFacility();
			return new ObjectResponse(Constants.SUCCESS, null, countersFacility.getCounters());
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return new GenericJsonResponse(Constants.ERROR, e.getMessage());
		}
	}

	@GET
	@Path("current-rates")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericJsonResponse getCurrentRates()
	{
		try
		{
			RatesContainer ratesCountainer = app.getRatesContainer();
			return new ObjectResponse(Constants.SUCCESS, null, ratesCountainer.getCurrentRates());
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return new GenericJsonResponse(Constants.ERROR, e.getMessage());
		}
	}

	@GET
	@Path("config")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericJsonResponse getConfig()
	{
		try
		{
			Config config = app.getConfig();
			return new ObjectResponse(Constants.SUCCESS, null, config);
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return new GenericJsonResponse(Constants.ERROR, e.getMessage());
		}
	}
}

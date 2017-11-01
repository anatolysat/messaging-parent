package local.iot.devices.currencypuller.tasks;

import java.util.concurrent.atomic.AtomicBoolean;

import com.mobius.software.mqtt.parser.header.api.MQMessage;
import com.mobius.software.mqtt.parser.header.impl.Publish;

import local.iot.devices.commons.execution.AsyncListener;
import local.iot.devices.commons.execution.AsyncTask;
import local.iot.devices.commons.interfaces.ConnectionHandler;
import local.iot.devices.commons.model.CurrencyRate;
import local.iot.devices.commons.net.mqtt.MqttClient;
import local.iot.devices.commons.util.JsonMapper;
import local.iot.devices.currencypuller.Config;
import local.iot.devices.currencypuller.RatesContainer;
import local.iot.devices.currencypuller.facilities.CountersFacility;
import local.iot.devices.currencypuller.facilities.CountersFacility.Counter;
import local.iot.devices.currencypuller.utils.MessageBuilder;

public class MqttPublishTask extends AsyncTask<MqttState>
{
	private CountersFacility counters;

	private Config config;
	private RatesContainer ratesContainer;
	private JsonMapper mapper;
	private ConnectionHandler<MQMessage> mqttClient;

	private AtomicBoolean isRunning = new AtomicBoolean();

	public MqttPublishTask(Config config, RatesContainer ratesContainer, JsonMapper mapper, ConnectionHandler<MQMessage> mqttClient, AsyncListener<MqttState> listener)
	{
		super(listener);
		this.config = config;
		this.ratesContainer = ratesContainer;
		this.mapper = mapper;
		this.mqttClient = mqttClient;
		this.counters = CountersFacility.getInstance();
	}

	@Override
	public void execute()
	{
		try
		{
			if (!isRunning.get())
				return;

			for (int i = 0; i < config.getMqttPublishWindow(); i++)
			{
				CurrencyRate rate = ratesContainer.pollPending();
				if (rate != null)
				{
					String topicName = config.getMqttTopicPrefix() + rate.getCcy();
					String jsonMessage = mapper.toJson(rate);
					Publish publish = MessageBuilder.buildPublish(topicName, jsonMessage);
					mqttClient.sendMessage(publish);
					counters.increment(Counter.MQTT_SENT);
				} else
					break;
			}
		} catch (Exception e)
		{
			errorOccured(e);
		}
	}

	public void deactivate()
	{
		isRunning.set(false);
	}

	public void activate()
	{
		isRunning.set(true);
	}
}

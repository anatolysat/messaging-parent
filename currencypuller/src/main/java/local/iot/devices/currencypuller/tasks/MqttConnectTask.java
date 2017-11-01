package local.iot.devices.currencypuller.tasks;

import com.mobius.software.mqtt.parser.header.api.MQMessage;
import com.mobius.software.mqtt.parser.header.impl.Connect;

import local.iot.devices.commons.execution.AsyncListener;
import local.iot.devices.commons.execution.AsyncTask;
import local.iot.devices.commons.interfaces.ConnectionHandler;
import local.iot.devices.commons.net.mqtt.MqttClient;
import local.iot.devices.currencypuller.Config;
import local.iot.devices.currencypuller.facilities.CountersFacility;
import local.iot.devices.currencypuller.facilities.CountersFacility.Counter;
import local.iot.devices.currencypuller.utils.MessageBuilder;

public class MqttConnectTask extends AsyncTask<MqttState>
{
	private Config config;
	private ConnectionHandler<MQMessage> mqttClient;
	private CountersFacility counters;

	public MqttConnectTask(Config config, ConnectionHandler<MQMessage> mqttClient, AsyncListener<MqttState> listener)
	{
		super(listener);
		this.config = config;
		this.mqttClient = mqttClient;
		this.counters = CountersFacility.getInstance();
	}

	@Override
	public void execute()
	{
		Connect connect = MessageBuilder.buildConnect(config);
		mqttClient.sendMessage(connect);
		counters.increment(Counter.MQTT_SENT);
	}
}

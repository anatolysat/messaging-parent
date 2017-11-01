package local.iot.devices.currencypuller.tasks;

import com.mobius.software.mqtt.parser.header.api.MQMessage;

import local.iot.devices.commons.execution.AsyncListener;
import local.iot.devices.commons.execution.AsyncTask;
import local.iot.devices.commons.interfaces.ConnectionHandler;

public class MqttConnectionTask extends AsyncTask<MqttState>
{
	private ConnectionHandler<MQMessage> mqttClient;

	public MqttConnectionTask(ConnectionHandler<MQMessage> mqttClient, AsyncListener<MqttState> listener)
	{
		super(listener);
		this.mqttClient = mqttClient;
	}

	@Override
	public void execute()
	{
		try
		{
			mqttClient.stop();
			mqttClient.start();
			listener.process(MqttState.CONNECTION_ESTABLISHED);
		} catch (Exception e)
		{
			errorOccured(e);
		}
	}
}

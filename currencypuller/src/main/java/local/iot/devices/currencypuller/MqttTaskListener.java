package local.iot.devices.currencypuller;

import java.util.concurrent.atomic.AtomicReference;

import com.mobius.software.mqtt.parser.header.api.MQMessage;

import local.iot.devices.commons.execution.AsyncListener;
import local.iot.devices.commons.execution.TaskExecutor;
import local.iot.devices.commons.interfaces.ConnectionHandler;
import local.iot.devices.commons.net.mqtt.MqttClient;
import local.iot.devices.commons.util.JsonMapper;
import local.iot.devices.currencypuller.tasks.MqttConnectionTask;
import local.iot.devices.currencypuller.facilities.CountersFacility;
import local.iot.devices.currencypuller.facilities.LoggerFacility;
import local.iot.devices.currencypuller.facilities.CountersFacility.Counter;
import local.iot.devices.currencypuller.tasks.MqttConnectTask;
import local.iot.devices.currencypuller.tasks.MqttPublishTask;
import local.iot.devices.currencypuller.tasks.MqttState;

public class MqttTaskListener implements AsyncListener<MqttState>
{
	private TaskExecutor executor;
	private Config config;

	private MqttConnectionTask mqttConnectionTask;
	private MqttConnectTask mqttConnectTask;
	private MqttPublishTask mqttPublishTask;

	private AtomicReference<MqttState> currState = new AtomicReference<>();

	private LoggerFacility logger;
	private CountersFacility counters;

	public MqttTaskListener(TaskExecutor executor, Config config, ConnectionHandler<MQMessage> mqttClient, JsonMapper mapper, Requester requester, RatesContainer ratesContainer)
	{
		this.executor = executor;
		this.config = config;
		final AsyncListener<MqttState> callback = this;
		this.mqttConnectionTask = new MqttConnectionTask(mqttClient, callback);
		this.mqttConnectTask = new MqttConnectTask(config, mqttClient, callback);
		this.mqttPublishTask = new MqttPublishTask(config, ratesContainer, mapper, mqttClient, callback);
		this.logger = LoggerFacility.getInstance();
		this.counters = CountersFacility.getInstance();
	}

	@Override
	public void process(MqttState result)
	{
		currState.set(result);
		switch (currState.get())
		{
		case CONNECTED:
			mqttPublishTask.activate();
			executor.scheduleTask(mqttPublishTask, 0, config.getMqttPublishInterval(), config.getMqttPublishTimeUnit());
			break;
		case CONNECTION_ESTABLISHED:
			executor.submitTask(mqttConnectTask);
			break;
		case INIT:
		case DISCONNECTED:
			executor.scheduleTask(mqttConnectionTask, config.getMqttReconnectDelay(), config.getMqttReconnectTimeUnit());
			break;
		}
	}

	@Override
	public void errorOccured(Exception e)
	{
		logger.error(e.getMessage(), e);
		counters.increment(Counter.ERROR);
		mqttPublishTask.deactivate();
		MqttState previousState = currState.getAndSet(MqttState.DISCONNECTED);
		if (previousState != MqttState.DISCONNECTED)
			process(currState.get());
	}

	public MqttState getMqttState()
	{
		return currState.get();
	}
}

package local.iot.devices.currencypuller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

import com.mobius.software.mqtt.parser.header.api.MQMessage;
import com.mobius.software.mqtt.parser.header.impl.Connack;

import local.iot.devices.commons.execution.TaskExecutor;
import local.iot.devices.commons.interfaces.ConnectionHandler;
import local.iot.devices.commons.interfaces.ConnectionListener;
import local.iot.devices.commons.net.mqtt.MqttClient;
import local.iot.devices.commons.util.JsonMapper;
import local.iot.devices.currencypuller.facilities.CountersFacility;
import local.iot.devices.currencypuller.facilities.LoggerFacility;
import local.iot.devices.currencypuller.facilities.StatisticsFacility;
import local.iot.devices.currencypuller.facilities.CountersFacility.Counter;
import local.iot.devices.currencypuller.rest.GrizzlyServer;
import local.iot.devices.currencypuller.tasks.RequestCurrencyTask;
import local.iot.devices.currencypuller.tasks.MqttState;

public class App implements ConnectionListener<MQMessage>
{
	private LoggerFacility logger;
	private CountersFacility counters;
	private StatisticsFacility statistics;

	private Config config;
	private JsonMapper mapper = new JsonMapper();
	private Requester requester;
	private TaskExecutor executor;
	private ConnectionHandler<MQMessage> mqttClient;
	private MqttTaskListener mqttListener;
	private RatesContainer ratesContainer;

	private GrizzlyServer server;

	public App()
	{
		this.config = Config.load();
		this.logger = LoggerFacility.getInstance(config.getLoggerLevel());
		this.counters = CountersFacility.getInstance();
		this.statistics = StatisticsFacility.getInstance();
		this.requester = new Requester();
		this.ratesContainer = new RatesContainer();
		this.server = GrizzlyServer.init(this, URI.create(config.getApiUrl()));
	}

	public void init() throws IOException
	{
		final ConnectionListener<MQMessage> listener = this;
		mqttClient = new MqttClient(config.getMqttServerHost(), config.getMqttServerPort(), config.getMqttClientThreads(), listener);

		executor = new TaskExecutor(config.getCoreThreads(), config.getMqttCleintThreadPoolName(), config.getCoreExecutorTerminateTimeout());
		executor.scheduleTask(new RequestCurrencyTask(requester, config.getCurrencyApiUrl(), ratesContainer), 0, config.getCurrencyPollPeriod(), config.getCurrencyPollTimeUnit());

		mqttListener = new MqttTaskListener(executor, config, mqttClient, mapper, requester, ratesContainer);
		mqttListener.process(MqttState.INIT);
	}

	public void terminate()
	{
		server.terminate();
		requester.stopClient();
		executor.shutdown();
		mqttClient.stop();
	}

	@Override
	public void messagReceived(InetSocketAddress address, MQMessage message)
	{
		counters.increment(Counter.MQTT_RECEIVED);
		switch (message.getType())
		{
		case CONNACK:
			Connack connack = (Connack) message;
			switch (connack.getReturnCode())
			{
			case ACCEPTED:
				logger.info("mqtt connect accepted");
				mqttListener.process(MqttState.CONNECTED);
				break;
			case BAD_USER_OR_PASS:
			case IDENTIFIER_REJECTED:
			case NOT_AUTHORIZED:
			case SERVER_UNUVALIABLE:
			case UNACCEPTABLE_PROTOCOL_VERSION:
				logger.warn("MQTT server rejected connection:" + connack.getReturnCode());
				mqttListener.process(MqttState.DISCONNECTED);
				break;
			}
			break;
		case PINGRESP:
			break;
		case PUBACK:
			break;
		case PUBREC:
			break;
		case PUBCOMP:
			break;
		case CONNECT:
		case DISCONNECT:
		case PINGREQ:
		case PUBLISH:
		case PUBREL:
		case SUBACK:
		case SUBSCRIBE:
		case UNSUBACK:
		case UNSUBSCRIBE:
			logger.warn("received unexpected " + message.getType() + " from server");
			mqttListener.process(MqttState.DISCONNECTED);
			break;
		}
	}

	@Override
	public void connectionLost(InetSocketAddress address)
	{
		logger.warn("lost connection to " + address);
		counters.increment(Counter.ERROR);
		mqttListener.process(MqttState.DISCONNECTED);
	}

	public CountersFacility getCountersFacility()
	{
		return counters;
	}

	public MqttState getMqttState()
	{
		return mqttListener.getMqttState();
	}

	public StatisticsFacility getStatisticsFacility()
	{
		return statistics;
	}

	public RatesContainer getRatesContainer()
	{
		return ratesContainer;
	}

	public Config getConfig()
	{
		return config;
	}
}

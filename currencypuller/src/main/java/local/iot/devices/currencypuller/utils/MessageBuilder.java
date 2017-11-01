package local.iot.devices.currencypuller.utils;

import com.mobius.software.mqtt.parser.avps.QoS;
import com.mobius.software.mqtt.parser.avps.Text;
import com.mobius.software.mqtt.parser.avps.Topic;
import com.mobius.software.mqtt.parser.header.impl.Connect;
import com.mobius.software.mqtt.parser.header.impl.Publish;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import local.iot.devices.currencypuller.Config;

public class MessageBuilder
{
	public static Connect buildConnect(Config config)
	{
		return new Connect(config.getMqttUsername(), config.getMqttPassword(), config.getMqttClientId(), true, 0, null);
	}

	public static Publish buildPublish(String topic, String data)
	{
		Publish publish = new Publish();
		publish.setTopic(new Topic(new Text(topic), QoS.AT_MOST_ONCE));
		ByteBuf content = Unpooled.buffer(data.length()).writeBytes(data.getBytes());
		publish.setContent(content);
		return publish;
	}
}

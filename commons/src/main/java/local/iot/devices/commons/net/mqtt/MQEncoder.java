package local.iot.devices.commons.net.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.mobius.software.mqtt.parser.MQParser;
import com.mobius.software.mqtt.parser.header.api.MQMessage;

@Sharable
public class MQEncoder extends MessageToByteEncoder<MQMessage>
{
	@Override
	protected void encode(ChannelHandlerContext ctx, MQMessage message, ByteBuf out) throws Exception
	{
		ByteBuf buf = MQParser.encode(message);
		out.writeBytes(buf);
	}
}
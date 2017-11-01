package local.iot.devices.commons.net.mqtt;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.mobius.software.mqtt.parser.header.api.MQMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import local.iot.devices.commons.interfaces.ConnectionListener;

@Sharable
public class MessageHandler extends SimpleChannelInboundHandler<MQMessage>
{
	private ConnectionListener<MQMessage> listener;

	public MessageHandler(ConnectionListener<MQMessage> listener)
	{
		this.listener = listener;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MQMessage message) throws Exception
	{
		Channel channel = ctx.channel();
		listener.messagReceived((InetSocketAddress) channel.remoteAddress(), message);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception
	{
		SocketAddress address = ctx.channel().remoteAddress();
		listener.connectionLost((InetSocketAddress) address);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
	{
		ctx.flush();
	}
}
package local.iot.devices.commons.net.mqtt;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

import com.mobius.software.mqtt.parser.header.api.MQMessage;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import local.iot.devices.commons.interfaces.ConnectionHandler;
import local.iot.devices.commons.interfaces.ConnectionListener;

public class MqttClient extends ConnectionHandler<MQMessage>
{
	private static final Logger logger = Logger.getLogger(MqttClient.class);

	private NioEventLoopGroup workerGroup;
	private Bootstrap bootstrap;
	private Channel channel;

	public MqttClient(String serverHost, int serverPort, int numberOfThreads, ConnectionListener<MQMessage> listener)
	{
		super(serverHost, serverPort, numberOfThreads, listener);
	}

	@Override
	public void start()
	{
		InetSocketAddress serverAddress = new InetSocketAddress(serverHost, serverPort);

		this.workerGroup = new NioEventLoopGroup(numberOfThreads);

		this.bootstrap = new Bootstrap();
		this.bootstrap.group(workerGroup);
		this.bootstrap.channel(NioSocketChannel.class);
		this.bootstrap.handler(new ChannelInitializer<SocketChannel>()
		{
			@Override
			public void initChannel(SocketChannel ch) throws Exception
			{
				ch.pipeline().addLast(new MQDecoder());
				ch.pipeline().addLast(new MQEncoder());
				ch.pipeline().addLast(new MessageHandler(listener));
				ch.pipeline().addLast(new ExceptionHandler());
			}
		});

		try
		{
			logger.info("connecting to server " + serverAddress);
			this.channel = bootstrap.connect(serverAddress).sync().channel();
		} catch (InterruptedException e)
		{
			logger.error("AN ERROR OCCURED WHILE CONNECTING TO " + serverAddress, e);
		}
	}

	@Override
	public void stop()
	{
		try
		{
			if (channel != null)
				channel.close().sync();
			if (workerGroup != null)
				workerGroup.shutdownGracefully();
		} catch (InterruptedException e)
		{
			logger.error("AN ERROR OCCURED WHILE SHUTTING DOWN", e);
		}
	}

	@Override
	public void sendMessage(MQMessage message)
	{
		channel.writeAndFlush(message);
	}
}

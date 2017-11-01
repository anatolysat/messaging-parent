package local.iot.devices.commons.net.mqtt;

import java.net.SocketAddress;

import org.apache.log4j.Logger;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

@Sharable
public class ExceptionHandler extends ChannelDuplexHandler
{
	private static final Logger logger = Logger.getLogger(ExceptionHandler.class);
	private static final String separator = ",";

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	{
		SocketAddress address = ctx.channel().remoteAddress();
		if (ctx.channel().isOpen())
			ctx.channel().close();

		StringBuilder sb = new StringBuilder();
		sb.append(ctx.channel().localAddress()).append(separator);
		sb.append(address).append(separator);
		sb.append(cause.getClass().getName().substring(cause.getClass().getName().lastIndexOf(".") + 1)).append(separator);
		sb.append(cause.getMessage().substring(cause.getMessage().lastIndexOf(".") + 1));
		logger.error(sb.toString());
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise)
	{
		ctx.connect(remoteAddress, localAddress, promise.addListener(new ChannelFutureListener()
		{
			@Override
			public void operationComplete(ChannelFuture future)
			{
				if (!future.isSuccess())
					logger.error("an error occured while connect");
			}
		}));
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
	{
		ctx.write(msg, promise.addListener(new ChannelFutureListener()
		{
			@Override
			public void operationComplete(ChannelFuture future)
			{
				if (!future.isSuccess())
				{
					future.cause().printStackTrace();
					logger.error("an error occured while write");
				}
			}
		}));
	}
}

package local.iot.devices.commons.net.mqtt;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

@Sharable
public class MqttIdleStateHandler extends IdleStateHandler
{
	public MqttIdleStateHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit)
	{
		super(readerIdleTime, writerIdleTime, allIdleTime, unit);
	}

	@Override
	protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception
	{
		if (evt.state() == IdleState.READER_IDLE)
			ctx.close();

		super.channelIdle(ctx, evt);
	}
}

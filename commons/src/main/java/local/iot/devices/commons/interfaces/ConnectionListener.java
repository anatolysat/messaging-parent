package local.iot.devices.commons.interfaces;

import java.net.InetSocketAddress;

public interface ConnectionListener<T>
{
	void messagReceived(InetSocketAddress address, T message);

	void connectionLost(InetSocketAddress address);
}

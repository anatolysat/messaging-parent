package local.iot.devices.commons.interfaces;

public abstract class ConnectionHandler<T>
{
	protected String serverHost;
	protected int serverPort;
	protected int numberOfThreads;
	protected ConnectionListener<T> listener;

	public ConnectionHandler(String serverHost, int serverPort, int numberOfThreads, ConnectionListener<T> listener)
	{
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.numberOfThreads = numberOfThreads;
		this.listener = listener;
	}

	public abstract void start();

	public abstract void stop();

	public abstract void sendMessage(T message);
}

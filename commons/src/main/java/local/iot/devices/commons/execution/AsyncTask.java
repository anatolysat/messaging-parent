package local.iot.devices.commons.execution;

public abstract class AsyncTask<T> implements Task
{
	protected AsyncListener<T> listener;

	public AsyncTask(AsyncListener<T> listener)
	{
		this.listener = listener;
	}

	@Override
	public void errorOccured(Exception e)
	{
		listener.errorOccured(e);
	}
}

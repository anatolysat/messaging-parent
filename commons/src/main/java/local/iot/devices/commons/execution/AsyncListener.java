package local.iot.devices.commons.execution;

public interface AsyncListener<T>
{
	void process(T result);
	
	void errorOccured(Exception e);
}

package local.iot.devices.commons.execution;

public interface Task
{
	void execute();

	void errorOccured(Exception e);
}

package local.iot.devices.commons.execution;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TaskExecutor
{
	private static final Log logger = LogFactory.getLog(TaskExecutor.class);

	private ExecutorService executor;
	private Timer timer = new Timer();
	private LinkedBlockingQueue<Task> queue = new LinkedBlockingQueue<>();
	private int terminateTimeout;
	private boolean isRunning = true;

	public TaskExecutor(int numberOfThreads, String threadPoolName, int terminateTimeout)
	{
		this.terminateTimeout = terminateTimeout;
		ThreadFactory threadFactory = getThreadFactory(threadPoolName);
		executor = Executors.newFixedThreadPool(numberOfThreads, threadFactory);
		for (int i = 0; i < numberOfThreads; i++)
			executor.submit(new Worker());
	}

	public void submitTask(Task task)
	{
		this.queue.offer(task);
	}

	public void scheduleTask(Task task, int delay, int period, TimeUnit unit)
	{
		if (isRunning)
		{
			timer.scheduleAtFixedRate(new TimerTask()
			{
				@Override
				public void run()
				{
					if (isRunning)
						task.execute();
					else
						logger.warn("Discarding timer task due to termination");
				}
			}, unit.toMillis(delay), unit.toMillis(period));
		}
	}

	public void scheduleTask(Task task, int delay, TimeUnit unit)
	{
		if (isRunning)
		{
			timer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					if (isRunning)
						task.execute();
					else
						logger.warn("Discarding timer task due to termination");
				}
			}, unit.toMillis(delay));
		}
	}

	public void shutdown()
	{
		this.isRunning = false;
		executor.shutdown();
		try
		{
			boolean isTerminated = executor.awaitTermination(terminateTimeout, TimeUnit.MILLISECONDS);
			if (!isTerminated)
			{
				logger.warn("Termination timeout. Performing forced shutdown");
				executor.shutdownNow();
			}
		} catch (InterruptedException e)
		{
			logger.error("AN ERROR OCCURED WHILE STOPING", e);
		}
	}

	private ThreadFactory getThreadFactory(String namePrefix)
	{
		return new ThreadFactory()
		{
			final AtomicLong count = new AtomicLong(0);

			@Override
			public Thread newThread(Runnable r)
			{
				Thread thread = new Thread(r);

				if (namePrefix != null)
					thread.setName(namePrefix + "-" + count.getAndIncrement());

				return thread;
			}
		};
	}

	private class Worker implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				while (isRunning)
				{
					Task task = queue.poll(100, TimeUnit.MILLISECONDS);
					if (task != null)
					{
						if (isRunning)
							task.execute();
						else
							logger.warn("Discarding task due to termination");
					}
				}
			} catch (InterruptedException e)
			{
				logger.error("TASK INTERRUPTED", e);
			}
		}
	}
}

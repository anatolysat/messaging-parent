package local.iot.devices.currencypuller.facilities;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import local.iot.devices.currencypuller.rest.model.CounterValue;
import local.iot.devices.currencypuller.rest.model.Counters;

public class CountersFacility
{
	private static CountersFacility INSTANCE;

	public enum Counter
	{
		ERROR
		{
			@Override
			public String getName()
			{
				return "errors";
			}
		},
		CURRENCY_API_CALL
		{
			@Override
			public String getName()
			{
				return "currency api calls";
			}
		},
		MQTT_SENT
		{
			@Override
			public String getName()
			{
				return "sent mqtt messages";
			}
		},
		MQTT_RECEIVED
		{
			@Override
			public String getName()
			{
				return "received mqtt messages";
			}
		};

		public abstract String getName();
	}

	private EnumMap<Counter, AtomicInteger> counters = new EnumMap<>(Counter.class);

	private CountersFacility()
	{
		for (Counter counter : Counter.values())
			counters.put(counter, new AtomicInteger(0));
	}

	public static CountersFacility getInstance()
	{
		if (INSTANCE == null)
			INSTANCE = new CountersFacility();
		return INSTANCE;
	}

	public void increment(Counter counter)
	{
		counters.get(counter).incrementAndGet();
	}

	public Counters getCounters()
	{
		List<CounterValue> values = new ArrayList<>();
		for (Entry<Counter, AtomicInteger> entry : this.counters.entrySet())
		{
			String cName = entry.getKey().getName();
			Long cValue = Long.valueOf(entry.getValue().get());
			values.add(new CounterValue(cName, cValue));
		}
		return new Counters(values);
	}
}

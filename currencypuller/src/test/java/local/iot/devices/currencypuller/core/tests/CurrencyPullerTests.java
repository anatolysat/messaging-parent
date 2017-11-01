package local.iot.devices.currencypuller.core.tests;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Test;

import local.iot.devices.currencypuller.App;

public class CurrencyPullerTests
{
	@Test
	public void testManualRun()
	{
		try
		{
			App app = new App();
			app.init();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Press any key to stop:");
			br.readLine();
			app.terminate();
		} catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
}

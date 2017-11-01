package local.iot.devices.currencypuller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{
	public static void main(String[] args)
	{
		App app = new App();
		try
		{
			app.init();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Press any key to stop:");
			br.readLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			app.terminate();
			System.exit(0);
		}
	}
}

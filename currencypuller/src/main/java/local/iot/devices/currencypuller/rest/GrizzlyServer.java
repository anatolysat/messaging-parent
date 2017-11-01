package local.iot.devices.currencypuller.rest;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import local.iot.devices.currencypuller.App;

public class GrizzlyServer
{
	private HttpServer server;

	private GrizzlyServer(App app, URI uri)
	{
		final ResourceConfig rc = new ResourceConfig().packages("local.iot.devices.currencypuller.rest").register(new RestController(app));
		server = GrizzlyHttpServerFactory.createHttpServer(uri, rc);
	}

	public static GrizzlyServer init(App app, URI uri)
	{
		return new GrizzlyServer(app, uri);
	}

	public void terminate()
	{
		server.shutdown();
	}
}

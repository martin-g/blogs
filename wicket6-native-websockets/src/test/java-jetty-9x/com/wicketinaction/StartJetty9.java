package com.wicketinaction;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Starts Jetty9 server
 */
public class StartJetty9
{
	public static void main(String[] args) throws Exception
	{
		Server server = new Server();

		// HTTP connector
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(8080);
		connector.setIdleTimeout(30000);
		connector.setSoLingerTime(-1);

		// Set the connectors
		server.setConnectors(new Connector[] { connector });

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar("src/main/webapp");

		server.setHandler(webapp);

		server.start();
//		server.dumpStdErr();
		server.join();
	}
}

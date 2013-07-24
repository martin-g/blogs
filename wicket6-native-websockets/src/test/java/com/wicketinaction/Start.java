package com.wicketinaction;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start {
    public static void main(String[] args) throws Exception {


	    HttpConfiguration http_config = new HttpConfiguration();
	    http_config.setSecureScheme("https");
	    http_config.setSecurePort(8443);
	    http_config.setOutputBufferSize(32768);
	    http_config.setRequestHeaderSize(8192);
	    http_config.setResponseHeaderSize(8192);
	    http_config.setSendServerVersion(true);
	    http_config.setSendDateHeader(false);

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server, new HttpConnectionFactory(http_config));

        // Set some timeout options to make debugging easier.
//        connector.setMaxIdleTime(timeout);
        connector.setSoLingerTime(-1);
        connector.setPort(8080);
        server.addConnector(connector);

	    Resource keystore = Resource.newClassPathResource("/keystore");
	    if (keystore != null && keystore.exists()) {
		    // if a keystore for a SSL certificate is available, start a SSL
		    // connector on port 8443.
		    // By default, the quickstart comes with a Apache Wicket Quickstart
		    // Certificate that expires about half way september 2021. Do not
		    // use this certificate anywhere important as the passwords are
		    // available in the source.

		    SslContextFactory factory = new SslContextFactory();
		    factory.setKeyStoreResource(keystore);
		    factory.setKeyStorePassword("wicket");
		    factory.setTrustStoreResource(keystore);
		    factory.setKeyManagerPassword("wicket");

		    // SSL HTTP Configuration
		    HttpConfiguration https_config = new HttpConfiguration(http_config);
		    https_config.addCustomizer(new SecureRequestCustomizer());

		    // SSL Connector
		    ServerConnector sslConnector = new ServerConnector(server,
				    new SslConnectionFactory(factory,"http/1.1"),
				    new HttpConnectionFactory(https_config));
		    sslConnector.setPort(8443);
		    server.addConnector(sslConnector);

		    System.out.println("SSL access to the quickstart has been enabled on port 8443");
		    System.out.println("You can access the application using SSL on https://localhost:8443");
		    System.out.println();
	    }

        WebAppContext bb = new WebAppContext();
        bb.setServer(server);
        bb.setContextPath("/");
        bb.setWar("src/main/webapp");

        // START JMX SERVER
        // MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        // MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
        // server.getContainer().addEventListener(mBeanContainer);
        // mBeanContainer.start();

        server.setHandler(bb);

        try {
            System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
            server.start();
            System.in.read();
            System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
            server.stop();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

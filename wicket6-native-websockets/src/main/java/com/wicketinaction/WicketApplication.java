package com.wicketinaction;

import com.wicketinaction.charts.ChartWebSocketResource;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.https.HttpsConfig;
import org.apache.wicket.protocol.https.HttpsMapper;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 */
public class WicketApplication extends WebApplication
{
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public void init()
	{
		super.init();

		setRootRequestMapper(new HttpsMapper(getRootRequestMapper(), new HttpsConfig()));

		mountPage("/behavior", WebSocketBehaviorDemoPage.class);
		mountPage("/resource", WebSocketResourceDemoPage.class);

		setRootRequestMapper(new HttpsMapper(getRootRequestMapper(), new HttpsConfig()));

		getSharedResources().add(ChartWebSocketResource.NAME, new ChartWebSocketResource());
	}
}

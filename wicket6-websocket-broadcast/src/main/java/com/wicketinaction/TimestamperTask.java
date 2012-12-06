package com.wicketinaction;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.ws.IWebSocketSettings;
import org.apache.wicket.protocol.ws.api.WebSocketPushBroadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimestamperTask implements Runnable
{
	private static final Logger log = LoggerFactory.getLogger(TimestamperTask.class);

	private final Application application;
	
	public TimestamperTask(Application application)
	{
		this.application = application;
	}

	public void run()
	{
		log.info("Sending timestamp to feed");
		FeedItem feedItem = new FeedItem("------ TIMESTAMP --------");
		IWebSocketSettings webSocketSettings = IWebSocketSettings.HOLDER.get(application);
		WebSocketPushBroadcaster broadcaster =
				new WebSocketPushBroadcaster(webSocketSettings.getConnectionRegistry());
		broadcaster.broadcastAll(application, feedItem);
	}
}

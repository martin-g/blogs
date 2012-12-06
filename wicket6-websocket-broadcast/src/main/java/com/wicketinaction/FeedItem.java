package com.wicketinaction;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage;

public class FeedItem implements IWebSocketPushMessage, Serializable
{
	private static final long serialVersionUID = 1L;

	private final String content;
	private final Date timestamp;

	public FeedItem(String content)
	{
		this.content = content;
		this.timestamp = new Date();
	}

	public String getContent()
	{
		return content;
	}

	public Date getTimestamp()
	{
		return timestamp;
	}

	@Override
	public String toString()
	{
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(timestamp) + ": " + content;
	}

}

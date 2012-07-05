/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wicketinaction.charts;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.protocol.ws.api.IWebSocketConnection;
import org.apache.wicket.protocol.ws.api.IWebSocketConnectionRegistry;
import org.apache.wicket.protocol.ws.api.SimpleWebSocketConnectionRegistry;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage;

/**
 * @since
 */
public class WebSocketChart extends Panel
{
	public WebSocketChart(final String id)
	{
		super(id);

		add(new ChartUpdatingBehavior());
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(new ChartsResourceReference()));
	}

	private static class ChartUpdatingBehavior extends WebSocketBehavior
	{
		@Override
		protected void onConnect(ConnectedMessage message)
		{
			super.onConnect(message);
			UpdateTask updateTask = new UpdateTask(message.getApplication(), message.getSessionId(), message.getPageId());
			Executors.newScheduledThreadPool(1).schedule(updateTask, 1, TimeUnit.SECONDS);
		}
	}

	private static class UpdateTask implements Runnable
	{
		private static final String JSON_SKELETON = "{ \"year\": \"%s\", \"field\": \"%s\", \"value\": %s }";

		private final String applicationName;
		private final String sessionId;
		private final int pageId;

		private final Record[] data;

		private UpdateTask(Application application, String sessionId, int pageId)
		{
			this.applicationName = application.getName();
			this.sessionId = sessionId;
			this.pageId = pageId;

			Random randomGenerator = new Random();
			this.data = new Record[1000];
			for (int i = 0; i < 1000; i++)
			{
				Record r = new Record();
				r.year = 2000 + i;
				r.field = (i % 2 == 0) ? "Company 1" : "Company 2";
				r.value = randomGenerator.nextInt(1500);
				data[i] = r;
			}
		}

		@Override
		public void run()
		{
			IWebSocketConnectionRegistry webSocketConnectionRegistry = new SimpleWebSocketConnectionRegistry();
			int dataIndex = 0;

			while (true && dataIndex < data.length)
			{
				Application application = Application.get(applicationName);
				IWebSocketConnection connection = webSocketConnectionRegistry.getConnection(application, sessionId, pageId);
				if (connection == null || !connection.isOpen())
				{
					return;
				}

				Record record = data[dataIndex++];
				String json = String.format(JSON_SKELETON, record.year, record.field, record.value);

				try
				{
					connection.sendMessage(json);
				} catch (IOException e)
				{
					e.printStackTrace();
					return;
				}

				try
				{
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
					return;
				}
			}
		}
	}

	private static class Record
	{
		private int year;
		private String field;
		private int value;
	}
}

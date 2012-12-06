package com.wicketinaction;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.WebSocketRequestHandler;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.protocol.ws.api.event.WebSocketPushPayload;
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage;
import org.apache.wicket.protocol.ws.api.message.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeedPage extends BasePage {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(FeedPage.class);
	private List<String> messages = Collections
			.synchronizedList(new LinkedList<String>());
	private WebMarkupContainer container;

	@Override
	protected void onInitialize() {
		super.onInitialize();

		setDefaultModel(new CompoundPropertyModel<FeedPage>(this));
		container = new WebMarkupContainer("container");
		add(container.setOutputMarkupId(true));
		container.add(new ListView<String>("messages",
				new PropertyModel<List<String>>(this, "messages")) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<String> item) {
				item.setDefaultModel(CompoundPropertyModel.of(item
						.getDefaultModel()));
				item.add(new Label("message", item.getModelObject()));
			}
		});

		add(new WebSocketBehavior() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onConnect(ConnectedMessage message) {
				super.onConnect(message);
				log.info("Client connected");
			}

			@Override
			protected void onMessage(WebSocketRequestHandler handler,
					TextMessage message) {
				log.info("Received message {}", message.getText());
			}

			@Override
			public void onException(Component component,
					RuntimeException exception) {
				log.warn("Got exception", exception);
			}
		});

	}

	@Override
	public void onEvent(IEvent<?> event) {
		if (event.getPayload() instanceof WebSocketPushPayload) {
			WebSocketPushPayload wsEvent = (WebSocketPushPayload) event
					.getPayload();
			handleMessage(wsEvent.getHandler(), (FeedItem) wsEvent.getMessage());
		}
	}

	public void handleMessage(WebSocketRequestHandler handler, FeedItem message) {
		messages.add(message.toString());
		while (messages.size() > 5) {
			messages.remove(0);
		}
		log.info("Push message {}. Number of messages {}.", message, messages.size());
		log.info("FeedPage: {}", handler);
		container.modelChanged();
		handler.add(container);
	}

}

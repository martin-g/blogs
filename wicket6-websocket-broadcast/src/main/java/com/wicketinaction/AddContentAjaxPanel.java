package com.wicketinaction;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.ws.IWebSocketSettings;
import org.apache.wicket.protocol.ws.api.WebSocketPushBroadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddContentAjaxPanel extends Panel
{
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(AddContentAjaxPanel.class);

	public AddContentAjaxPanel(String id)
	{
		super(id);
		setOutputMarkupId(true);
	}

	protected void onInitialize()
	{
		super.onInitialize();

		final Form<Void> ajaxForm = new Form<Void>("ajaxForm");
		add(ajaxForm.setOutputMarkupId(true));

		final Label latestMessage = new Label("latestMessage", Model.of(""));
		ajaxForm.add(latestMessage);
		final TextField<String> ajaxMessage = new TextField<String>(
				"ajaxMessage", Model.of(""));
		ajaxForm.add(ajaxMessage);
		ajaxForm.add(new AjaxButton("sendAjax") {
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				log.info("submit");
				String message = ajaxMessage.getModelObject();
				final FeedItem feedItem = new FeedItem(message);
				latestMessage.setDefaultModelObject(feedItem);
				ajaxMessage.setModelObject(null);
				target.add(ajaxForm);
				IWebSocketSettings webSocketSettings = IWebSocketSettings.Holder.get(getApplication());
				WebSocketPushBroadcaster broadcaster =
						new WebSocketPushBroadcaster(webSocketSettings.getConnectionRegistry());
				broadcaster.broadcastAll(Application.get(), feedItem);
			}
		});

	};

}

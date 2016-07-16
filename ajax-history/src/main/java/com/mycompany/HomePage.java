package com.mycompany;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		final Label content = new Label("content", "Home");
		content.setOutputMarkupId(true);
		add(content);

		add(new HistoryAjaxLink("home", "?home") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				updateContent(target, getId(), content);
			}
		});

		add(new HistoryAjaxLink("page1", "?page1") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				updateContent(target, getId(), content);
			}
		});

		add(new HistoryAjaxLink("page2", "#page2") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				updateContent(target, getId(), content);
			}
		});

		add(new HistoryAjaxLink("page3", "page3") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				updateContent(target, getId(), content);
			}
		});
    }

	private void updateContent(final AjaxRequestTarget target, String linkId, final Label content) {
		System.err.println("Clicked: " + linkId);
		content.setDefaultModelObject(linkId + "'s content");
		target.add(content);
	}
}

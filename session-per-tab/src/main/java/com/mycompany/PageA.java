package com.mycompany;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class PageA extends WebPage {
	private static final long serialVersionUID = 1L;

	public PageA(final PageParameters parameters) {
		super(parameters);

		add(new BookmarkablePageLink<>("pageB", PageB.class));

		setStatelessHint(false);
    }
}

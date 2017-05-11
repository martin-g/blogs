package com.mycompany;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class PageB extends WebPage {
	private static final long serialVersionUID = 1L;

	public PageB(final PageParameters parameters) {
		super(parameters);

		add(new BookmarkablePageLink<>("pageA", PageA.class));

		setStatelessHint(false);
    }
}

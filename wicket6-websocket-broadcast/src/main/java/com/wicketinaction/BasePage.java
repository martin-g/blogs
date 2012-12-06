package com.wicketinaction;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class BasePage extends WebPage {
	private static final long serialVersionUID = 1L;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new BookmarkablePageLink<Void>("feedLink", FeedPage.class));
		add(new BookmarkablePageLink<Void>("addContentLink",
				AddContentPage.class));
	}
}

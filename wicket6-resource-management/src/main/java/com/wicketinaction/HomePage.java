package com.wicketinaction;

import com.wicketinaction.resourcemanagement.bundles.BundlesPage;
import com.wicketinaction.resourcemanagement.dependency.DependencyPage;
import com.wicketinaction.resourcemanagement.positioning.ChildPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new BookmarkablePageLink<Void>("dependency", DependencyPage.class));
		add(new BookmarkablePageLink<Void>("bundles", BundlesPage.class));
		add(new BookmarkablePageLink<Void>("positioning", ChildPage.class));
	}

}

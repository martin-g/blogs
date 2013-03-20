package com.wicketinaction;

import com.wicketinaction.resourcemanagement.DojoResourceReference;
import com.wicketinaction.resourcemanagement.bundles.BundlesPage;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.head.filter.AbstractHeaderResponseFilter;
import org.apache.wicket.markup.head.filter.FilteredHeaderItem;
import org.apache.wicket.markup.head.filter.FilteringHeaderResponse;
import org.apache.wicket.markup.head.filter.OppositeHeaderResponseFilter;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A demo application for "Wicket 6 Resource management" blog article
 */
public class ResourceManagementApplication extends WebApplication
{
	/**
	 * The name of a filter used by FilteringHeaderResponse to render FilteredHeaderItems out of the <head>
	 * part of the page.
	 * See ChildPanel.java for a usage.
	 */
	public static final String JS_IN_FOOTER_FILTER_NAME = "myFilterName";

	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public void init()
	{
		super.init();

		if (usesDeploymentConfig()) {
			/**
			 * Register a resource replacement for Dojo.
			 * Any usage of DojoResourceReference will be replaced by the external Google url.
			 */
			addResourceReplacement(
					DojoResourceReference.get(),
					new UrlResourceReference(
							Url.parse("https://ajax.googleapis.com/ajax/libs/dojo/1.7.3/dojo/dojo.js")));
		}

		/**
		 * Registers a bundle that will be used whenever a component or a behavior contributes any of
		 * the bundle's resource references
		 */
		getResourceBundles().addJavaScriptBundle(ResourceManagementApplication.class, "bundle.js",
			new BundlesPage.BundledResourceReferenceA(),
			new BundlesPage.BundledResourceReferenceB(),
			new BundlesPage.BundledResourceReferenceC());


		/*
		 * Resource filtering
		 */
		setHeaderResponseDecorator(new IHeaderResponseDecorator()
		{
			@Override
			public IHeaderResponse decorate(IHeaderResponse response)
			{
				// the name of the bukcet used to write to the <head>
				String headBucket = "headBucket";

				List<FilteringHeaderResponse.IHeaderResponseFilter> filters = new ArrayList<FilteringHeaderResponse.IHeaderResponseFilter>();

				// a filter that accepts only FilteredHeaderItems with name == JS_IN_FOOTER_FILTER_NAME
				AbstractHeaderResponseFilter bucketAcceptingFilter = new AbstractHeaderResponseFilter(JS_IN_FOOTER_FILTER_NAME)
				{
					@Override
					public boolean accepts(HeaderItem item)
					{
						boolean accepts = false;
						if (item instanceof FilteredHeaderItem)
						{
							FilteredHeaderItem filteredHeaderItem = (FilteredHeaderItem) item;
							if (JS_IN_FOOTER_FILTER_NAME.equals(filteredHeaderItem.getFilterName()))
							{
								accepts = true;
							}
						}
						return accepts;
					}
				};
				filters.add(bucketAcceptingFilter);

				// a filter that accepts everything that is not accepted by 'bucketAcceptingFilter'
				filters.add(new OppositeHeaderResponseFilter(headBucket, bucketAcceptingFilter));

				return new FilteringHeaderResponse(response, headBucket, filters) {
					@Override
					public void render(HeaderItem item) {
						if (item instanceof StringHeaderItem) {
							StringHeaderItem stringHeaderItem = (StringHeaderItem) item;

							// make specific header item coming from <wicket:head> a priority one
							if (stringHeaderItem.getString().toString().contains("X-UA-Compatible")) {
								super.render(new PriorityHeaderItem(stringHeaderItem));
							} else {
								super.render(item);
							}
						}
						else {
							super.render(item);
						}
					}
				};
			}
		});
	}

	@Override
	public RuntimeConfigurationType getConfigurationType()
	{
		// change the configuration type to see how PackageResourceReferences deliver (non-)minified resources
		return RuntimeConfigurationType.DEPLOYMENT;
	}
}

package com.wicketinaction.requestmappers;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.wicketinaction.requestmappers.packageMounter.Page1;
import com.wicketinaction.requestmappers.packageMounter.Page2;

public class HomePage extends WebPage {

    public HomePage(PageParameters parameters) {
        super(parameters);
        
        add(getListParamsLink());
        add(getPlaceholdersLink());
        add(getPlaceholdersOptionalLink());
        
        add(new Link<Void>("bookarkableLink") {
            @Override
            public void onClick() {
                setResponsePage(APage.class);
            }
        });
        
        add(new BookmarkablePageLink<Void>("packagePage1", Page1.class));
        add(new BookmarkablePageLink<Void>("packagePage2", Page2.class));
    }
    

    private Component getPlaceholdersOptionalLink() {
        BookmarkablePageLink<Void> placeholdersOptionalLink = new BookmarkablePageLink<Void>("placeholdersOptionalLink", MountedMapperPlaceholdersPage.class);
        PageParameters params = placeholdersOptionalLink.getPageParameters();
        params.set("named1", "namedValue1");
        params.set("named3", "namedValue3");
        return placeholdersOptionalLink;
    }
    
    private Component getPlaceholdersLink() {
        BookmarkablePageLink<Void> placeholdersLink = new BookmarkablePageLink<Void>("placeholdersLink", MountedMapperPlaceholdersPage.class);
        PageParameters params = placeholdersLink.getPageParameters();
        params.set("named1", "namedValue1");
        params.set("named2", "namedValue2");
        params.set("named3", "namedValue3");
        return placeholdersLink;
    }

    private BookmarkablePageLink<Void> getListParamsLink() {
        BookmarkablePageLink<Void> listParamsLink = new BookmarkablePageLink<Void>("listParamsLink", MountedMapperPage.class);
        PageParameters params = listParamsLink.getPageParameters();
        params.set(0, "indexedValue0");
        params.set(1, "indexedValue1");
        params.set(2, "indexedValue2");
        params.set("named0", "namedValue0");
        params.set("named1", "namedValue1");
        params.set("named2", "namedValue2");
        return listParamsLink;
    }
}

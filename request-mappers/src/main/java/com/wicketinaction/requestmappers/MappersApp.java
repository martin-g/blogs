package com.wicketinaction.requestmappers;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import com.wicketinaction.requestmappers.packageMounter.BasePage;
import com.wicketinaction.requestmappers.resources.images.ImageResourceReference;
import com.wicketinaction.requestmappers.resources.images.ImageResourcesPage;

public class MappersApp extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();
    
        mountResource("images/${name}", new ImageResourceReference());
        mountPage("imagesPage", ImageResourcesPage.class);
        
        mountPage("mounted", MountedMapperPage.class);
        mountPage("mounted2/${named1}/#{named2}/const/${named3}", MountedMapperPlaceholdersPage.class);
        
        mountPackage("package", BasePage.class);
        
        setRootRequestMapper(new RootMapper(getRootRequestMapper()));
    }
}

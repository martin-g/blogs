package com.wicketinaction.requestmappers;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class MountedMapperPlaceholdersPage extends WebPage {

    public MountedMapperPlaceholdersPage(PageParameters parameters) {
        super(parameters);
        
        add(new Label("named1", parameters.get("named1").toString()));
        add(new Label("named2", parameters.get("named2").toString()));
        add(new Label("named3", parameters.get("named3").toString()));
    }
}

package com.wicketinaction.requestmappers.packageMounter;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class BasePage extends WebPage {

    public BasePage(final PageParameters parameters) {
        super(parameters);
        
        add(new Label("pageName", getPageModel()));
    }

    protected IModel<String> getPageModel() {
        return new AbstractReadOnlyModel<String>() {

            @Override
            public String getObject() {
                return BasePage.this.getClass().getName();
            }
        };
    }
}

package com.wicketinaction.requestmappers;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters.NamedPair;
import org.apache.wicket.util.string.StringValue;

public class MountedMapperPage extends WebPage {

    public MountedMapperPage(PageParameters parameters) {
        super(parameters);
        
        int indexedCount = parameters.getIndexedCount();
        RepeatingView indexedView = new RepeatingView("indexed");
        for (int i = 0; i < indexedCount; i++) {
            StringValue indexed = parameters.get(i);
            indexedView.add(new Label(indexedView.newChildId(), indexed.toString()));
        }
        add(indexedView);
        
        List<NamedPair> namedParameters = parameters.getAllNamed();
        ListView<NamedPair> namedView = new ListView<NamedPair>("named", namedParameters) {
            
            @Override
            protected void populateItem(ListItem<NamedPair> item) {
                NamedPair namedPair = item.getModelObject();
                String name = namedPair.getKey();
                String value = namedPair.getValue();
                item.add(new Label("item", name + " = " + value));
            }
        };
        add(namedView);
    }
}

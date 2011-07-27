package com.wicketinaction.requestmappers.resources.images;

import java.util.Arrays;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;

public class ImageResourcesPage extends WebPage {

    /**
     * The image names for which dynamic images will be generated
     */
    private static final String[] IMAGE_NAMES = new String[] {"one", "two", "three"};
    
    public ImageResourcesPage(final PageParameters parameters) {
        super(parameters);
        
        final ResourceReference imagesResourceReference = new ImageResourceReference();
        final PageParameters imageParameters = new PageParameters();
        
        ListView<String> listView = new ListView<String>("list", Arrays.asList(IMAGE_NAMES)) {

            @Override
            protected void populateItem(ListItem<String> item) {
                String imageName = item.getModelObject();
                imageParameters.set("name", imageName);
                
                // generates nice looking url (the mounted one) to the current image
                CharSequence urlForWordAsImage = getRequestCycle().urlFor(imagesResourceReference, imageParameters);
                ExternalLink link = new ExternalLink("link", urlForWordAsImage.toString());
                link.setBody(Model.of(imageName));
                item.add(link);
                
            }
        };
        add(listView);
    }

}

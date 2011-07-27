package com.wicketinaction.requestmappers;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;

public class APage extends WebPage implements IMarkupResourceStreamProvider {

    public IResourceStream getMarkupResourceStream(MarkupContainer arg0,
            Class<?> arg1) {
        
        return new StringResourceStream("<html><body>A page</body></html>");
    }

}

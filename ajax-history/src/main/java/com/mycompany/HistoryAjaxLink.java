package com.mycompany;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.CharSequenceResource;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.apache.wicket.util.string.Strings;

import java.util.List;

/**
 *
 */
public abstract class HistoryAjaxLink extends AjaxLink<Void> {

    private final CharSequence historyUrl;

    public HistoryAjaxLink(final String id, final CharSequence historyUrl) {
        super(id);
        this.historyUrl = historyUrl;
    }

    @Override
    protected void onComponentTag(final ComponentTag tag) {
        super.onComponentTag(tag);

        tag.put("data-history", true);

        if (!Strings.isEmpty(historyUrl)) {
            tag.put("data-history-url", historyUrl);

            CharSequence historyTitle = getHistoryTitle();
            if (!Strings.isEmpty(historyTitle)) {
                tag.put("data-history-title", historyTitle);
            }
        }
    }

    protected CharSequence getHistoryTitle() {
        return null;
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);

        JQueryPluginResourceReference reference = new JQueryPluginResourceReference(HistoryAjaxLink.class, "wicket-ajax-history.js") {
            @Override
            public List<HeaderItem> getDependencies() {
                List<HeaderItem> dependencies = super.getDependencies();
                dependencies.add(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getWicketAjaxReference()));
                return dependencies;
            }
        };
        response.render(JavaScriptHeaderItem.forReference(reference));
    }
}

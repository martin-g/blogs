package com.mycompany.fileupload;

import com.mycompany.WicketApplication;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.bootstrap.Bootstrap;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Contributes all CSS/JS resources needed by http://blueimp.github.com/jQuery-File-Upload/
 */
public class FileUploadBehavior extends Behavior {

    /**
     * The name of the request parameter used for the multipart
     * Ajax request
     */
    public static final String PARAM_NAME = "FILE-UPLOAD";

    /**
     * Configures the connected component to render its markup id
     * because it is needed to initialize the JavaScript widget.
     * @param component
     */
    @Override
    public void bind(Component component) {
        super.bind(component);

        component.setOutputMarkupId(true);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        Bootstrap.renderHead(response);

        response.render(CssHeaderItem.forReference(
                new CssResourceReference(FileUploadBehavior.class, "bootstrap-image-gallery.min.css")));

        response.render(CssHeaderItem.forReference(
                new CssResourceReference(FileUploadBehavior.class, "jquery.fileupload-ui.css")));

        response.render(JavaScriptHeaderItem.forReference(
                component.getApplication().getJavaScriptLibrarySettings().getJQueryReference()));

        response.render(JavaScriptHeaderItem.forReference(
                new JavaScriptResourceReference(FileUploadBehavior.class, "jquery.ui.widget.js")));

        response.render(JavaScriptHeaderItem.forReference(
                new JavaScriptResourceReference(FileUploadBehavior.class, "jquery.iframe-transport.js")));

        response.render(JavaScriptHeaderItem.forReference(
                new JavaScriptResourceReference(FileUploadBehavior.class, "tmpl.min.js")));

        response.render(JavaScriptHeaderItem.forReference(
                new JavaScriptResourceReference(FileUploadBehavior.class, "load-image.min.js")));

        response.render(JavaScriptHeaderItem.forReference(
                new JavaScriptResourceReference(FileUploadBehavior.class, "jquery.fileupload.js")));

        response.render(JavaScriptHeaderItem.forReference(
                new JavaScriptResourceReference(FileUploadBehavior.class, "jquery.fileupload-fp.js")));

        response.render(JavaScriptHeaderItem.forReference(
                new JavaScriptResourceReference(FileUploadBehavior.class, "jquery.fileupload-ui.js")));


        PackageTextTemplate jsTmpl = new PackageTextTemplate(FileUploadBehavior.class, "main.js");
        Map<String, Object> variables = new HashMap<String, Object>();

        variables.put("componentMarkupId", component.getMarkupId());
        variables.put("url", component.urlFor(new FileUploadResourceReference(WicketApplication.BASE_FOLDER), null));
        variables.put("paramName", PARAM_NAME);

        String s = jsTmpl.asString(variables);
        response.render(JavaScriptHeaderItem.forScript(s, "fileupload"));
    }
}

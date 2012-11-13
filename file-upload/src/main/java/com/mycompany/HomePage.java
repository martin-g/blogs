package com.mycompany;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * A demo page for the integration with
 * <a href="http://blueimp.github.com/jQuery-File-Upload/">jQuery-File-Upload</a>
 */
public class HomePage extends WebPage {

    public HomePage(final PageParameters parameters) {
        super(parameters);

        FileUploadPanel fileUpload = new FileUploadPanel("fileUpload");
        add(fileUpload);
    }

}

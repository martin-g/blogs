package com.mycompany.fileupload;

import org.apache.wicket.markup.html.panel.Panel;

/**
 * This bar contributes the toolbar with "Add files", "Start upload",
 * "Cancel upload" and "Delete all" buttons.
 */
public class FileUploadBar extends Panel {

    public FileUploadBar(String id) {
        super(id);

        add(new FileUploadBehavior());
    }
}

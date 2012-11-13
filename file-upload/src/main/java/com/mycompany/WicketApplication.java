package com.mycompany;

import com.mycompany.fileupload.FileManageResourceReference;
import com.mycompany.fileupload.FileUploadResourceReference;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 */
public class WicketApplication extends WebApplication
{
   public static final String BASE_FOLDER = "/tmp/fileUploader";

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

        mountResource("fileManager", new FileManageResourceReference(BASE_FOLDER));
		mountResource("fileUpload", new FileUploadResourceReference(BASE_FOLDER));
	}
}

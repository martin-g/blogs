package com.mycompany.parsley;

import org.apache.wicket.resource.JQueryPluginResourceReference;

/**
 *
 */
public class ParsleyJsReference extends JQueryPluginResourceReference
{
	public ParsleyJsReference()
	{
		super(ParsleyJsReference.class, "parsley.js");
	}
}

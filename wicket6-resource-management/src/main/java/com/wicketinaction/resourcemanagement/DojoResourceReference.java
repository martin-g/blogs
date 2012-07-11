package com.wicketinaction.resourcemanagement;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class DojoResourceReference extends JavaScriptResourceReference
{
	private static final long serialVersionUID = 1L;

	private static final DojoResourceReference INSTANCE = new DojoResourceReference();

	public static DojoResourceReference get()
	{
		return INSTANCE;
	}

	private DojoResourceReference()
	{
		super(DojoResourceReference.class, "dojo-1.7.3.js");
	}
}

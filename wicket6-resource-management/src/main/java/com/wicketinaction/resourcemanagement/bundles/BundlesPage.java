/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wicketinaction.resourcemanagement.bundles;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.resource.bundles.ConcatResourceBundleReference;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class BundlesPage extends WebPage
{
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(JavaScriptHeaderItem.forReference(new BundledResourceReferenceA()));
		response.render(JavaScriptHeaderItem.forReference(new BundledResourceReferenceB()));
		response.render(JavaScriptHeaderItem.forReference(new BundledResourceReferenceC()));

		response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(BundlesPage.class, "not-bundled.js")));

		response.render(JavaScriptHeaderItem.forReference(new MyBundle()));
	}

	public static class BundledResourceReferenceA extends JavaScriptResourceReference
	{
		public BundledResourceReferenceA()
		{
			super(BundledResourceReferenceA.class, "bundled-a.js");
		}
	}

	public static class BundledResourceReferenceB extends JavaScriptResourceReference
	{
		public BundledResourceReferenceB()
		{
			super(BundledResourceReferenceB.class, "bundled-b.js");
		}
	}

	public static class BundledResourceReferenceC extends JavaScriptResourceReference
	{
		public BundledResourceReferenceC()
		{
			super(BundledResourceReferenceC.class, "bundled-c.js");
		}
	}


	private static class MyBundle extends ConcatResourceBundleReference<JavaScriptReferenceHeaderItem>
	{
		public MyBundle()
		{
			super(MyBundle.class, "custom-bundle.js", getResources());

			if (Application.exists())
			{
				Application.get().getResourceReferenceRegistry().registerResourceReference(this);
			}
		}

		private static List<JavaScriptReferenceHeaderItem> getResources()
		{
			List<JavaScriptReferenceHeaderItem> references = new ArrayList<JavaScriptReferenceHeaderItem>();
			references.add(JavaScriptHeaderItem.forReference(new BundledResourceReferenceA()));
			references.add(JavaScriptHeaderItem.forReference(new BundledResourceReferenceB()));
			references.add(JavaScriptHeaderItem.forReference(new BundledResourceReferenceC()));

			return references;
		}
	}

}

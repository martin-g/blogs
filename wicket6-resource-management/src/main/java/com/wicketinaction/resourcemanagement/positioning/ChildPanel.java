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
package com.wicketinaction.resourcemanagement.positioning;

import com.wicketinaction.ResourceManagementApplication;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.filter.FilteredHeaderItem;

/**
 *
 */
public class ChildPanel extends BasePanel
{
	public ChildPanel(final String id)
	{
		super(id);
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		// renders a JavaScript contribution in the <head> part of the page
		response.render(JavaScriptHeaderItem.forScript("var context = 'ChildPanel';", ChildPanel.class.getSimpleName()));

		JavaScriptContentHeaderItem itemToFilter = JavaScriptHeaderItem.forScript("var context = 'ChildPanel - filtered';", ChildPanel.class.getSimpleName() + " - filtered");
		// using FilteredHeaderItem will render 'itemToFilter' in the HeaderResponseContainer that is assigned to
		// ResourceManagementApplication.JS_IN_FOOTER_FILTER_NAME
		response.render(new FilteredHeaderItem(itemToFilter, ResourceManagementApplication.JS_IN_FOOTER_FILTER_NAME));
	}
}

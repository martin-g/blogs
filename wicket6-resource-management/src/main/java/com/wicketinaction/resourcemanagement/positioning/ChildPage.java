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

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;

/**
 *
 */
public class ChildPage extends BasePage
{
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		// the following two header contributions would be rendered one next to the other, but the second one
		// uses PriorityHeaderItem to render itself at the top of the <head> part of the page

		response.render(JavaScriptHeaderItem.forScript("var context = 'ChildPage';", ChildPage.class.getSimpleName()));

		response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forScript("var context = 'Priority ChildPage';", ChildPage.class.getSimpleName() + " - priority")));

	}
}

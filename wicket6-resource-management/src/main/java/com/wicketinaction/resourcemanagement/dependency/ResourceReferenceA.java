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
package com.wicketinaction.resourcemanagement.dependency;

import java.util.Arrays;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.wicketinaction.resourcemanagement.DojoResourceReference;

/**
 * A resource reference that declares some dependencies
 */
public class ResourceReferenceA extends JavaScriptResourceReference {
	public ResourceReferenceA() {
		super(ResourceReferenceA.class, "a.js");
	}

	@Override
	protected String getMinifiedName() {
		return "a.compressed.js";
	}

	@Override
	public Iterable<? extends HeaderItem> getDependencies() {
		return Arrays.<HeaderItem>asList(JavaScriptHeaderItem
				.forReference(DojoResourceReference.get()), CssHeaderItem
				.forReference(new CssResourceReference(
						ResourceReferenceA.class, "a.css")));
	}
}

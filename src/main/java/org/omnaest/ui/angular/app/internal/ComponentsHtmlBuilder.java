/*

	Copyright 2017 Danny Kunz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


*/
package org.omnaest.ui.angular.app.internal;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.omnaest.ui.angular.app.component.ComponentRenderResult;

public class ComponentsHtmlBuilder
{
	private Map<String, String>	componentToHtml			= new LinkedHashMap<>();
	private Map<String, String>	componentToReference	= new LinkedHashMap<>();

	public Map<String, String> getComponentToHtml()
	{
		return this.componentToHtml;
	}

	public void addComponentHtml(String name, String reference, ComponentRenderResult result, boolean withReference)
	{
		this.addComponentHtml(name, reference, result.getHtml(), withReference);
	}

	public void addComponentHtml(String name, String reference, String html, boolean withReference)
	{
		this.componentToHtml.put(name, html);
		if (withReference)
		{
			this.componentToReference.put(name, reference);
		}
	}

	public String renderComponentReferences()
	{
		return this.componentToReference.values()
										.stream()
										.collect(Collectors.joining());
	}
}

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
package org.omnaest.ui.angular.app.component;

import java.util.List;
import java.util.Map;

import org.omnaest.ui.angular.app.internal.raw.RawHtmlElement;
import org.omnaest.ui.angular.app.service.Service;

public interface Component
{
	public static interface Function
	{
		public String getJavaScriptFunction();
	}

	public static interface NamedFunction extends Function
	{
		public String getName();
	}

	public static interface ServiceFunction extends Function
	{
		public List<Service> getDependencies();
	}

	public static interface RenderContext
	{
		public String getBaseUrl();
	}

	public ComponentRenderResult render(RenderContext context);

	/**
	 * Renders the xml reference used in other html fragments like <component1>
	 * 
	 * @return
	 */
	public RawHtmlElement renderReference(Map<String, String> bindings);

	/**
	 * @see #renderReference(Map)
	 * @return
	 */
	public RawHtmlElement renderReference();

	public String getName();

	public void addFunction(NamedFunction function);

	public void addFunction(ServiceFunction function);

	RawHtmlElement renderReference(Map<String, String> bindings, RawHtmlElement... transclusions);

	Component withTransclusion(List<Component> components);

	Component withTransclusion(Component... components);

	List<Component> getSubComponents();
}

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

public class DecoratorComponent<C extends Component> implements Component
{
	protected C component;

	public DecoratorComponent(C component)
	{
		super();
		this.component = component;
	}

	@Override
	public ComponentRenderResult render(RenderContext context)
	{
		return this.component.render(context);
	}

	@Override
	public String getName()
	{
		return this.component.getName();
	}

	@Override
	public void addFunction(NamedFunction function)
	{
		this.component.addFunction(function);
	}

	@Override
	public void addFunction(ServiceFunction function)
	{
		this.component.addFunction(function);
	}

	@Override
	public RawHtmlElement renderReference(Map<String, String> bindings)
	{
		return this.component.renderReference(bindings);
	}

	@Override
	public RawHtmlElement renderReference(Map<String, String> bindings, RawHtmlElement... transclusions)
	{
		return this.component.renderReference(bindings, transclusions);
	}

	@Override
	public RawHtmlElement renderReference()
	{
		return this.component.renderReference();
	}

	@Override
	public Component withTransclusion(List<Component> components)
	{
		return this.component.withTransclusion(components);
	}

	@Override
	public Component withTransclusion(Component... components)
	{
		return this.component.withTransclusion(components);
	}

	@Override
	public List<Component> getSubComponents()
	{
		return this.component.getSubComponents();
	}

}

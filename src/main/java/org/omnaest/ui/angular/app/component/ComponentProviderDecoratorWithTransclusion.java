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

@SuppressWarnings("unchecked")
public class ComponentProviderDecoratorWithTransclusion<C extends Component> extends ComponentProviderDecorator<C>
{
	public ComponentProviderDecoratorWithTransclusion(C component)
	{
		super(component);
	}

	public ComponentProvider<C> withTransclusion(List<ComponentProvider<?>> components)
	{
		return () -> (C) this.component.withTransclusion(components);
	}

	public ComponentProvider<C> withTransclusion(ComponentProvider<?>... components)
	{
		return () -> (C) this.component.withTransclusion(components);
	}

	public ComponentProvider<C> withTransclusion(Component... components)
	{
		return () -> (C) this.component.withTransclusion(components);
	}

}

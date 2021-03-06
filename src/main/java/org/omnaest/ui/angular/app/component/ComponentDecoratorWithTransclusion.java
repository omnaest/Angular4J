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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.omnaest.ui.angular.app.internal.raw.RawHtmlElement;

public class ComponentDecoratorWithTransclusion<C extends Component> extends ComponentDecorator<C>
{
	protected Supplier<List<ComponentProvider<? extends Component>>> transclusions;

	public ComponentDecoratorWithTransclusion(C component, Supplier<List<ComponentProvider<? extends Component>>> transclusions)
	{
		super(component);
		this.transclusions = transclusions;
	}

	@Override
	public RawHtmlElement renderReference(Map<String, String> bindings)
	{
		return super.renderReference(bindings, this.transclusions	.get()
																	.stream()
																	.map(provider -> provider.get())
																	.map(component -> component.renderReference())
																	.collect(Collectors.toList())
																	.toArray(new RawHtmlElement[0]));
	}

	@Override
	public RawHtmlElement renderReference()
	{
		Map<String, String> bindings = Collections.emptyMap();
		return this.renderReference(bindings);
	}

}

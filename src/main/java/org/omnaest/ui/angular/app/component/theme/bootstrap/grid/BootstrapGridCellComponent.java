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
package org.omnaest.ui.angular.app.component.theme.bootstrap.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.omnaest.ui.angular.app.component.Component;
import org.omnaest.ui.angular.app.component.ComponentProvider;
import org.omnaest.ui.angular.app.component.ComponentProviderDecoratorWithTransclusion;
import org.omnaest.ui.angular.app.component.HtmlComponent;
import org.omnaest.ui.angular.app.component.theme.GridComponent.Cell;

public class BootstrapGridCellComponent extends ComponentProviderDecoratorWithTransclusion<HtmlComponent> implements Cell
{
	private List<ComponentProvider<?>>	referenceComponents	= new ArrayList<>();
	private Supplier<Integer>			columns				= () -> 6;

	public BootstrapGridCellComponent(String name)
	{
		super(new HtmlComponent(name));

		this.init();
	}

	public BootstrapGridCellComponent setColumns(Supplier<Integer> columns)
	{
		this.columns = columns;
		return this;
	}

	private void init()
	{
		this.component.newDiv(div ->
		{
			div	.setClass("col-sm-" + this.columns.get())
				.addComponents(this.referenceComponents	.stream()
														.map(provider -> provider.get()))
				.setAttribute("ng-transclude", "");
		});
	}

	protected Cell addComponent(Component component)
	{
		this.referenceComponents.add(() -> component);
		return this;
	}

	protected Cell addComponent(ComponentProvider<? extends Component> component)
	{
		return this.addComponent(component.get());
	}

	@Override
	public ComponentProvider<HtmlComponent> withTransclusion(List<ComponentProvider<?>> components)
	{
		components.forEach(component -> this.addComponent(component));
		return super.withTransclusion(components);
	}

	@Override
	public ComponentProvider<HtmlComponent> withTransclusion(ComponentProvider<?>... components)
	{
		Arrays	.asList(components)
				.forEach(component -> this.addComponent(component));
		return super.withTransclusion(components);
	}

	@Override
	public ComponentProvider<HtmlComponent> withTransclusion(Component... components)
	{
		Arrays	.asList(components)
				.forEach(component -> this.addComponent(component));
		return super.withTransclusion(components);

	}

	@Override
	public ComponentProvider<HtmlComponent> withContent(Component... components)
	{
		return this.withTransclusion(components);
	}

	@Override
	public ComponentProvider<HtmlComponent> withContent(ComponentProvider<?>... components)
	{
		return this.withTransclusion(components);
	}

	@Override
	public ComponentProvider<HtmlComponent> withContent(List<ComponentProvider<?>> components)
	{
		return this.withTransclusion(components);
	}

}

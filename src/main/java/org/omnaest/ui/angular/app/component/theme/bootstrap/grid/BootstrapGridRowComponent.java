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
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.omnaest.ui.angular.app.component.Component;
import org.omnaest.ui.angular.app.component.ComponentProvider;
import org.omnaest.ui.angular.app.component.ComponentProviderDecoratorWithTransclusion;
import org.omnaest.ui.angular.app.component.HtmlComponent;
import org.omnaest.ui.angular.app.component.theme.GridComponent.Cell;
import org.omnaest.ui.angular.app.component.theme.GridComponent.Row;

public class BootstrapGridRowComponent extends ComponentProviderDecoratorWithTransclusion<HtmlComponent> implements Row
{
	private List<ComponentProvider<?>> referenceComponents = new ArrayList<>();

	public BootstrapGridRowComponent(String name)
	{
		super(new HtmlComponent(name));

		this.init();
	}

	private void init()
	{
		this.component.newDiv(div ->
		{
			div	.setClass("row")
				.addComponents(this.referenceComponents	.stream()
														.map(provider -> provider.get()))
				.setAttribute("ng-transclude", "");
		});

	}

	@Override
	public RowWithTransclusion newCell(Function<Cell, ComponentProvider<? extends Component>> cellConsumer)
	{
		return this.newCell(this.getDefaultColumnSize(), cellConsumer);
	}

	private Supplier<Integer> getDefaultColumnSize()
	{
		return () -> 12 / this.referenceComponents.size();
	}

	public RowWithTransclusion newCell(int columns, Function<Cell, ComponentProvider<? extends Component>> cellConsumer)
	{
		return this.newCell(() -> columns, cellConsumer);
	}

	public RowWithTransclusion newCell(Supplier<Integer> columns, Function<Cell, ComponentProvider<? extends Component>> cellConsumer)
	{
		this.createCell(cellConsumer, columns);
		return new RowWithTransclusion()
		{
			@Override
			public Component get()
			{
				return BootstrapGridRowComponent.this.component.withTransclusion(() -> BootstrapGridRowComponent.this.referenceComponents);
			}

			@Override
			public RowWithTransclusion newCell(Function<Cell, ComponentProvider<? extends Component>> cellConsumer)
			{
				BootstrapGridRowComponent.this.createCell(cellConsumer, BootstrapGridRowComponent.this.getDefaultColumnSize());
				return this;
			}

			@Override
			public RowWithTransclusion addCell(ComponentProvider<? extends Component> component)
			{
				return this.newCell(cell -> cell.withContent(component));
			}

			@Override
			public RowWithTransclusion addCell(int columns, ComponentProvider<? extends Component> component)
			{
				BootstrapGridRowComponent.this.createCell(cellConsumer, () -> columns);
				return this;
			}
		};
	}

	private void createCell(Function<Cell, ComponentProvider<? extends Component>> cellConsumer, Supplier<Integer> columns)
	{
		BootstrapGridCellComponent cell = new BootstrapGridCellComponent("gridCell").setColumns(columns);
		this.referenceComponents.add(cellConsumer.apply(cell));
	}

	@Override
	public RowWithTransclusion addCell(ComponentProvider<? extends Component> component)
	{
		return this.addCell(this.getDefaultColumnSize(), component);
	}

	@Override
	public RowWithTransclusion addCell(int columns, ComponentProvider<? extends Component> component)
	{
		return this.addCell(() -> columns, component);
	}

	public RowWithTransclusion addCell(Supplier<Integer> columns, ComponentProvider<? extends Component> component)
	{
		return this.newCell(columns, cell -> cell.withContent(component));
	}

}

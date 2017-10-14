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

import org.omnaest.ui.angular.app.component.Component;
import org.omnaest.ui.angular.app.component.ComponentProvider;
import org.omnaest.ui.angular.app.component.ComponentProviderDecoratorWithTransclusion;
import org.omnaest.ui.angular.app.component.HtmlComponent;
import org.omnaest.ui.angular.app.component.theme.GridComponent;

public class BootstrapGridComponent extends ComponentProviderDecoratorWithTransclusion<HtmlComponent> implements GridComponent
{
	private List<ComponentProvider<? extends Component>> referenceComponents = new ArrayList<>();

	public BootstrapGridComponent(String name)
	{
		super(new HtmlComponent(name));

		this.init();
	}

	private void init()
	{
		this.component.newDiv(div ->
		{
			div	.setClass("container")
				.addComponents(this.referenceComponents	.stream()
														.map(provider -> provider.get()))
				.setAttribute("ng-transclude", "");
		});
	}

	@Override
	public GridComponentWithTransclusion transclusionWith()
	{
		return new GridComponentWithTransclusion()
		{
			@Override
			public Component get()
			{
				return BootstrapGridComponent.this.component.withTransclusion(() -> BootstrapGridComponent.this.referenceComponents);
			}

			@Override
			public GridComponentWithTransclusion newRow(Function<Row, ComponentProvider<Component>> rowConsumer)
			{
				BootstrapGridComponent.this.createNewRow(rowConsumer);
				return this;
			}
		};
	}

	private void createNewRow(Function<Row, ComponentProvider<Component>> rowConsumer)
	{
		Row row = new BootstrapGridRowComponent("gridRow");
		this.referenceComponents.add(rowConsumer.apply(row));
	}

}

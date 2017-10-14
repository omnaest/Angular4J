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
package org.omnaest.ui.angular.app.component.theme;

import java.util.List;
import java.util.function.Function;

import org.omnaest.ui.angular.app.component.Component;
import org.omnaest.ui.angular.app.component.ComponentProvider;
import org.omnaest.ui.angular.app.component.HtmlComponent;

public interface GridComponent extends ComponentProvider<HtmlComponent>
{
	public static interface GridComponentWithTransclusion extends ComponentProvider<Component>
	{
		public GridComponentWithTransclusion newRow(Function<Row, ComponentProvider<Component>> row);
	}

	public static interface Row extends ComponentProvider<HtmlComponent>
	{
		public static interface RowWithTransclusion extends ComponentProvider<Component>
		{
			public RowWithTransclusion newCell(Function<Cell, ComponentProvider<? extends Component>> cell);

			public RowWithTransclusion addCell(ComponentProvider<? extends Component> component);

			public RowWithTransclusion addCell(int columns, ComponentProvider<? extends Component> component);
		}

		public RowWithTransclusion newCell(Function<Cell, ComponentProvider<? extends Component>> cell);

		public RowWithTransclusion addCell(ComponentProvider<? extends Component> component);

		public RowWithTransclusion addCell(int columns, ComponentProvider<? extends Component> component);

	}

	public static interface Cell
	{
		ComponentProvider<HtmlComponent> withContent(Component... components);

		ComponentProvider<HtmlComponent> withContent(ComponentProvider<?>... components);

		ComponentProvider<HtmlComponent> withContent(List<ComponentProvider<?>> components);
	}

	public GridComponentWithTransclusion transclusionWith();
}

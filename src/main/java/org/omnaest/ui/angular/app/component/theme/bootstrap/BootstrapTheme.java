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
package org.omnaest.ui.angular.app.component.theme.bootstrap;

import org.omnaest.ui.angular.app.component.theme.ButtonComponent;
import org.omnaest.ui.angular.app.component.theme.GridComponent;
import org.omnaest.ui.angular.app.component.theme.PanelComponent;
import org.omnaest.ui.angular.app.component.theme.Theme;
import org.omnaest.ui.angular.app.component.theme.bootstrap.button.BootstrapButtonComponent;
import org.omnaest.ui.angular.app.component.theme.bootstrap.grid.BootstrapGridComponent;
import org.omnaest.ui.angular.app.component.theme.bootstrap.panel.BootstrapPanelComponent;

public class BootstrapTheme implements Theme
{

	@Override
	public ButtonComponent newButton(String name)
	{
		return new BootstrapButtonComponent(name);
	}

	@Override
	public PanelComponent newPanel(String name)
	{
		return new BootstrapPanelComponent(name);
	}

	@Override
	public GridComponent newGridComponent(String name)
	{
		return new BootstrapGridComponent(name);
	}
}

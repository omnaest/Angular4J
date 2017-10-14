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
package org.omnaest.ui.angular.app;

import java.io.File;
import java.net.URI;
import java.util.Map;

import org.omnaest.ui.angular.app.component.Component;
import org.omnaest.ui.angular.app.component.ComponentProvider;

public interface AngularApplication
{
	public static interface RenderResult
	{
		public String getIndexHtml();

		public Map<String, String> getComponentsHtml();

	}

	public AngularApplication addComponent(Component component);

	public AngularApplication addComponent(ComponentProvider<? extends Component> component);

	public RenderResult renderHtml();

	public AngularApplication withTitle(String title);

	public AngularApplication withBaseUrl(URI baseUrl);

	public AngularApplication withBaseUrl(File baseUrl);

	AngularApplication withBaseUrl(String baseUrl);
}

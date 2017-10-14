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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.omnaest.ui.angular.app.component.Component;
import org.omnaest.ui.angular.app.component.Component.RenderContext;
import org.omnaest.ui.angular.app.component.ComponentProvider;
import org.omnaest.ui.angular.app.component.ComponentRenderResult;
import org.omnaest.ui.angular.app.internal.CSSBuilder;
import org.omnaest.ui.angular.app.internal.ComponentsHtmlBuilder;
import org.omnaest.ui.angular.app.internal.ScriptBuilder;
import org.omnaest.ui.angular.utils.ResourceLoader;

public class AngularApplicationImpl implements AngularApplication
{
	private String											baseUrl;
	private String											title;
	private List<ComponentProvider<? extends Component>>	componentProviders	= new ArrayList<>();

	@Override
	public AngularApplication withBaseUrl(URI baseUrl)
	{
		return this.withBaseUrl(baseUrl.toString());
	}

	@Override
	public AngularApplication withBaseUrl(File baseUrl)
	{
		return this.withBaseUrl(baseUrl.toURI());
	}

	@Override
	public AngularApplication withBaseUrl(String baseUrl)
	{
		this.baseUrl = baseUrl;
		return this;
	}

	@Override
	public RenderResult renderHtml()
	{
		ScriptBuilder scriptBuilder = new ScriptBuilder();
		ComponentsHtmlBuilder componentsHtmlBuilder = new ComponentsHtmlBuilder();
		CSSBuilder cssBuilder = new CSSBuilder();

		scriptBuilder.addJavaScript("app", ResourceLoader	.loadJavaScriptBinding(this)
															.get());

		List<? extends Component> components = this.componentProviders	.stream()
																		.map(provider -> provider.get())
																		.collect(Collectors.toList());

		components	.stream()
					.distinct()
					.forEach(component ->
					{
						boolean withReference = true;
						this.addToComponentsBuilder(scriptBuilder, componentsHtmlBuilder, cssBuilder, component, withReference);
					});
		components	.stream()
					.flatMap(component -> component	.getSubComponents()
													.stream())
					.distinct()
					.forEach(component ->
					{
						boolean withReference = false;
						this.addToComponentsBuilder(scriptBuilder, componentsHtmlBuilder, cssBuilder, component, withReference);
					});

		String indexHtml = ResourceLoader	.load(this, "/index.html")
											.replaceToken("${title}", StringUtils.defaultString(this.title))
											.replaceToken("${scripts}", scriptBuilder.render())
											.replaceToken("${css}", cssBuilder.render())
											.replaceToken("${components}", componentsHtmlBuilder.renderComponentReferences())
											.replaceToken("${baseUrl}", this.baseUrl != null ? this.baseUrl.toString() : "")
											.get();
		return new RenderResult()
		{
			@Override
			public String getIndexHtml()
			{
				return indexHtml;
			}

			@Override
			public Map<String, String> getComponentsHtml()
			{
				return componentsHtmlBuilder.getComponentToHtml();
			}
		};
	}

	private void addToComponentsBuilder(ScriptBuilder scriptBuilder, ComponentsHtmlBuilder componentsHtmlBuilder, CSSBuilder cssBuilder, Component component,
										boolean withReference)
	{
		String name = component.getName();
		ComponentRenderResult result = component.render(this.createRenderContext());
		String reference = component.renderReference()
									.asString();

		scriptBuilder.addJavaScriptFrom(name, result);

		componentsHtmlBuilder.addComponentHtml(name, reference, result, withReference);
		cssBuilder.addCssFrom(result);
	}

	private RenderContext createRenderContext()
	{
		return new RenderContext()
		{
			@Override
			public String getBaseUrl()
			{
				return AngularApplicationImpl.this.baseUrl == null ? null : AngularApplicationImpl.this.baseUrl.toString();
			}
		};
	}

	@Override
	public AngularApplication withTitle(String title)
	{
		this.title = title;
		return this;
	}

	@Override
	public AngularApplication addComponent(Component component)
	{
		return this.addComponent(() -> component);
	}

	@Override
	public AngularApplication addComponent(ComponentProvider<? extends Component> componentProvider)
	{
		this.componentProviders.add(componentProvider);
		return this;
	}

}
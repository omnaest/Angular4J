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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.omnaest.ui.angular.app.internal.JavaScriptFunctionBuilder;
import org.omnaest.ui.angular.app.internal.raw.RawCustomHtmlElement;
import org.omnaest.ui.angular.app.internal.raw.RawHtmlElement;
import org.omnaest.ui.angular.app.service.Service;
import org.omnaest.ui.angular.app.service.ServiceConsumer;
import org.omnaest.ui.angular.utils.ResourceLoader;

public abstract class BasicComponent implements Component, ServiceConsumer
{
	protected String name;

	private List<Service>						services		= new ArrayList<>();
	private List<Supplier<Stream<Component>>>	subComponents	= new ArrayList<>();
	private JavaScriptFunctionBuilder			functionBuilder	= new JavaScriptFunctionBuilder();

	public BasicComponent(String name)
	{
		super();
		this.name = name;
	}

	@Override
	public String getName()
	{
		return this.generateReferenceTag();
	}

	@Override
	public ComponentRenderResult render(RenderContext context)
	{
		return new ComponentRenderResult()	.setJavaScript(this.loadJavaScript(context))
											.setHtml(this.generateHtml(context))
											.setCss(this.generateCss(context));
	}

	protected abstract String generateCss(RenderContext context);

	protected abstract String generateHtml(RenderContext context);

	protected abstract String generateJavaScript(RenderContext context);

	private String loadJavaScript(RenderContext context)
	{
		return Arrays	.asList(ResourceLoader	.loadJavaScriptBinding(BasicComponent.class)
												.replaceToken("${name}", this.name)
												.replaceToken("${dependencyInjection}", this.generateDependencyInjection())
												.replaceToken("${dependencyParameters}", this.generateDependencyParameters())
												.replaceToken("${templateUrl}", this.determineTermplateUrl(context))
												.replaceToken("${functions}", this.functionBuilder.build())
												.get(),
								this.generateJavaScript(context))
						.stream()
						.collect(Collectors.joining("\n"));
	}

	private String determineTermplateUrl(RenderContext context)
	{
		String baseUrl = context.getBaseUrl();
		return (baseUrl != null ? baseUrl + "/" : "") + this.generateReferenceTag() + ".html" + "?" + System.currentTimeMillis();
	}

	private String generateDependencyParameters()
	{
		return this.services.stream()
							.map(service -> service.name())
							.collect(Collectors.joining(","));
	}

	private String generateDependencyInjection()
	{
		String joined = this.services	.stream()
										.map(service -> "\"" + service.name() + "\"")
										.collect(Collectors.joining(","));
		joined = StringUtils.isNotBlank(joined) ? joined + "," : "";
		return joined;
		//return "'$scope', '$http', '$interval'";
	}

	@Override
	public void uses(Service service)
	{
		this.services.add(service);
	}

	@Override
	public void addFunction(NamedFunction function)
	{
		this.functionBuilder.addJavaScriptFunction(function);
	}

	@Override
	public void addFunction(ServiceFunction function)
	{
		//
		this.addFunction((NamedFunction) function);

		//
		List<Service> dependencies = function.getDependencies();
		if (dependencies != null)
		{
			dependencies.forEach(this::uses);
		}
	}

	@Override
	public RawCustomHtmlElement renderReference(Map<String, String> bindings)
	{
		return new RawCustomHtmlElement(this.generateReferenceTag());
	}

	private String generateReferenceTag()
	{
		//angular does need reference tags in dash syntax and not in camelcase form
		return this.name.replaceAll("([A-Z])", "-$1")
						.toLowerCase();
	}

	@Override
	public RawHtmlElement renderReference()
	{
		Map<String, String> bindings = Collections.emptyMap();
		return this.renderReference(bindings);
	}

	public Component withTransclusion(Supplier<List<ComponentProvider<? extends Component>>> componentProviders)
	{
		this.subComponents.add(() ->
		{
			List<Component> components = componentProviders	.get()
															.stream()
															.map(provider -> provider.get())
															.collect(Collectors.toList());
			return components.stream();
		});
		return new ComponentDecoratorWithTransclusion<Component>(this, componentProviders);
	}

	@Override
	public Component withTransclusion(Component... components)
	{
		return this.withTransclusion(Arrays	.asList(components)
											.stream()
											.map(component -> (ComponentProvider<Component>) () -> component)
											.collect(Collectors.toList()));
	}

	@Override
	public Component withTransclusion(ComponentProvider<?>... componentProviders)
	{
		return this.withTransclusion(Arrays.asList(componentProviders));
	}

	@Override
	public Component withTransclusion(List<ComponentProvider<? extends Component>> componentProviders)
	{
		return this.withTransclusion(() -> componentProviders);
	}

	@Override
	public RawHtmlElement renderReference(Map<String, String> bindings, RawHtmlElement... transclusions)
	{
		return this	.renderReference(bindings)
					.addElements(Arrays.asList(transclusions));
	}

	@Override
	public List<Component> getSubComponents()
	{
		return this.subComponents	.stream()
									.flatMap(componentStreamSupplier -> componentStreamSupplier.get())
									.flatMap(component -> Stream.concat(Stream.of(component), component	.getSubComponents()
																										.stream()))
									.collect(Collectors.toList());
	}

}

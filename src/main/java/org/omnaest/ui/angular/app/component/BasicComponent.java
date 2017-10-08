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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.omnaest.ui.angular.app.internal.JavaScriptFunctionBuilder;
import org.omnaest.ui.angular.app.service.Service;
import org.omnaest.ui.angular.app.service.ServiceConsumer;
import org.omnaest.ui.angular.utils.ResourceLoader;
import org.omnaest.utils.XMLHelper;

public abstract class BasicComponent implements Component, ServiceConsumer
{
	protected String name;

	private List<Service>				services		= new ArrayList<>();
	private JavaScriptFunctionBuilder	functionBuilder	= new JavaScriptFunctionBuilder();

	public BasicComponent(String name)
	{
		super();
		this.name = name;
	}

	@Override
	public String getName()
	{
		return this.name;
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
		return (baseUrl != null ? baseUrl + "/" : "") + this.name + "?" + System.currentTimeMillis();
	}

	private String generateDependencyParameters()
	{
		return this.services.stream()
							.map(service -> service.name())
							.collect(Collectors.joining(","));
	}

	private String generateDependencyInjection()
	{
		return this.services.stream()
							.map(service -> "\"" + service.name() + "\"")
							.collect(Collectors.joining(","));
		//return "'$scope', '$http', '$interval'";
	}

	@Override
	public void uses(Service service)
	{
		this.services.add(service);
	}

	@Override
	public void addFunction(Function function)
	{
		this.functionBuilder.addJavaScriptFunction(function);
	}

	@Override
	public void addFunction(ServiceFunction function)
	{
		//
		this.addFunction((Function) function);

		//
		List<Service> dependencies = function.getDependencies();
		if (dependencies != null)
		{
			dependencies.forEach(this::uses);
		}
	}

	@Override
	public String renderReference(Map<String, String> bindings)
	{
		return XMLHelper.serializer()
						.withoutHeader()
						.serialize(new JAXBElement<String>(new QName(this.name), String.class, ""));
	}

	@Override
	public String renderReference()
	{
		Map<String, String> bindings = null;
		return this.renderReference(bindings);
	}

}

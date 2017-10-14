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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.ui.angular.app.internal.raw.RawCompositeHtmlElement;
import org.omnaest.ui.angular.app.internal.raw.RawDivElement;
import org.omnaest.ui.angular.app.internal.raw.RawHtmlElement;
import org.omnaest.ui.angular.app.internal.raw.RawTemplateHtmlElement;
import org.omnaest.ui.angular.utils.ResourceLoader;
import org.omnaest.ui.angular.utils.ResourceLoader.Resource;

public class HtmlComponent extends BasicComponent
{
	private List<CSSClass>		cssClasses		= new ArrayList<>();
	private List<HtmlElement>	htmlElements	= new ArrayList<>();

	private abstract class BasicHtmlElementImpl implements HtmlElement
	{
		protected String				cssClass;
		protected String				style;
		protected Map<String, String>	attributes	= new LinkedHashMap<>();

		@Override
		public HtmlElement setClass(String cssClass)
		{
			this.cssClass = cssClass;
			return this;
		}

		@Override
		public HtmlElement setStyle(String style)
		{
			this.style = style;
			return this;
		}

		@Override
		public HtmlElement setAttribute(String name, String value)
		{
			this.attributes.put(name, value);
			return this;
		}

		@Override
		public HtmlElement setClass(CSSClass cssClass)
		{
			this.setClass(cssClass.name());
			HtmlComponent.this.register(cssClass);
			return this;
		}

	}

	private class TemplateHtmlElementImpl extends BasicHtmlElementImpl implements TemplateHtmlElement
	{
		private Supplier<String> template;

		@Override
		public RawHtmlElement render(RenderContext context)
		{
			return new RawTemplateHtmlElement(this.template.get())	.setCssClass(this.cssClass)
																	.setStyle(this.style)
																	.setAttributes(this.attributes);
		}

		@Override
		public TemplateHtmlElement setTemplate(String template)
		{
			return this.setTemplate(() -> template);
		}

		@Override
		public TemplateHtmlElement setTemplate(Supplier<String> template)
		{
			this.template = template;
			return this;
		}

	}

	private class ComponentReferencingHtmlElementImpl extends BasicHtmlElementImpl implements ComponentReferencingHtmlElement
	{
		private Component component;

		@Override
		public RawHtmlElement render(RenderContext context)
		{
			return this.component	.renderReference()
									.setCssClass(this.cssClass)
									.setStyle(this.style)
									.setAttributes(this.attributes);
		}

		@Override
		public ComponentReferencingHtmlElement setReference(Component component)
		{
			this.component = component;
			return this;
		}

	}

	private abstract class CompositeHtmlElementImpl extends BasicHtmlElementImpl implements CompositeHtmlElement
	{

		protected List<HtmlElement> elements = new ArrayList<>();

		@Override
		public CompositeHtmlElement setClass(String cssClass)
		{
			super.setClass(cssClass);
			return this;
		}

		@Override
		public CompositeHtmlElement setStyle(String style)
		{
			super.setStyle(style);
			return this;
		}

		@Override
		public CompositeHtmlElement setAttribute(String name, String value)
		{
			super.setAttribute(name, value);
			return this;
		}

		@Override
		public RawHtmlElement render(RenderContext context)
		{
			RawCompositeHtmlElement rawElement = this.renderRawCompositeHtmlElement();
			return rawElement	.setCssClass(this.cssClass)
								.setStyle(this.style)
								.setAttributes(this.attributes)
								.addElements(this.elements	.stream()
															.map(element -> element.render(context))
															.collect(Collectors.toList()));
		}

		@Override
		public CompositeHtmlElement newDiv(Consumer<DivElement> div)
		{
			DivElementImpl element = new DivElementImpl();
			div.accept(element);
			return this.addElement(() -> element);
		}

		@Override
		public CompositeHtmlElement addElement(Supplier<HtmlElement> supplier)
		{
			HtmlElement element = supplier.get();
			this.elements.add(element);
			return this;
		}

		@Override
		public CompositeHtmlElement addComponent(Component component)
		{
			HtmlElement element = new ComponentReferencingHtmlElementImpl().setReference(component);
			this.elements.add(element);
			return this;
		}

		@Override
		public CompositeHtmlElement addComponents(Stream<Component> components)
		{
			if (components != null)
			{
				components.forEach(this::addComponent);
			}
			return this;
		}

		protected abstract RawCompositeHtmlElement renderRawCompositeHtmlElement();
	}

	private class DivElementImpl extends CompositeHtmlElementImpl implements DivElement
	{

		@Override
		protected RawDivElement renderRawCompositeHtmlElement()
		{
			return new RawDivElement();
		}

	}

	public static interface CSSClass
	{
		public String name();

		public String css();
	}

	public static interface HtmlElement
	{
		public HtmlElement setClass(CSSClass cssClass);

		public HtmlElement setClass(String cssClass);

		public HtmlElement setAttribute(String name, String value);

		public RawHtmlElement render(RenderContext context);

		public HtmlElement setStyle(String style);
	}

	public static interface ComponentReferencingHtmlElement extends HtmlElement
	{
		public ComponentReferencingHtmlElement setReference(Component component);
	}

	public static interface TemplateHtmlElement extends HtmlElement
	{
		public TemplateHtmlElement setTemplate(String template);

		public TemplateHtmlElement setTemplate(Supplier<String> template);
	}

	public static interface CompositeHtmlElement extends HtmlElement
	{
		/**
		 * Creates a new child div element
		 * 
		 * @param div
		 * @return this
		 */
		public CompositeHtmlElement newDiv(Consumer<DivElement> div);

		public CompositeHtmlElement addComponent(Component component);

		public CompositeHtmlElement addComponents(Stream<Component> components);

		public CompositeHtmlElement addElement(Supplier<HtmlElement> supplier);

	}

	public static interface DivElement extends CompositeHtmlElement
	{

	}

	public HtmlComponent(String name)
	{
		super(name);
	}

	public void register(CSSClass cssClass)
	{
		this.cssClasses.add(cssClass);
	}

	public HtmlComponent newDiv(Consumer<DivElement> div)
	{
		DivElement divElement = new DivElementImpl();
		div.accept(divElement);
		return this.addElement(() -> divElement);
	}

	public HtmlComponent addElement(Supplier<HtmlElement> supplier)
	{
		this.htmlElements.add(supplier.get());
		return this;
	}

	public HtmlComponent addElement(HtmlElement htmlElement)
	{
		return this.addElement(() -> htmlElement);
	}

	@Override
	protected String generateCss(RenderContext context)
	{
		return this.cssClasses	.stream()
								.map(cssClass -> cssClass.css())
								.collect(Collectors.joining("\n"));
	}

	@Override
	protected String generateHtml(RenderContext context)
	{
		return this.htmlElements.stream()
								.map(htmlElement -> htmlElement	.render(context)
																.asString())
								.collect(Collectors.joining("\n"));
	}

	@Override
	protected String generateJavaScript(RenderContext context)
	{
		return "";
	}

	public void bindHtmlTemplate(Component component)
	{
		Consumer<Resource> resourceModifier = null;
		this.bindHtmlTemplate(component, resourceModifier);
	}

	public void bindHtmlTemplate(Component component, Consumer<Resource> resourceModifier)
	{
		Resource resource = ResourceLoader.loadJavaHTMLBinding(component);

		this.addElement(new TemplateHtmlElementImpl().setTemplate(() ->
		{
			if (resourceModifier != null)
			{
				resourceModifier.accept(resource);
			}

			return resource.get();
		}));

	}

}

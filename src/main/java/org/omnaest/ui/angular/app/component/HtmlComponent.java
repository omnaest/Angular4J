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
import java.util.stream.Collectors;

import org.omnaest.ui.angular.app.internal.raw.RawCompositeHtmlElement;
import org.omnaest.ui.angular.app.internal.raw.RawDivElement;
import org.omnaest.ui.angular.app.internal.raw.RawHtmlElement;
import org.omnaest.utils.XMLHelper;

public class HtmlComponent extends BasicComponent
{
	private List<CSSClass>		cssClasses		= new ArrayList<>();
	private List<HtmlElement>	htmlElements	= new ArrayList<>();
	private Component			subComponent;

	private abstract class CompositeHtmlElementImpl implements CompositeHtmlElement
	{
		protected String				cssClass;
		protected String				style;
		protected Map<String, String>	attributes	= new LinkedHashMap<>();
		protected List<HtmlElement>		elements	= new ArrayList<>();

		@Override
		public HtmlElement setClass(CSSClass cssClass)
		{
			this.setClass(cssClass.name());
			HtmlComponent.this.register(cssClass);
			return this;
		}

		@Override
		public HtmlElement setClass(String cssClass)
		{
			this.cssClass = cssClass;
			return this;
		}

		@Override
		public CompositeHtmlElement setStyle(String style)
		{
			this.style = style;
			return this;
		}

		@Override
		public CompositeHtmlElement setAttribute(String name, String value)
		{
			this.attributes.put(name, value);
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
															.collect(Collectors.toList()))
								.setContent(() -> HtmlComponent.this.renderSubComponent(context));
		}

		@Override
		public CompositeHtmlElement newDiv(Consumer<DivElement> div)
		{
			DivElementImpl element = new DivElementImpl();
			div.accept(element);
			this.elements.add(element);
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

	public static interface CompositeHtmlElement extends HtmlElement
	{
		/**
		 * Creates a new child div element
		 * 
		 * @param div
		 * @return this
		 */
		public CompositeHtmlElement newDiv(Consumer<DivElement> div);

	}

	public static interface DivElement extends CompositeHtmlElement
	{

	}

	public HtmlComponent(String name)
	{
		super(name);
	}

	private String renderSubComponent(RenderContext context)
	{
		String reference = this.subComponent.renderReference();

		return reference;
	}

	public void register(CSSClass cssClass)
	{
		this.cssClasses.add(cssClass);
	}

	public void newDiv(Consumer<DivElement> div)
	{
		DivElement divElement = new DivElementImpl();
		div.accept(divElement);
		this.htmlElements.add(divElement);
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
								.map(htmlElement -> XMLHelper	.serializer()
																.withoutHeader()
																.serialize(htmlElement.render(context)))
								.collect(Collectors.joining("\n"));
	}

	@Override
	protected String generateJavaScript(RenderContext context)
	{
		return "";
	}

}

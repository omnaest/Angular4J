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
package org.omnaest.ui.angular.app.internal.raw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlSeeAlso(RawCustomHtmlElement.class)
@XmlAccessorType(XmlAccessType.NONE)
public class RawCompositeHtmlElement extends RawHtmlElement
{
	@XmlElementRef
	private List<RawHtmlElement> elements = new ArrayList<>();

	@XmlAnyElement
	@XmlJavaTypeAdapter(value = RawCustomHtmlElementAdapter.class)
	private List<RawCustomHtmlElement> customElements = new ArrayList<>();

	@XmlAnyElement
	@XmlJavaTypeAdapter(value = RawCustomHtmlElementAdapter.class)
	private List<RawTemplateHtmlElement> templateElements = new ArrayList<>();

	public RawCompositeHtmlElement addElements(Collection<RawHtmlElement> elements)
	{
		if (elements != null)
		{
			elements.forEach(this::addElement);
		}
		return this;
	}

	public RawCompositeHtmlElement addElement(RawHtmlElement element)
	{
		if (element instanceof RawCustomHtmlElement)
		{
			this.customElements.add((RawCustomHtmlElement) element);
		}
		else if (element instanceof RawTemplateHtmlElement)
		{
			this.templateElements.add((RawTemplateHtmlElement) element);
		}
		else
		{
			this.elements.add(element);
		}
		return this;
	}

	@Override
	public RawCompositeHtmlElement setCssClass(String cssClass)
	{
		super.setCssClass(cssClass);
		return this;
	}

	@Override
	public RawCompositeHtmlElement setAttributes(Map<String, String> attributes)
	{
		super.setAttributes(attributes);
		return this;
	}

	@Override
	public RawCompositeHtmlElement setAttibute(String name, String value)
	{
		super.setAttibute(name, value);
		return this;
	}

	@Override
	public RawCompositeHtmlElement setStyle(String style)
	{
		super.setStyle(style);
		return this;
	}

	@Override
	public RawCompositeHtmlElement setContent(Supplier<String> content)
	{
		super.setContent(content);
		return this;
	}

}

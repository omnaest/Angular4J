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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.NONE)
public class RawHtmlElement
{
	@XmlAttribute
	private String id;

	@XmlAttribute(name = "class")
	private String cssClass;

	@XmlAttribute(name = "style")
	private String style;

	@XmlAnyAttribute
	private Map<QName, String> attributes = new LinkedHashMap<>();

	private Supplier<String> content = () -> null;

	public RawHtmlElement setAttributes(Map<String, String> attributes)
	{
		if (attributes != null)
		{
			attributes	.entrySet()
						.forEach(entry -> this.setAttibute(entry.getKey(), entry.getValue()));
		}
		return this;
	}

	public RawHtmlElement setAttibute(String name, String value)
	{
		if (name != null)
		{
			this.attributes.put(new QName(name), value);
		}
		return this;
	}

	public String getCssClass()
	{
		return this.cssClass;
	}

	public RawHtmlElement setCssClass(String cssClass)
	{
		this.cssClass = cssClass;
		return this;
	}

	@XmlValue
	public String getContent()
	{
		return this.content.get();
	}

	public RawHtmlElement setContent(String content)
	{
		return this.setContent(() -> content);
	}

	public RawHtmlElement setContent(Supplier<String> content)
	{
		this.content = content;
		return this;
	}

	public String getId()
	{
		return this.id;
	}

	public RawHtmlElement setId(String id)
	{
		this.id = id;
		return this;
	}

	public RawHtmlElement setStyle(String style)
	{
		this.style = style;
		return this;
	}

}

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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.omnaest.utils.XMLHelper;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlJavaTypeAdapter(value = RawTemplateHtmlElementAdapter.class)
public class RawTemplateHtmlElement extends RawHtmlElement
{
	@XmlTransient
	protected JAXBElement<?> element;

	public RawTemplateHtmlElement()
	{
		super();
	}

	public RawTemplateHtmlElement(String content)
	{
		super();

		try
		{
			this.setElement(XMLHelper.parse(content, JAXBElement.class));
		} catch (Exception e)
		{
			throw new IllegalStateException(content, e);
		}
	}

	public RawTemplateHtmlElement setElement(JAXBElement<?> element)
	{
		this.element = element;
		return this;
	}

	public JAXBElement<?> getElement()
	{
		return this.element;
	}

}

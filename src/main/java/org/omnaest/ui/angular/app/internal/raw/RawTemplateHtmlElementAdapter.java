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
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class RawTemplateHtmlElementAdapter extends XmlAdapter<JAXBElement<?>, RawTemplateHtmlElement>
{
	@Override
	public JAXBElement<?> marshal(RawTemplateHtmlElement rawTemplateElement) throws Exception
	{
		JAXBElement<?> jaxbElement = rawTemplateElement.getElement();
		return jaxbElement;
	}

	@Override
	public RawTemplateHtmlElement unmarshal(JAXBElement<?> jaxbElement) throws Exception
	{
		RawTemplateHtmlElement rawTemplateElement = new RawTemplateHtmlElement().setElement(jaxbElement);
		return rawTemplateElement;
	}
}
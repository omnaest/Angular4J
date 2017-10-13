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

public class RawCustomHtmlElementAdapter extends XmlAdapter<JAXBElement<RawCustomHtmlElement>, RawCustomHtmlElement>
{
	@Override
	public JAXBElement<RawCustomHtmlElement> marshal(RawCustomHtmlElement rawCustomElement) throws Exception
	{
		JAXBElement<RawCustomHtmlElement> jaxbElement = new JAXBElement<>(rawCustomElement.getTag(), RawCustomHtmlElement.class, rawCustomElement);
		return jaxbElement;
	}

	@Override
	public RawCustomHtmlElement unmarshal(JAXBElement<RawCustomHtmlElement> jaxbElement) throws Exception
	{
		RawCustomHtmlElement rawCustomElement = jaxbElement.getValue();
		rawCustomElement.setTag(jaxbElement.getName());
		return rawCustomElement;
	}
}
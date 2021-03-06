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
package org.omnaest.ui.angular.utils;

import org.omnaest.ui.angular.app.internal.raw.RawCustomHtmlElement;
import org.omnaest.ui.angular.app.internal.raw.RawCustomHtmlElementAdapter;
import org.omnaest.ui.angular.app.internal.raw.RawHtmlElement;
import org.omnaest.ui.angular.app.internal.raw.RawTemplateHtmlElement;
import org.omnaest.ui.angular.app.internal.raw.RawTemplateHtmlElementAdapter;
import org.omnaest.utils.XMLHelper;

/**
 * Renders {@link RawHtmlElement}s
 * 
 * @author omnaest
 */
public class RawHtmlElementRenderer
{
	public static String render(RawHtmlElement htmlElement)
	{
		try
		{
			Object model;
			if (htmlElement instanceof RawCustomHtmlElement)
			{
				model = new RawCustomHtmlElementAdapter().marshal((RawCustomHtmlElement) htmlElement);
			}
			else if (htmlElement instanceof RawTemplateHtmlElement)
			{
				model = new RawTemplateHtmlElementAdapter().marshal((RawTemplateHtmlElement) htmlElement);
			}
			else
			{
				model = htmlElement;
			}
			return XMLHelper.serializer()
							.withRootTypes(htmlElement.getClass())
							.withoutHeader()
							.serialize(model);
		} catch (Exception e)
		{
			throw new IllegalStateException(e);
		}
	}
}

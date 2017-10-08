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
package org.omnaest.ui.angular.app.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.omnaest.ui.angular.app.component.ComponentRenderResult;
import org.omnaest.ui.angular.app.internal.raw.RawCSS;
import org.omnaest.utils.XMLHelper;

public class CSSBuilder
{
	private List<String> cssList = new ArrayList<>();

	public void addCss(String css)
	{
		if (StringUtils.isNotBlank(css))
		{
			this.cssList.add(css);
		}
	}

	public String render()
	{
		return XMLHelper.serializer()
						.withoutHeader()
						.serialize(new RawCSS().setContent(this.cssList	.stream()
																		.collect(Collectors.joining("\n"))));
	}

	public void addCssFrom(ComponentRenderResult result)
	{
		this.addCss(result.getCss());
	}
}

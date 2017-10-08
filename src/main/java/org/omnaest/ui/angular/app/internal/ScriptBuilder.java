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
import org.omnaest.ui.angular.app.internal.raw.RawScript;
import org.omnaest.utils.XMLHelper;

/**
 * @author omnaest
 */
public class ScriptBuilder
{
	private List<String> scripts = new ArrayList<>();

	public ScriptBuilder addJavaScriptFrom(ComponentRenderResult componentRenderResult)
	{
		return this.addJavaScript(componentRenderResult.getJavaScript());
	}

	public ScriptBuilder addJavaScript(String javaScript)
	{
		if (StringUtils.isNotBlank(javaScript))
		{
			this.scripts.add(javaScript);
		}
		return this;
	}

	public String render()
	{
		return XMLHelper.serializer()
						.withoutHeader()
						.serialize(new RawScript().setContent(this.scripts	.stream()
																			.collect(Collectors.joining("\n"))));
	}
}

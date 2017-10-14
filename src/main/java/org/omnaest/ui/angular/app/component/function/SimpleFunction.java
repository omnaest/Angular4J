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
package org.omnaest.ui.angular.app.component.function;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.omnaest.ui.angular.app.component.Component.Function;

public class SimpleFunction implements Function
{
	private List<String> lines = new ArrayList<>();

	@Override
	public String getJavaScriptFunction()
	{
		StringBuilder wrapper = new StringBuilder();

		wrapper.append("function()\n");
		wrapper.append("{\n");
		wrapper.append(this.lines	.stream()
									.map(line -> "  " + line)
									.collect(Collectors.joining("\n")));
		wrapper.append("\n");
		wrapper.append("}\n");

		return wrapper.toString();
	}

	public SimpleFunction append(String line)
	{
		this.lines.add(line);
		return this;
	}

}

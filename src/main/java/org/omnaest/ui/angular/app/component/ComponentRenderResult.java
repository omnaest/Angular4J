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

public class ComponentRenderResult
{
	private String	javaScript;
	private String	html;
	private String	css;

	public String getJavaScript()
	{
		return this.javaScript;
	}

	public ComponentRenderResult setJavaScript(String javaScript)
	{
		this.javaScript = javaScript;
		return this;
	}

	public String getHtml()
	{
		return this.html;
	}

	public ComponentRenderResult setHtml(String html)
	{
		this.html = html;
		return this;
	}

	public String getCss()
	{
		return this.css;
	}

	public ComponentRenderResult setCss(String css)
	{
		this.css = css;
		return this;
	}

}

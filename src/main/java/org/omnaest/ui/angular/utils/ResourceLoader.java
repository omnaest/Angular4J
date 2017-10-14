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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author omnaest
 */
public class ResourceLoader
{

	public static interface Resource
	{
		public Resource replace(String regex, String replacement);

		public Resource replaceToken(String token, String replacement);

		public String get();

	}

	public static Resource loadJavaScriptBinding(Object instance)
	{
		return loadJavaScriptBinding(instance.getClass());
	}

	public static Resource loadJavaScriptBinding(Class<?> type)
	{
		return load(type, StringUtils.capitalize(type.getSimpleName()) + ".js");
	}

	public static Resource loadJavaCSSBinding(Object instance)
	{
		return load(instance, StringUtils.capitalize(instance	.getClass()
																.getSimpleName())
				+ ".css");
	}

	public static Resource loadJavaHTMLBinding(Object instance)
	{
		return load(instance, StringUtils.capitalize(instance	.getClass()
																.getSimpleName())
				+ ".html");
	}

	public static Resource load(Object instance, String resource)
	{
		return load(instance.getClass(), resource);
	}

	public static Resource load(Class<?> type, String resource)
	{
		try
		{
			return new Resource()
			{
				private String content = IOUtils.toString(type.getResourceAsStream(resource), StandardCharsets.UTF_8);

				@Override
				public Resource replace(String regex, String replacement)
				{
					this.content = this.content.replaceAll(regex, replacement);
					return this;
				}

				@Override
				public String get()
				{
					return this.content;
				}

				@Override
				public Resource replaceToken(String token, String replacement)
				{
					return this.replace(Pattern.quote(token), Matcher.quoteReplacement(replacement));
				}

			};
		} catch (IOException e)
		{
			throw new IllegalStateException("Failed to load resource: " + resource + " " + type.getName(), e);
		}
	}
}

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
package org.omnaest.ui.angular;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.omnaest.ui.angular.app.AngularApplication.RenderResult;
import org.omnaest.ui.angular.app.component.function.SimpleFunction;
import org.omnaest.ui.angular.app.component.theme.bootstrap.button.ButtonComponent;
import org.omnaest.ui.angular.app.component.theme.bootstrap.panel.PanelComponent;
import org.omnaest.utils.JSONHelper;

public class AngularUtilsTest
{

	@Test
	public void testNewInstance() throws Exception
	{
		PanelComponent panel1 = new PanelComponent("panel1");
		ButtonComponent button1 = new ButtonComponent("button1");

		button1.onClick(new SimpleFunction().append("alert('totoro');"));

		RenderResult renderResult = AngularUtils.newInstance()
												.withBaseUrl("") //"C:\\Temp\\angular"
												.withTitle("Unit test")
												.addComponent(panel1.withTransclusion(button1))
												.renderHtml();
		String indexHtml = renderResult.getIndexHtml();
		System.out.println(indexHtml);

		Map<String, String> componentsHtml = renderResult.getComponentsHtml();
		System.out.println(JSONHelper.prettyPrint(componentsHtml));

		File directory = new File("C:/Temp/angular");
		FileUtils.writeStringToFile(new File(directory, "index.html"), indexHtml, StandardCharsets.UTF_8);
		componentsHtml	.entrySet()
						.forEach(entry ->
						{
							String fileName = entry.getKey() + ".html";
							String content = entry.getValue();

							try
							{
								FileUtils.writeStringToFile(new File(directory, fileName), content, StandardCharsets.UTF_8);
							} catch (IOException e)
							{
								throw new IllegalStateException(e);
							}
						});
	}

}

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

import org.omnaest.ui.angular.app.AngularApplication;
import org.omnaest.ui.angular.app.AngularApplicationImpl;
import org.omnaest.ui.angular.app.component.theme.Theme;
import org.omnaest.ui.angular.app.component.theme.ThemeProvider;

/**
 * @see AngularApplication
 * @author omnaest
 */
public class AngularUtils
{
	public static AngularApplication newInstance()
	{
		return new AngularApplicationImpl();
	}

	public static Theme newTheme(ThemeProvider themes)
	{
		return themes.get();
	}
}

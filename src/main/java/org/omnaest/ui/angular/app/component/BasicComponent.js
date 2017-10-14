
angular.module("app").component(
		"${name}",
		{
			templateUrl : "${templateUrl}",
			controller : [ ${dependencyInjection}
					function( ${dependencyParameters} ) {
						var self = this;
						
						${functions}
					} ],
			bindings : {},
			transclude : true
		});
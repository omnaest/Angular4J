angular.module('app').component(
		'tabs',
		{
			templateUrl : '/js/component/tabs/tabs.html',
			controller : [ '$scope', '$http', '$interval',
					function($scope, $http, $interval) {
						var self = this;

						self.tabs = [];
						self.register = function(tab) {
							self.tabs.push(tab);

							if (self.tabs.length == 1) {
								tab.active = true;
							}
						};

						self.clearActive = function() {
							self.tabs.forEach(function(tab) {
								tab.active = false;
							});
						};

					} ],
			bindings : {},
			transclude : true
		});
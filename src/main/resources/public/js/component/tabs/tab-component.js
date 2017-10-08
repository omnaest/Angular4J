angular.module('app').component(
		'tab',
		{
			templateUrl : '/js/component/tabs/tab.html',
			controller : [
					'$scope',
					'$http',
					'$interval',
					function($scope, $http, $interval) {
						var self = this;
						self.active = false;

						self.$onInit = function() {
							self.tabs.register(self);
						};

						self.activate = function() {
							self.tabs.clearActive();
							self.active = true;
						}

						$scope.$watch(function() {
							return self.active;
						}, function(newVal, oldVal) {
							if (newVal) {
								$scope
										.$broadcast("tabActivated",
												[ self.name ]);
							} else {
								$scope.$broadcast("tabDeactivated",
										[ self.name ]);
							}
						});

					} ],
			require : {
				tabs : '^^tabs'
			},
			bindings : {
				name : "@"
			},
			transclude : true
		});
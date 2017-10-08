angular.module('app').component(
		'metabolismview',
		{
			templateUrl : '/js/component/metabolismview/metabolismview.html',
			controller : [
					'$scope',
					'$http',
					'$interval',
					function($scope, $http, $interval) {
						var self = this;

						$interval(function() {
							self.metabolismurl = 'metabolism.svg?'
									+ new Date().getTime();
						}, 1000);

					} ],
			bindings : {},
			transclude : false
		});
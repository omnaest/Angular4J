angular.module('app').component(
		'detailpanel',
		{
			templateUrl : '/js/component/detailpanel/detailpanel.html',
			controller : [ '$scope', '$http', '$interval',
					function($scope, $http, $interval) {
						var self = this;
					} ],
			bindings : {},
			transclude : false
		});
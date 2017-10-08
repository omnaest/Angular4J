angular.module('app').component(
		'view',
		{
			templateUrl : '/js/component/detailpanel/view.html',
			controller : [ '$scope', '$http', '$interval',
					function($scope, $http, $interval) {
						var self = this;

						self.zoom = 1.2;
						self.rotationX = 0;
						self.rotationY = 0;
						self.rotationZ = 0;

						self.angleX = 0;
						self.angleY = 0;
						self.angleZ = 0;

						$interval(function() {
							$http({
								method : 'PUT',
								url : '/view',
								data : {
									resolutionFactor : self.zoom,
									rotationX : 0,
									rotationY : 0,
									rotationZ : 0,
									angleX : self.angleX,
									angleY : self.angleY,
									angleZ : self.angleZ
								}
							}).then(function(response) {
							});
						}, 1000);
						
						$interval(function() {
							self.angleX = (self.angleX + self.rotationX) % 360;
							self.angleY = (self.angleY + self.rotationY) % 360;
							self.angleZ = (self.angleZ + self.rotationZ) % 360;
							
							
						}, 1000);

					} ],
			bindings : {},
			transclude : false
		});
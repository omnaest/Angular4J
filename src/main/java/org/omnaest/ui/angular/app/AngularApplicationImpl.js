
angular
		.module(
				'app',
				[ 'mobile-angular-ui', 'mobile-angular-ui.gestures',
						'720kb.datepicker' ]).controller(
				'appController',
				[
						'$scope',
						'$rootScope',
						'$http',
						'$timeout',
						'SharedState',
						function(	$scope, 
									$rootScope, 
									$http, 
									$timeout
								) 
						{
							var self = this;

						} ]);

angular
		.module('app')
		.component(
				'metabolites',
				{
					templateUrl : '/js/component/detailpanel/metabolites.html',
					controller : [
							'$scope',
							'$http',
							'$interval',
							function($scope, $http, $interval) {
								var self = this;

								self.metabolites = [];

								self.load = function() {

									$http({
										method : 'GET',
										url : '/metabolites'
									})
											.then(
													function(response) {

														self.metabolites = [];

														for ( var key in response.data) {
															self.metabolites
																	.push({
																		id : key,
																		label : key
																				.substring(
																						0,
																						50),
																		value : response.data[key],
																		initialValue : response.data[key]
																	});
														}
													});

								};

								self.load();

								$scope.$on("tabActivated",
										function(event, data) {
											self.load();
										});

								self.update = function() {

									self.metabolites
											.forEach(function(metabolite) {
												if (metabolite.value != metabolite.initialValue) {
													$http({
														method : 'POST',
														url : '/metabolites',
														data : {
															metabolite : metabolite.id,
															linkMode : metabolite.value
														}
													});
													metabolite.initialValue = metabolite.value;
												}
											});
								};

							} ],
					bindings : {},
					transclude : false
				});
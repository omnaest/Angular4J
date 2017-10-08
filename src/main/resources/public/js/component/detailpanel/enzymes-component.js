angular.module('app').component(
		'enzymes',
		{
			templateUrl : '/js/component/detailpanel/enzymes.html',
			controller : [ '$scope', '$http', '$interval',
					function($scope, $http, $interval) {
						var self = this;

						self.activeEnzymes = [];
						self.availableEnzymes = [];

						self.activateEnzyme = function(enzymeId) {
							$http({
								method : 'PUT',
								url : '/enzymes/active/' + enzymeId,
								data : {}
							}).then(function(response) {
								self.renderLists();
							});
						};
						self.deactivateEnzyme = function(enzymeId) {
							$http({
								method : 'DELETE',
								url : '/enzymes/active/' + enzymeId
							}).then(function(response) {
								self.renderLists();
							});
						};

						self.renderLists = function() {
							$http({
								method : 'GET',
								url : '/enzymes/inactive',
								params : {
									metabolite : self.metaboliteFilter
								}
							}).then(function(response) {
								self.availableEnzymes = [];
								response.data.forEach(function(enzymeId) {
									self.availableEnzymes.push({
										name : enzymeId,
										value : false,
										activate : function() {
											self.activateEnzyme(enzymeId);
										}
									});
								});
							});

							$http({
								method : 'GET',
								url : '/enzymes/active'
							}).then(function(response) {
								self.activeEnzymes = [];
								response.data.forEach(function(enzymeId) {
									self.activeEnzymes.push({
										name : enzymeId,
										value : true,
										deactivate : function() {
											self.deactivateEnzyme(enzymeId);
										}
									});
								});
							});
						};
						self.renderLists();

						//
						self.load = function() {
							$http({
								method : 'GET',
								url : '/enzymes/presets'
							}).then(function(response) {
								self.renderLists();
							});
						};

						self.save = function() {
							$http({
								method : 'PUT',
								url : '/enzymes/presets',
								data : {}
							}).then(function(response) {
								self.renderLists();
							});
						};

						self.clear = function() {
							$http({
								method : 'DELETE',
								url : '/enzymes/presets'
							}).then(function(response) {
								self.renderLists();
							});
						};

					} ],
			bindings : {},
			transclude : false
		});
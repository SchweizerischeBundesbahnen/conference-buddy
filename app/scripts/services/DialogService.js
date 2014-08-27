'use strict';

angular.module('conferenceBuddyApp')
	.service('DialogService', ['$modal', function ($modal) {

		this.showError = function (title, message, details) {
			return this.showModal({}, {headerText: title, bodyText: message, details: details});
		};

		this.showModal = function (config, props) {
			if (!config) {
				config = {};
			}
			config.backdrop = 'static';
			return this.show(config, props);
		};

		this.show = function (config, props) {
			var tempConfig = {};
			var tempProps = {};
			//Map angular-ui modal custom defaults to modal defaults defined in service
			angular.extend(tempConfig, defaultConfig, config);
			//Map modal.html $scope custom properties to defaults defined in service
			angular.extend(tempProps, defaultProps, props);

			if (!tempConfig.controller) {
				tempConfig.controller = function ($scope, $modalInstance) {
					$scope.options = tempProps;
					$scope.ok = function (result) {
						$modalInstance.close(result);
					};
					$scope.close = function () {
						$modalInstance.dismiss('cancel');
					};
				};
			}
			return $modal.open(tempConfig).result;
		};


		var defaultConfig = {
			backdrop: true,
			keyboard: true,
			modalFade: true,
			templateUrl: '/partials/dialog.html'
		};

		var defaultProps = {
			closeButtonText: 'Schliessen',
			actionButtonText: 'OK',
			headerText: 'Info',
			bodyText: 'Info Default',
			details: 'Details Default'
		};
	}]);

'use strict';

angular.module('conferenceBuddyApp')
	.factory('UserService', [function() {

		return  {
			currentUser: function() {
				return {
					'name': 'Stefan Zeller',
					'id': 'u215942',
					'email': 'stefan.zeller@sbb.ch'
				};
			}
		};

	}]);
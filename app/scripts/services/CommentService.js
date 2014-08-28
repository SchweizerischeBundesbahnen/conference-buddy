'use strict';

angular.module('conferenceBuddyApp')
	.factory('CommentService', ['$http', function ($http) {

		return  {
			load: function () {
				return $http.get('api/comments.json')
					.then(function (result) {
						return result.data;
					});
			}
		};

	}]);
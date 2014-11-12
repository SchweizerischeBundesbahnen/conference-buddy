'use strict';

angular.module('conferenceBuddyApp').factory('RatingService', ['$http', 'UserService', 'REST_URL', function($http, userService, REST_URL) {

    userService.initFromCookie();

    return {
        load: function(presentationId) {
            return $http.get(REST_URL + '/rating/' + presentationId).then(function(result) {
                return result.data;
            });
        },
        save: function(presentationId, rating) {
            return $http.put(REST_URL + '/rating/' + presentationId, rating).then(function(result) {
                return result.data;
            });
        }
    };
}]);

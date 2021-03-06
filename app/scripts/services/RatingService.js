'use strict';

angular.module('conferenceBuddyApp').factory('RatingService', ['$http', 'REST_URL', function($http, REST_URL) {

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

'use strict';

angular.module('conferenceBuddyApp').factory('HttpInterceptor', ['$q', '$location', 'ROUTES', function($q, $location, ROUTES) {

    return function(promise) {

        var success = function(response) {
            return response;
        };

        var error = function(response) {
            if (response.status === 401) {
                $location.url(ROUTES.REGISTER);
            }
            return $q.reject(response);
        };

        return promise.then(success, error);
    };
}]);
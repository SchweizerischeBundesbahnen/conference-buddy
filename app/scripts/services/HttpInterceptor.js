'use strict';

angular.module('conferenceBuddyApp').factory('HttpInterceptor', ['$q', '$location', '$cookieStore', 'ROUTES', 'AUTH', function($q, $location, $cookieStore, ROUTES, AUTH) {

    return function(promise) {

        var success = function(response) {
            return response;
        };

        var error = function(response) {
            if (response.status === 401) {
                $cookieStore.remove(AUTH.COOKIES_USERTOKEN);
                $cookieStore.remove(AUTH.COOKIES_USER);
                $location.url(ROUTES.REGISTER);
            }
            return $q.reject(response);
        };

        return promise.then(success, error);
    };
}]);
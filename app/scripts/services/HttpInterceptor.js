'use strict';

angular.module('conferenceBuddyApp').factory('HttpInterceptor', ['$q', '$location', 'StorageService', 'ROUTES', 'AUTH',
    function($q, $location, storageService, ROUTES, AUTH) {

    return function(promise) {

        var success = function(response) {
            return response;
        };

        var error = function(response) {
            if (response.status === 401) {
                storageService.clear();
                $location.url(ROUTES.REGISTER);
            }
            return $q.reject(response);
        };

        return promise.then(success, error);
    };
}]);
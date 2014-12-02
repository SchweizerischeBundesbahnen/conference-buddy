'use strict';

angular.module('conferenceBuddyApp').factory('HttpInterceptor',
['$q', '$location', 'StorageService', 'ROUTES', function($q, $location, storageService, ROUTES) {

    var HTTP_HEADER_TOKEN = 'X-Access-Token';

    return {
        request: function(config) {
            config.headers[HTTP_HEADER_TOKEN] = storageService.getToken();

            return config;
        },
        responseError: function(response) {
            if (response.status === 401) {
                storageService.clear();
                $location.url(ROUTES.REGISTER);
            }
            return $q.reject(response);
        }
    };
}]);
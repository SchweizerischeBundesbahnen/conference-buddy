'use strict';

angular.module('conferenceBuddyApp').factory('UserService',
    ['$http', 'StorageService', 'REST_URL', function($http, storageService, REST_URL) {

    var HTTP_HEADER_TOKEN = 'X-Access-Token';

    return {
        initFromCookie: function() {
            $http.defaults.headers.common[HTTP_HEADER_TOKEN] = storageService.getToken();
        },
        isRegistered: function() {
            return typeof storageService.getToken() === 'string';
        },
        register: function(user) {
            return $http.post(REST_URL + '/user', user).then(function(result) {
            });
        },
        validate: function(userToken) {
            $http.defaults.headers.common[HTTP_HEADER_TOKEN] = userToken;
            return $http.get(REST_URL + '/user').then(function(result) {
                storageService.setUserAndToken(result.data, userToken);
            });
        },
        currentUser: function() {
            return storageService.getUser();
        }
    };

}]);
'use strict';

angular.module('conferenceBuddyApp').factory('UserService',
    ['$http', 'StorageService', 'REST_URL', function($http, storageService, REST_URL) {

    return {
        isRegistered: function() {
            return typeof storageService.getToken() === 'string';
        },
        register: function(user) {
            return $http.post(REST_URL + '/user', user).then(function(result) {
            });
        },
        unregister: function() {
            storageService.clear();
        },
        validate: function(userToken) {
            storageService.setToken(userToken);
            return $http.get(REST_URL + '/user').then(function(result) {
                storageService.setUser(result.data);
            });
        },
        currentUser: function() {
            return storageService.getUser();
        }
    };

}]);
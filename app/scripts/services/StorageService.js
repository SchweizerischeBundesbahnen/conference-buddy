'use strict';

angular.module('conferenceBuddyApp').factory('StorageService', ['localStorageService', function(localStorageService) {

    var KEY_TOKEN = 'token';
    var KEY_USER = 'user';

    return {
        setToken: function(token) {
            localStorageService.set(KEY_TOKEN, token);
        },
        setUser: function(user) {
            localStorageService.set(KEY_USER, user);
        },
        clear: function() {
            localStorageService.clearAll();
        },
        getUser: function() {
            return localStorageService.get(KEY_USER);
        },
        getToken: function() {
            return localStorageService.get(KEY_TOKEN);
        }
    };

}]);
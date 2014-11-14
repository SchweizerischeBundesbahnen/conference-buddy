'use strict';

angular.module('conferenceBuddyApp').factory('UserService',
['$http', '$cookieStore', 'REST_URL', 'AUTH', function($http, $cookieStore, REST_URL, AUTH) {

    return {
        initFromCookie: function() {
            $http.defaults.headers.common[AUTH.HTTP_HEADER_TOKEN] = $cookieStore.get(AUTH.COOKIES_USERTOKEN);
        },
        isRegistered: function() {
            return typeof $cookieStore.get(AUTH.COOKIES_USERTOKEN) === 'string';
        },
        register: function(user) {
            return $http.post(REST_URL + '/user', user).then(function(result) {
                $http.defaults.headers.common[AUTH.HTTP_HEADER_TOKEN] = result.data;
                $cookieStore.put(AUTH.COOKIES_USERTOKEN, result.data);
                $cookieStore.put(AUTH.COOKIES_USER, user);
            });
        },
        validate: function(userToken) {
            return $http.get(REST_URL + '/user').then(function(result) {
                $http.defaults.headers.common[AUTH.HTTP_HEADER_TOKEN] = result.data;
                $cookieStore.put(AUTH.COOKIES_USERTOKEN, result.data);
                $cookieStore.put(AUTH.COOKIES_USER, user);
            });
        },
        currentUser: function() {
            return $cookieStore.get(AUTH.COOKIES_USER);
        }
    };

}]);
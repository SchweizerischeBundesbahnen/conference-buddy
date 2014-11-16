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
            });
        },
        validate: function(userToken) {
            $http.defaults.headers.common[AUTH.HTTP_HEADER_TOKEN] = userToken;
            return $http.get(REST_URL + '/user').then(function(result) {
                $cookieStore.put(AUTH.COOKIES_USERTOKEN, userToken);
                $cookieStore.put(AUTH.COOKIES_USER, result.data);
            });
        },
        currentUser: function() {
            return $cookieStore.get(AUTH.COOKIES_USER);
        }
    };

}]);
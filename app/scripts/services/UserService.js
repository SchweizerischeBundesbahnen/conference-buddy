'use strict';

angular.module('conferenceBuddyApp').factory('UserService', ['$http', '$cookieStore', 'REST_URL', function($http, $cookieStore, REST_URL) {

    var HTTP_HEADER_TOKEN = 'X-Access-Token';
    var COOKIES_USERTOKEN = 'userToken';
    var COOKIES_USER = 'user';

    $http.defaults.headers.common[HTTP_HEADER_TOKEN] = $cookieStore.get(COOKIES_USERTOKEN);

    return {
        isRegistered: function() {
            return typeof $cookieStore.get(COOKIES_USERTOKEN) === 'string';
        },
        register: function(user) {
            return $http.post(REST_URL + '/user', user).then(function(result) {
                // TODO wait for registration e-
                console.log(result.data);
                $http.defaults.headers.common[HTTP_HEADER_TOKEN] = result.data;
                $cookieStore.put(COOKIES_USERTOKEN, result.data);
                $cookieStore.put(COOKIES_USER, user);
            });
        },
        validate: function(userToken) {
            $http.get(REST_URL + '/user').then(function(result) {
                // userToken submitted as HTTP_HEADER_TOKEN
                $http.defaults.headers.common[HTTP_HEADER_TOKEN] = result.data;
                $cookieStore.put(COOKIES_USERTOKEN, result.data);
                $cookieStore.put(COOKIES_USER, user);
            });
        },
        currentUser: function() {
            return $cookieStore.get(COOKIES_USER);
        }
    };

}]);
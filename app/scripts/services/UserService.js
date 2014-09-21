'use strict';

angular.module('conferenceBuddyApp').factory('UserService', ['$http', '$cookieStore', function($http, $cookieStore) {

    var HTTP_HEADER_TOKEN = 'X-Access-Token';
    var COOKIES_USERTOKEN = 'userToken';
    var COOKIES_USER = 'user';

    $http.defaults.headers.common[HTTP_HEADER_TOKEN] = $cookieStore.get(COOKIES_USERTOKEN);

    return {
        isRegistered: function() {
            return typeof $cookieStore.get(COOKIES_USERTOKEN) === 'string';
        },
        register: function(user) {
            return $http.get('api/user.json').then(function(result) {
                $http.defaults.headers.common[HTTP_HEADER_TOKEN] = result.data.userToken;
                $cookieStore.put(COOKIES_USERTOKEN, result.data.userToken);
                $cookieStore.put(COOKIES_USER, user);
            });
        },
        currentUser: function() {
            return $cookieStore.get(COOKIES_USER);
        }
    };

}]);
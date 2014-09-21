'use strict';

angular.module('conferenceBuddyApp').factory('RegistrationService', ['$http', '$cookies', '$cookieStore', function($http, $cookies, $cookieStore) {

    var TOKEN_DEF = 'X-Access-Token';

    // initialize once from cookies if existing
    $http.defaults.headers.common[TOKEN_DEF] = $cookies.token;

    return {
        isRegistered : function() {
            return typeof $http.defaults.headers.common[TOKEN_DEF] === 'string';
        },
        register: function(name, email, userid) {
            return $http.get('api/user.json').then(function(result) {
                $http.defaults.headers.common[TOKEN_DEF] = result.data.userToken;
                console.log('>' + result.data.userToken + '<');
                $cookieStore.put('token', result.data.userToken);
            });
        }
    };

}]);
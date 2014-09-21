'use strict';

var app = angular.module('conferenceBuddyApp', ['ngRoute', 'ngTouch', 'ngAnimate', 'ngCookies', 'ui.bootstrap']);

app.config(['$provide', '$routeProvider', '$httpProvider', function($provide, $routeProvider, $httpProvider) {

    $httpProvider.responseInterceptors.push('httpInterceptor');

    $routeProvider.when('/', {
        templateUrl: 'views/tracks.html',
        controller: 'TrackController'
    }).when('/mytrack', {
        templateUrl: 'views/mytrack.html',
        controller: 'MyTrackController'
    }).when('/register', {
        templateUrl: 'views/register.html',
        controller: 'RegistrationController'
    }).otherwise({
        redirectTo: '/'
    });

    // provides a catch-all handler for all non-catched errors
    $provide.decorator('$exceptionHandler', ['$delegate', function($delegate) {
        return function(exception, cause) {
            console.error(exception);
            $delegate(exception, cause);
        };
    }]);
}]);

/*
app.run(['RegistrationService', function(registrationService) {
    registrationService.init();
*/


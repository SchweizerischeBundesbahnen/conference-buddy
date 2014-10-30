'use strict';

var app = angular.module('conferenceBuddyApp', ['ngRoute', 'ngTouch', 'ngAnimate', 'ngCookies', 'ui.bootstrap']);

// route constants, injectable into services and controllers
app.constant('ROUTES', {
    CONFERENCE: '/',
    ABOUT: '/about',
    MYTRACK: '/mytrack',
    TRACKS: '/tracks',
    DETAILS: '/details',
    REGISTER: '/register'
})
app.constant('REST_URL', 'http://localhost:9000/conferencebuddy-web');


app.config(['$provide', '$routeProvider', '$httpProvider', 'ROUTES', function($provide, $routeProvider, $httpProvider, ROUTES) {

    // we are using the interceptor to check UserService status (auth-token)
    $httpProvider.responseInterceptors.push('HttpInterceptor');

    // routes
    $routeProvider.when(ROUTES.CONFERENCE, {
        templateUrl: 'views/conference.html',
        controller: 'ConferenceController'
    }).when(ROUTES.TRACKS, {
        templateUrl: 'views/tracks.html',
        controller: 'TrackController'
    }).when(ROUTES.TRACKS + ROUTES.DETAILS, {
        templateUrl: 'views/details.html',
        controller: 'DetailsController'
    }).when(ROUTES.MYTRACK, {
        templateUrl: 'views/mytrack.html',
        controller: 'MyTrackController'
    }).when(ROUTES.MYTRACK + ROUTES.DETAILS, {
        templateUrl: 'views/details.html',
        controller: 'DetailsController'
    }).when(ROUTES.REGISTER, {
        templateUrl: 'views/register.html',
        controller: 'UserController'
    }).when(ROUTES.ABOUT, {
        templateUrl: 'views/about.html',
        controller: 'AboutController'
    }).otherwise({
        redirectTo: ROUTES.CONFERENCE
    });

    // not sure if we need this?
    // provides a catch-all handler for all non-catched errors
    $provide.decorator('$exceptionHandler', ['$delegate', function($delegate) {
        return function(exception, cause) {
            console.error(exception);
            $delegate(exception, cause);
        };
    }]);
}]);


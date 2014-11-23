'use strict';

var app = angular.module('conferenceBuddyApp', ['ngRoute', 'ngTouch', 'ngAnimate', 'ngCookies', 'ui.bootstrap', 'angular-loading-bar']);

// route constants, injectable into services and controllers
app.constant('ROUTES', {
    CONFERENCE: '/',
    ABOUT: '/about',
    MYTRACK: '/mytrack',
    TRACKS: '/tracks',
    DETAILS: '/details',
    REGISTER: '/register'
});
app.constant('REST_URL', '/service');
app.constant('AUTH', {
    HTTP_HEADER_TOKEN: 'X-Access-Token',
    COOKIES_USERTOKEN: 'userToken',
    COOKIES_USER: 'user'
});

app.config(['$provide', '$routeProvider', '$httpProvider', 'ROUTES', function($provide, $routeProvider, $httpProvider, ROUTES) {

    // we are using the interceptor to check UserService status (auth-token)
    $httpProvider.responseInterceptors.push('HttpInterceptor');

    // routes
    $routeProvider.when(ROUTES.CONFERENCE, {
        templateUrl: 'views/conference.html',
        controller: 'ConferenceController',
        resolve: {
            service: initializeConference
        }
    }).when(ROUTES.TRACKS, {
        templateUrl: 'views/tracks.html',
        controller: 'TrackController',
        resolve: {
            service: initializeConference
        }
    }).when(ROUTES.TRACKS + ROUTES.DETAILS, {
        templateUrl: 'views/details.html',
        controller: 'DetailsController',
        resolve: {
            service: initializeConference
        }
    }).when(ROUTES.MYTRACK, {
        templateUrl: 'views/mytrack.html',
        controller: 'MyTrackController',
        resolve: {
            service: initializeConference
        }
    }).when(ROUTES.MYTRACK + ROUTES.DETAILS, {
        templateUrl: 'views/details.html',
        controller: 'DetailsController',
        resolve: {
            service: initializeConference
        }
    }).when(ROUTES.REGISTER, {
        templateUrl: 'views/register.html',
        controller: 'UserController'
    }).when(ROUTES.ABOUT, {
        templateUrl: 'views/about.html'
    }).otherwise({
        redirectTo: ROUTES.CONFERENCE
    });
}]);

var initializeConference = function($q, $http, ConferenceService) {
    var deferred = $q.defer();
    ConferenceService.load().then(function(conf) {
        deferred.resolve(ConferenceService);
    });
    return deferred.promise;
};

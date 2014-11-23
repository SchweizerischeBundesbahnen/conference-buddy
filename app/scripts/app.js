'use strict';

var app = angular.module('conferenceBuddyApp',
['ngRoute', 'ngTouch', 'ngAnimate', 'ngCookies', 'ui.bootstrap', 'angular-loading-bar', 'LocalStorageModule']);

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

app.config(['$provide', '$routeProvider', '$httpProvider', 'localStorageServiceProvider', 'ROUTES', function($provide, $routeProvider, $httpProvider,
                                                                                                             localStorageServiceProvider, ROUTES) {

    // we are using the interceptor to check UserService status (auth-token)
    $httpProvider.interceptors.push('HttpInterceptor');

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

    localStorageServiceProvider.setPrefix('conferenceBuddyApp').setNotify(false, false);
}]);

var initializeConference = function($q, $http, ConferenceService) {
    var deferred = $q.defer();
    ConferenceService.load().then(function(conf) {
        deferred.resolve(ConferenceService);
    });
    return deferred.promise;
};

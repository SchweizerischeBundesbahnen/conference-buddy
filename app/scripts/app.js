'use strict';

angular
  .module('konferenzBuddyApp', [
    'ngRoute',
    'ngTouch',
    'ui.bootstrap'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/bytrack', {
        templateUrl: 'views/bytrack.html',
        controller: 'BytrackCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });

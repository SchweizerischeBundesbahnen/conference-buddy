'use strict';

angular
  .module('conferenceBuddyApp', [
    'ngRoute',
    'ngTouch',
    'ui.bootstrap'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
				templateUrl: 'views/tracks.html',
				controller: 'TrackController'
      })
      .otherwise({
        redirectTo: '/'
      });
  });

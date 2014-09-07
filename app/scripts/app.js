'use strict';

angular.module('conferenceBuddyApp', [
    'ngRoute', 'ngTouch', 'ngAnimate', 'ui.bootstrap'
]).config(['$provide', '$routeProvider', function($provide, $routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/tracks.html',
        controller: 'TrackController'
    }).when('/mytrack', {
        templateUrl: 'views/mytrack.html',
        controller: 'MyTrackController'
    }).otherwise({
        redirectTo: '/'
    });

    // provides a catch-all handler for all non-catched errors
    $provide.decorator('$exceptionHandler', ['$delegate', '$injector', function($delegate, $injector) {
        return function(exception, cause) {
            console.error(exception);
            $delegate(exception, cause);
            try {
                var dialogService = $injector.get('DialogService');
                console.log(dialogService);
                var message = exception.toString();
                var status = exception.status ? 'Status=' + exception.status : '-';
                console.log(dialogService.showError);
                // dialogService.showError('Technical Error', message, status);
                console.error(message);
            } catch (err) {
                console.error(err);
            }
        };
    }]);
}]);

'use strict';

angular.module('conferenceBuddyApp', [
    'ngRoute', 'ngTouch', 'ui.bootstrap'
]).config(['$provide', '$routeProvider', function($provide, $routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/tracks.html',
        controller: 'TrackController'
    }).otherwise({
        redirectTo: '/'
    });
    // provides a catch-all handler for all non-catched errors
    $provide.decorator('$exceptionHandler', function($delegate, $injector) {
        return function(exception, cause) {
            console.error(exception);
            $delegate(exception, cause);
            var dialogService = $injector.get('DialogService');
            var message = exception.toString();
            var status = exception.status ? 'Status=' + exception.status : '-';
            dialogService.showError('Technical Error', message, status);
        };
    });
}]);

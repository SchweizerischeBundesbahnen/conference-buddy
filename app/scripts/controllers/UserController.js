'use strict';

angular.module('conferenceBuddyApp').controller('UserController',
['$scope', '$location', 'ConferenceService', 'UserService', 'DialogService', 'ROUTES',
    function($scope, $location, conferenceService, userService, dialogService, ROUTES) {

    $scope.step = 1;
    $scope.user = {};

    checkRegistrationLink();

    $scope.showRegistration = function() {
        $scope.step++;
    };

    $scope.register = function() {
        userService.register($scope.user).then(function() {
            $scope.step++;
        }).catch(function(err) {
            dialogService.showError('Backend Error', 'Failed to register at the backend', 'HTTP-Status:' + err.status);
            $scope.step--;
        });
    };

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', 'HTTP-Status:' + err.status);
    });

    function checkRegistrationLink() {
        if ($location.hash()) {
            userService.validate($location.hash()).then(function() {
                $location.path(ROUTES.MYTRACK);
            }).catch(function(err) {
                $location.path(ROUTES.REGISTER);
            });
        }
    }

}]);

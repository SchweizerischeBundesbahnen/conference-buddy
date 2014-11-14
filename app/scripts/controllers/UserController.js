'use strict';

angular.module('conferenceBuddyApp').controller('UserController',
['$scope', '$location', 'ConferenceService', 'UserService', 'DialogService', 'ROUTES', function($scope, $location, conferenceService, userService,
                                                                                                dialogService, ROUTES) {

    $scope.conference = {};
    $scope.currentTrack = {};
    $scope.step = 1;

    $scope.user = {};

    checkRegistrationLink();

    $scope.showTracks = function() {
        $location.path(ROUTES.TRACKS);
    };

    $scope.showRegistration = function() {
        $scope.step++;
    };

    $scope.register = function() {
        userService.register($scope.user).then(function() {
            $scope.step++;
        }).catch(function(err) {
            dialogService.showError('Backend Error', 'Failed to register at the backend', err.data + ' HTTP-Status:' + err.status);
            $scope.step--;
        });
    };

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', err.data + ' HTTP-Status:' + err.status);
    });

    function checkRegistrationLink() {
        if ($location.hash()) {
            userService.validate($location.hash()).then(function() {
                $scope.showTracks();
            }).catch(function(err) {
                $location.path(ROUTES.REGISTER);
            });
        }
    }

}]);

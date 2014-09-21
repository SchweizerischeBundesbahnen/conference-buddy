'use strict';

angular.module('conferenceBuddyApp').controller('RegistrationController',
['$scope', '$location', 'ConferenceService', 'RegistrationService', function($scope, $location, conferenceService, registrationService) {

    $scope.conference = {};
    $scope.currentTrack = {};
    $scope.step = 1;

    $scope.userid;
    $scope.email;
    $scope.nameSurname;

    $scope.showTracks = function() {
        $location.path('/');
    };

    $scope.showRegistration = function() {
        $scope.step++;
    }

    $scope.register = function() {
        registrationService.register($scope.nameSurname, $scope.email, $scope.userid).then(function() {
            $scope.step++;
        }).catch(function(err) {
            dialogService.showError('Backend Error', 'Failed to register at the backend', err.data + ' HTTP-Status:' + err.status);
            $scope.step--;
        });
    }

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', err.data + ' HTTP-Status:' + err.status);
    });

}]);

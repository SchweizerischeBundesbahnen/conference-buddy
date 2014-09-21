'use strict';

angular.module('conferenceBuddyApp')
    .controller('RegistrationController', ['$scope', '$location', 'ConferenceService', function($scope, $location, conferenceService) {

    $scope.conference = {};
    $scope.currentTrack = {};
    $scope.step = 1;

    $scope.showTracks = function() {
        $location.path('/');
    };

    $scope.goToStep2 = function() {
        $scope.step = 2;
    }

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
        $scope.currentTrack = $scope.conference.tracks[0];
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', err.data + ' HTTP-Status:' + err.status);
    });

}]);

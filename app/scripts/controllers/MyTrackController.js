'use strict';

angular.module('conferenceBuddyApp').controller('MyTrackController',
['$scope', '$location', 'ConferenceService', 'DialogService', function($scope, $location, conferenceService, dialogService) {

    $scope.conference = {};
    $scope.currentTrack = {};

    $scope.showTracks = function() {
        $location.path('/');
    }

    $scope.hasAbstract = function(talk) {
        return talk.abstract && talk.abstract.length > 0;
    };

    $scope.showDetails = function(index) {
        var talk = $scope.currentTrack.talks[index];
        if ($scope.hasAbstract(talk)) {
            var options = {
                talk: talk,
                formatSpeakers: $scope.formatSpeakers
            };
            dialogService.showModal({templateUrl: 'partials/talk.html'}, options);
        }
    };

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
        $scope.currentTrack = $scope.conference.tracks[0];
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', err.data + ' HTTP-Status:' + err.status);
    });

}]);

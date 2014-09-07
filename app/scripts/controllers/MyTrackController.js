'use strict';

angular.module('conferenceBuddyApp').controller('MyTrackController',
['$scope', '$location', 'ConferenceService', 'DialogService', function($scope, $location, conferenceService, dialogService) {

    $scope.conference = {};
    $scope.currentTrack = {};

    $scope.showTracks = function() {
        $location.path('/');
    }

    $scope.hasAbstract = function(presentation) {
        return presentation.abstract && presentation.abstract.length > 0;
    };

    $scope.showDetails = function(index) {
        var presentation = $scope.currentTrack.presentations[index];
        if ($scope.hasAbstract(presentation)) {
            var options = {
                talk: presentation,
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

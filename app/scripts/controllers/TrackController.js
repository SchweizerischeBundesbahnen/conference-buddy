'use strict';

angular.module('conferenceBuddyApp').controller('TrackController',
['$scope', 'ConferenceService', 'DialogService', 'MyTrackService', function($scope, conferenceService, dialogService, myTrackService) {

    $scope.conference = {tracks: [ ]};
    $scope.currentTrack = null;
    $scope.copperfield = '';
    $scope.myTrack = [];

    var currentTrackIndex = 0;

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
        updateTrack();
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', err.data + ' HTTP-Status:' + err.status);
    });

    myTrackService.load().then(function(myTrack) {
        $scope.myTrack = myTrack;
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load myTrack data from the backend', err.data + ' HTTP-Status:' + err.status);
    });

    $scope.formatSpeakers = function(talk) {
        var speakers = '';
        talk.speakers.forEach(function(speaker) {
            if (speakers !== '') {
                speakers += ' & ';
            }
            speakers += speaker.name + ' ' + speaker.surname;
        });
        return speakers;
    };

    $scope.nextTrack = function() {
        if ($scope.hasNextTrack()) {
            currentTrackIndex += 1;
            updateTrack();
        }
    };

    $scope.hasNextTrack = function() {
        return $scope.conference.tracks.length > currentTrackIndex + 1;
    };

    $scope.previousTrack = function() {
        if ($scope.hasPreviousTrack()) {
            currentTrackIndex -= 1;
            updateTrack();
        }
    };

    $scope.hasPreviousTrack = function() {
        return currentTrackIndex > 0;
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

    $scope.hasAbstract = function(talk) {
        return talk.abstract && talk.abstract.length > 0;
    };

    $scope.isMyTrack = function (talk) {
        var index = $scope.myTrack.indexOf(talk.talkId);
        return index !== -1;
    };
    
    function updateTrack() {
        $scope.currentTrack = $scope.conference.tracks[currentTrackIndex];
        toggleMagic();
    }

    function toggleMagic() {
        $scope.copperfield = $scope.copperfield ? '' : 'magic';
    }

}]);

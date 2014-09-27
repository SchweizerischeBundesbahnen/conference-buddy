'use strict';

angular.module('conferenceBuddyApp').controller('TrackController',
['$scope', '$location', 'ConferenceService', 'CommentService', 'DialogService', 'MyTrackService', 'UserService', function($scope, $location,
                                                                                                                          conferenceService,
                                                                                                                          commentService,
                                                                                                                          dialogService,
                                                                                                                          myTrackService,
                                                                                                                          userService) {

    $scope.conference = {tracks: [ ]};
    $scope.currentTrack = null;
    $scope.copperfield = '';
    $scope.myTrack = [];

    var currentTrackIndex = 0;

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
        updateTrack();
        if (userService.isRegistered()) {
            myTrackService.load().then(function(myTrack) {
                $scope.myTrack = myTrack;
            }).catch(function(err) {
                dialogService.showError('Backend Error', 'Failed to load myTrack data from the backend', err.data + ' HTTP-Status:' + err.status);
            });
        }
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', err.data + ' HTTP-Status:' + err.status);
    });

    $scope.formatSpeakers = function(presentation) {
        var speakers = '';
        presentation.speakers.forEach(function(speaker) {
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

    $scope.hasAbstract = function(presentation) {
        return presentation.abstract && presentation.abstract.length > 0;
    };

    $scope.showMyTrack = function() {
        $location.path('mytrack');
    };

    $scope.isMyTrack = function(presentation) {
        var index = $scope.myTrack.indexOf(presentation.id);
        return !presentation.common && index !== -1;
    };

    function updateTrack() {
        $scope.currentTrack = $scope.conference.tracks[currentTrackIndex];
        toggleMagic();
    }

    function toggleMagic() {
        $scope.copperfield = $scope.copperfield ? '' : 'magic';
    }

}]);

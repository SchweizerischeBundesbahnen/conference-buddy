'use strict';

angular.module('conferenceBuddyApp').controller('TrackController',
['$scope', 'ConferenceService', 'DialogService', 'MyTrackService', 'UserService', function($scope, conferenceService, dialogService, myTrackService,
                                                                                           userService) {

    $scope.conference = {tracks: [ ]};
    $scope.currentTrack = null;
    $scope.copperfield = '';
    $scope.myTrack = [];

    var currentTrackIndex = 0;

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

    $scope.isOnMyTrack = function(presentation) {
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

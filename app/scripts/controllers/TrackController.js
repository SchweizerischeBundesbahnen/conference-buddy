'use strict';

angular.module('conferenceBuddyApp').controller('TrackController',
['$scope', 'ConferenceService', 'MyTrackService', 'UserService', 'DialogService',
    function($scope, conferenceService, myTrackService, userService, dialogService) {

    $scope.conference = {tracks: [ ]};
    $scope.copperfield = '';
    $scope.myTrack = [];

    // conferenceService  will initialized after conferenceService (http-promise!)
    $scope.conferenceService = {hasNextTrack: function() {
    }, hasPreviousTrack: function() {
    }};

    conferenceService.load().then(function(conference) {
        $scope.conferenceService = conferenceService;
        $scope.currentTrack = conferenceService.currentTrack();
        $scope.conference = conference;
        if (userService.isRegistered()) {
            myTrackService.load().then(function(myTrack) {
                $scope.myTrack = myTrack;
            }).catch(function(err) {
                if (err.status !== 401) {
                    dialogService.showError('Backend Error', 'Failed to load myTrack data from the backend', 'HTTP-Status:' + err.status);
                }
            });
        }
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', 'HTTP-Status:' + err.status);
    });

    $scope.nextTrack = function() {
        $scope.currentTrack = $scope.conferenceService.nextTrack();
        toggleMagic();
    };

    $scope.previousTrack = function() {
        $scope.currentTrack = $scope.conferenceService.previousTrack();
        toggleMagic();
    };

    $scope.isOnMyTrack = function(presentation) {
        var index = $scope.myTrack.indexOf(presentation.id);
        return !presentation.common && index !== -1;
    };

    function toggleMagic() {
        $scope.copperfield = $scope.copperfield ? '' : 'magic';
    }
}
]);

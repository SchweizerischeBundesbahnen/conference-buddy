'use strict';

angular.module('conferenceBuddyApp').controller('MyTrackController',
['$scope', 'ConferenceService', 'DialogService', 'MyTrackService', function($scope, conferenceService, dialogService, myTrackService) {

    $scope.conference = {};
    $scope.myTrack = {};

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
        myTrackService.loadResolved($scope.conference).then(function(myTrack) {
            $scope.myTrack = myTrack;
        }).catch(function(err) {
            dialogService.showError('Backend Error', 'Failed to load myTrack data from the backend', err.data + ' HTTP-Status:' + err.status);
        });
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', err.data + ' HTTP-Status:' + err.status);
    });

}]);

'use strict';

angular.module('conferenceBuddyApp').controller('ConferenceController',
['$scope', '$location', 'ConferenceService', 'DialogService', function($scope, $location, conferenceService, dialogService) {

    $scope.averageRating = 6;

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', err.data + ' HTTP-Status:' + err.status);
    });

}]);

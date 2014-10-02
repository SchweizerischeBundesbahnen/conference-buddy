'use strict';

angular.module('conferenceBuddyApp').controller('DetailsController',
    ['$scope', '$routeParams', '$window', 'ConferenceService', function($scope, $routeParams, $window, conferenceService) {

    $scope.conference = {tracks: [ ]};
    $scope.presentation = null;

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
        $scope.presentation = conferenceService.currentPresentation();
    });

    $scope.goBack = function() {
        $window.history.back();
    }

}]);

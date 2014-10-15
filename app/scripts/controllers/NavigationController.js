'use strict';

angular.module('conferenceBuddyApp').controller('NavigationController', ['$scope', '$location', 'ROUTES', function($scope, $location, ROUTES) {

    $scope.isConferenceActive = function() {
        return ROUTES.CONFERENCE === $location.path();
    };

    $scope.isTracksActive = function() {
        return ROUTES.TRACKS === $location.path() || (ROUTES.TRACKS + ROUTES.DETAILS) === $location.path();
    };

    $scope.isMyTrackActive = function() {
        return ROUTES.MYTRACK === $location.path() || (ROUTES.MYTRACK + ROUTES.DETAILS) === $location.path() || ROUTES.REGISTER === $location.path();
    };

    $scope.isAboutActive = function() {
        return ROUTES.ABOUT === $location.path();
    };

    $scope.goToMyTrack = function() {
        $location.path(ROUTES.MYTRACK);
    };

    $scope.goToTracks = function() {
        $location.path(ROUTES.TRACKS);
    };

    $scope.goToConference = function() {
        $location.path(ROUTES.CONFERENCE);
    };

    $scope.goToAbout = function() {
        $location.path(ROUTES.ABOUT);
    };

}]);

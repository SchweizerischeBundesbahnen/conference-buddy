'use strict';

angular.module('conferenceBuddyApp').controller('TrackSwitchController', ['$scope', '$location', function($scope, $location) {

    $scope.showTracks = function() {
        $location.path('/');
    };

    $scope.showMyTrack = function() {
        $location.path('mytrack');
    };

}]);

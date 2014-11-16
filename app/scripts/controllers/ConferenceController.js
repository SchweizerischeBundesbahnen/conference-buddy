'use strict';

angular.module('conferenceBuddyApp').controller('ConferenceController',
['$scope', '$location', 'ConferenceService', 'RatingService', 'UserService', 'DialogService',
    function($scope, $location, conferenceService, ratingService, userService, dialogService) {

    $scope.averageRating = 0;
    $scope.hasRatings = false;
    $scope.myRating = undefined;

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
        loadRatings();
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', 'HTTP-Status:' + err.status);
    });

    $scope.rate = function() {
        if (userService.isRegistered()) {
            ratingService.save($scope.conference.id, $scope.myRating).then(function(rating) {
                loadRatings();
            });
        }
    };

    $scope.isRegistered = function() {
        return userService.isRegistered();
    };

    function loadRatings() {
        ratingService.load($scope.conference.id).then(function(rating) {
            $scope.hasRatings = (rating.averageRate !== undefined);
            if ($scope.hasRatings) {
                $scope.averageRating = rating.averageRate;
            }
            $scope.myRating = (rating.rate !== undefined) ? rating.rate : 0;
        }).catch(function(err) {
            dialogService.showError('Backend Error', 'Failed to load ratings from the backend', 'HTTP-Status:' + err.status);
        });
    }

}]);

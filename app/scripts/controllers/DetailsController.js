'use strict';

angular.module('conferenceBuddyApp').controller('DetailsController',
    ['$scope', '$routeParams', '$window', 'ConferenceService', 'RatingService', function($scope, $routeParams, $window, conferenceService, ratingService) {

    $scope.conference = {tracks: [ ]};
    $scope.presentation = null;

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
        $scope.presentation = conferenceService.currentPresentation();
        ratingService.load($scope.presentation.id).then(function(rating) {
            $scope.hasRatings = (rating.average !== undefined);
            if ($scope.hasRatings) {
                $scope.averageRating = rating.average;
            }
            $scope.hasMyRating = (rating.myRating !== undefined);
            $scope.myRating = $scope.hasMyRating ? rating.myRating : 0;
        });
    });

    $scope.goBack = function() {
        $window.history.back();
    };

    $scope.rate = function() {
        var newRating;

        if ($scope.hasMyRating) {
            newRating = ratingService.update($scope.presentation.id, $scope.myRating);
        } else {
            newRating = ratingService.save($scope.presentation.id, $scope.myRating);
            $scope.hasMyRating = true;
        }

        $scope.hasRatings = true;
        $scope.averageRating = newRating.average;
    };
}]);

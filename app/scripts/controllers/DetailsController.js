'use strict';

angular.module('conferenceBuddyApp').controller('DetailsController',
    ['$scope', '$routeParams', '$window', '$location', 'ConferenceService', 'RatingService', function($scope, $routeParams, $window, $location,
        conferenceService, ratingService) {

    $scope.conference = {tracks: [ ]};
    $scope.presentation = null;

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
        $scope.presentation = conferenceService.currentPresentation();
        if ($scope.presentation == null) {
            // presentation is null or undefined. can happen if the user has the details page open and closes the browser. when he re-opens the
            // browser and restores the open tabs from his last session, there will be no current presentation. in that case, we will redirect him
            // to the home page
            $location.url('/');
        } else {
            ratingService.load($scope.presentation.id).then(function(rating) {
                $scope.hasRatings = (rating.average !== undefined);
                if ($scope.hasRatings) {
                    $scope.averageRating = rating.average;
                }
                $scope.hasMyRating = (rating.myRating !== undefined);
                $scope.myRating = $scope.hasMyRating ? rating.myRating : 0;
            });
        }
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

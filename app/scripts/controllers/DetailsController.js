'use strict';

angular.module('conferenceBuddyApp').controller('DetailsController',
    ['$scope', '$routeParams', '$window', '$location', 'ConferenceService', 'RatingService', 'UserService', 'ROUTES', function($scope, $routeParams, $window, $location,
        conferenceService, ratingService, userService, ROUTES) {

    $scope.conference = {tracks: []};
    $scope.presentation = null;

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
        $scope.presentation = conferenceService.currentPresentation();
        if (!$scope.presentation) {
            // presentation is null or undefined. can happen if the user has the details page open and closes the browser. when he re-opens the
            // browser and restores the open tabs from his last session, there will be no current presentation. in that case, we will redirect him
            // to the home page
            $location.path(ROUTES.TRACKS);
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
        if (userService.isRegistered()) {
            // "Rate!" sollte sowieso nur angezeigt werden, wenn der User registriert ist. Trotzdem hier nochmal prüfen zur Sicherheit.
            ratingService.save($scope.presentation.id, $scope.myRating).then(function(rating) {
                $scope.hasMyRating = true;
                $scope.hasRatings = true;
                $scope.averageRating = rating.average;
            });
        }
    };

    $scope.isRegistered = function() {
        return userService.isRegistered();
    };
}]);

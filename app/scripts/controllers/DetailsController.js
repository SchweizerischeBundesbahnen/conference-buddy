'use strict';

angular.module('conferenceBuddyApp').controller('DetailsController',
['$scope', '$routeParams', '$window', '$location', 'ConferenceService', 'RatingService', 'UserService', 'ROUTES',
    function($scope, $routeParams, $window, $location, conferenceService, ratingService, userService, ROUTES) {

    $scope.conference = {tracks: []};
    $scope.presentation = null;
    $scope.rated;

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
        $scope.presentation = conferenceService.currentPresentation();
        if (!$scope.presentation) {
            // presentation is null or undefined. can happen if the user has the details page open and closes the browser. when he re-opens the
            // browser and restores the open tabs from his last session, there will be no current presentation. in that case, we will redirect him
            // to the home page
            $location.path(ROUTES.TRACKS);
        } else {
            loadRatings();
        }
    });

    $scope.goBack = function() {
        $window.history.back();
    };

    $scope.rate = function() {
        if (userService.isRegistered()) {
            ratingService.save($scope.presentation.id, $scope.myRating).then(function(rating) {
                loadRatings();
            });
        }
    };

    $scope.isRegistered = function() {
        return userService.isRegistered();
    };

    function loadRatings() {
        ratingService.load($scope.presentation.id).then(function(rating) {
            $scope.hasRatings = (rating.averageRate !== undefined);
            if ($scope.hasRatings) {
                $scope.averageRating = rating.averageRate;
            }
            $scope.rated = rating.rate || false;
            $scope.myRating = rating.rate || 0;
        });

    }
}]);

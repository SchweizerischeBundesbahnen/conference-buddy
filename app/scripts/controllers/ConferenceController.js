'use strict';

angular.module('conferenceBuddyApp').controller('ConferenceController',
['$scope', '$location', 'ConferenceService', 'RatingService', 'DialogService', function($scope, $location, conferenceService, ratingService,
                                                                                        dialogService) {

    $scope.averageRating = 0;
    $scope.hasRatings = false;
    $scope.hasMyRating = false;

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
        ratingService.load($scope.conference.id).then(function(rating) {
            $scope.hasRatings = (rating.average !== undefined);
            if ($scope.hasRatings) {
                $scope.averageRating = rating.average;
            }
            $scope.hasMyRating = (rating.myRating !== undefined);
            $scope.myRating = $scope.hasMyRating ? rating.myRating : 0;
            console.log($scope.hasRatings);
            console.log($scope.averageRating);
            console.log($scope.myRating);
        }).catch(function(err) {
            dialogService.showError('Backend Error', 'Failed to load ratings from the backend', err.data + ' HTTP-Status:' + err.status);
        });

    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', err.data + ' HTTP-Status:' + err.status);
    });

}]);

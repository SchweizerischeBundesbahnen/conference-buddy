'use strict';

angular.module('conferenceBuddyApp').factory('RatingService', ['$http', function($http) {

    return {
        load: function(presentationId) {
            console.log('loading ratings for presentation ' + presentationId);
            return $http.get('api/ratings.json').then(function(result) {
                var i;
                for (i = 0; i < result.data.length; i++) {
                    if (result.data[i].presentationId === presentationId) {
                    console.log('returning rating ' + result.data[i]);
                        return result.data[i];
                    }
                }
                console.log('returning empty rating');
                return {
                    'presentationId': presentationId
                };
            });
        },
        save: function(presentationId, rating) {
            console.log('Rating presentation ' + presentationId + ' as a solid ' + rating);
            return {
                'presentationId': presentationId,
                'average': 5.3,
                'myRating': rating
            };
            /*
            $http.post('api/rating/' + talkId, {
                'rating': rating
            }).then(function(result) {
                return result.data;
            });
            */
        },
        update: function(presentationId, rating) {
            console.log('Correcting rating for presentation ' + presentationId + ' to ' + rating);
            return {
                'presentationId': presentationId,
                'average': 3.7,
                'myRating': rating
            };
            /*
            $http.put('api/rating/' + talkId, {
                'id': ratingId,
                'rating': rating
            });
            */
        }
    };
}]);
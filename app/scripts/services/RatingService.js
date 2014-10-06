'use strict';

angular.module('conferenceBuddyApp').factory('RatingService', ['$http', function($http) {

    return {
        load: function(presentationId) {
            return $http.get('api/ratings.json').then(function(result) {
                var i;
                for (i = 0; i < result.data.length; i++) {
                    if (result.data[i].presentationId === presentationId) {
                        return result.data[i];
                    }
                }
                return {
                    'presentationId': presentationId
                };
            });
        },
        save: function(presentationId, rating) {
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

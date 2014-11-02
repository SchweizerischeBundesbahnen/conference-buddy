'use strict';

angular.module('conferenceBuddyApp').factory('CommentService', ['$http', function($http) {

    function getRandomInt(min, max) {
        return Math.floor(Math.random() * (max - min)) + min;
    }

    return {
        load: function(talkId) {
            return $http.get('api/comments.json').then(function(result) {
                var number = getRandomInt(0, 100);
                var text = 'Random Text: ' + number;

                result.data.push({
                    'author': {
                        'name': 'Random User'
                    },
                    'comment': text,
                    'timestamp': '2014-09-08T18:26:00.000Z'
                });
                return result.data;
            });
        },
        save: function(commentEntry) {
            console.log('Save comment [' + commentEntry.author + ', ' + commentEntry.comment + ', ' + commentEntry.timestamp + ']');
        }
    };

}]);
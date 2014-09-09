'use strict';

angular.module('conferenceBuddyApp').factory('CommentService', ['$http', function($http) {

    return {
        load: function(talkId) {
            console.log(talkId);
            return $http.get('api/comments.json').then(function(result) {
                return result.data;
            });
        },
        save: function(commentEntry) {
            console.log('Save comment [' + commentEntry.author + ', ' + commentEntry.comment + ', ' + commentEntry.timestamp + ']');
        }
    };

}]);
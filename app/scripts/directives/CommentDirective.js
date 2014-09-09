'use strict';

angular.module('conferenceBuddyApp').directive('comments', ['CommentService', 'UserService', function(commentService, userService) {
    return {
        restrict: 'E',
        scope: {
            talkId: '@'
        },
        templateUrl: 'templates/comments-template.html',
        controller: function($scope) {
            $scope.commentEntry = null;
            $scope.author = userService.currentUser();

            commentService.load($scope.talkId).then(function(data) {
                $scope.comments = data;
            });

            $scope.submitForm = function(isValid) {
                if (isValid) {
                    var commentEntry = angular.copy($scope.commentEntry);
                    commentEntry.author = $scope.author;
                    commentEntry.timestamp = new Date().toJSON();
                    commentService.save(commentEntry);
                    $scope.comments.splice(0, 0, commentEntry);
                    $scope.commentEntry = null;
                }
            };

            $scope.formatDate = function(comment) {
                var date = new Date(comment.timestamp);
                var month = date.getMonth() + 1;
                return date.getDate() + '.' + month + '.' + date.getFullYear() + ' ' + date.getHours() + ':' + date.getMinutes();
            };

        }
    };
}]);
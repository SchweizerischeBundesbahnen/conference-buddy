'use strict';

angular.module('conferenceBuddyApp').directive('comments', ['CommentService', 'UserService', function(commentService, userService) {
    return {
        restrict: 'E',
        scope: {
            talkId: '@'
        },
        templateUrl: '../../templates/comments-template.html',
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
                    commentService.save(commentEntry);
                    $scope.comments.splice(0, 0, commentEntry);
                    $scope.commentEntry = null;
                }
            };
        }
    };
}]);
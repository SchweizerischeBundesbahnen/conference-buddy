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
                var i = $scope.comments[0];
                console.log(i.time);
            });

            $scope.submitForm = function(isValid) {
                if (isValid) {
                    var commentEntry = angular.copy($scope.commentEntry);
                    commentEntry.author = $scope.author;
                    commentEntry.time = JSON.stringify(new Date());
                    console.log(commentEntry.time);
                    commentService.save(commentEntry);
                    $scope.comments.splice(0, 0, commentEntry);
                    $scope.commentEntry = null;
                }
            };
        }
    };
}]);
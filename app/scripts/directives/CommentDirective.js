'use strict';

angular.module('conferenceBuddyApp').directive('comments', ['CommentService', 'UserService', function(commentService, userService) {
    return {
        restrict: 'E',
        scope: {
            pid: '@'
        },
        templateUrl: 'templates/comments-template.html',
        controller: function($scope) {

            $scope.commentEntry = null;
            $scope.author = userService.currentUser();

            $scope.load = function() {
                commentService.load($scope.pid).then(function(data) {
                    $scope.comments = data;
                });
            };

            $scope.load();

            $scope.submitForm = function(isValid) {
                if (userService.isRegistered()) {
                    if (isValid) {
                        commentService.save($scope.pid, $scope.commentEntry.value).then(function(comment) {
                            // TODO why not reload all here?
                            $scope.comments.splice(0, 0, comment);
                        });
                        $scope.commentEntry = null;
                    }
                }
            };

            $scope.formatDate = function(comment) {
                return moment(comment.timestamp).fromNow();
            };

            $scope.showInputForm = function() {
                return userService.isRegistered();
            };
        }
    };
}]);
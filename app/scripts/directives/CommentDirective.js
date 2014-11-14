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
                var date = new Date(comment.timestamp);
                var month = date.getMonth() + 1;
                var hours = (date.getHours() < 10) ? '0' : '';
                hours = hours + date.getHours();
                var mins = (date.getMinutes() < 10) ? '0' : '';
                mins = mins + date.getMinutes();
                return date.getDate() + '.' + month + '.' + date.getFullYear() + ' ' + hours + ':' + mins;
            };

            $scope.showInputForm = function() {
                return userService.isRegistered();
            };
        }
    };
}]);
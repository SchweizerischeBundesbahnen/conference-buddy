'use strict';

angular.module('conferenceBuddyApp').controller('AboutController',
['$scope', '$location', 'UserService', 'ROUTES', function($scope, $location, userService, ROUTES) {

    $scope.user = userService.currentUser();

    $scope.isRegistered = function() {
        return userService.isRegistered();
    };

    $scope.unregister = function() {
        userService.unregister();
        $location.url(ROUTES.REGISTER);
    };

    $scope.getUserId = function() {
      if($scope.user) {
        return $scope.user.userId;
      }
      return '-';
    };

}]);

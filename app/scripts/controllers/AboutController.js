'use strict';

angular.module('conferenceBuddyApp').controller('AboutController',
  ['$scope', '$location', 'UserService', 'ROUTES', function ($scope, $location, userService, ROUTES) {

    $scope.user = userService.currentUser();

    $scope.contributors = shuffle(["Christian Eichenberger", "Gilles Zimmermann", "Sebastian Graf", "Korhan Gülseven",
      "Manuel Friedli", "Marc Walter", "Michelle Luginbühl", "Nicolas Cotting", "Stefan Zeller"]);

    $scope.isRegistered = function () {
      return userService.isRegistered();
    };

    $scope.unregister = function () {
      userService.unregister();
      $location.url(ROUTES.REGISTER);
    };

    $scope.getUserId = function () {
      if ($scope.user) {
        return $scope.user.userId;
      }
      return '-';
    };

    function shuffle (array) {
      for (var i = array.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
      }
      return array.join(", ");
    }

  }]);

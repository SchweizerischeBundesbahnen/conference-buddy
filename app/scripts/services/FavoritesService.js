'use strict';

angular.module('conferenceBuddyApp').factory('FavoritesService', ['$http', function($http) {
    return {
        load: function() {
            return $http.get('api/favorites.json').then(function(result) {
                return result.data;
            });
        }
    };
}]);
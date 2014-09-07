'use strict';

angular.module('conferenceBuddyApp').factory('MyTrackService', ['$http', function($http) {
    return {
        load: function() {
            return $http.get('api/myTrack.json').then(function(result) {
                return result.data;
            });
        }
    };
}]);
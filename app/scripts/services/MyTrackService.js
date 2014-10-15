'use strict';

angular.module('conferenceBuddyApp').factory('MyTrackService', ['$http', '$location', 'ROUTES', function($http, $location, ROUTES) {

    function lookupPresentation(conference, presentationId) {
        for (var i = 0; i < conference.tracks.length; i++) {
            var track = conference.tracks[i];
            for (var j = 0; j < track.presentations.length; j++) {
                var presentation = track.presentations[j];
                if (presentation.id === presentationId) {
                    return presentation;
                }
            }
        }
    }

    return {
        load: function() {
            // TODO simulates server check & httpinterceptor
            if (!$http.defaults.headers.common['X-Access-Token']) {
                $location.url(ROUTES.REGISTER);
            }

            return $http.get('api/myTrack.json').then(function(result) {
                return result.data;
            });
        },

        loadResolved: function(conference) {
            return this.load().then(function(myTrackIds) {
                var myTrack = { presentations: [] };
                myTrackIds.forEach(function(presentationId) {
                    var presentation = lookupPresentation(conference, presentationId);
                    myTrack.presentations.push(presentation);
                });
                return myTrack;
            });
        }
    };
}]);
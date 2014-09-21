'use strict';

angular.module('conferenceBuddyApp').factory('MyTrackService', ['$http', '$location', function($http, $location) {

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

            // TODO simulates server check
            if (!$http.defaults.headers.common['X-Access-Token']) {
                $location.url('/register');
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
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

    function lookupTalk(conference, talkId) {
        var result;
        conference.talks.forEach(function(talk) {
            if (talkId === talk.id) {
                result = talk;
            }
        });
        return result;
    }

    function createMyTrack(conference, myTrackIds) {
        var result = [];
        conference.tracks[0].presentations.forEach(function(presentation) {
            var myTrackPresentationId, pres;
            var talkId = presentation.talkId;
            var talk = lookupTalk(conference, talkId);
            if (talk.common) {
                result.push(presentation);
            } else {
                myTrackPresentationId = myTrackIds[0];
                myTrackIds = myTrackIds.slice(1);
                pres = lookupPresentation(conference, myTrackPresentationId);
                result.push(pres);
            }

        });
        return result;
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
                myTrack.presentations = createMyTrack(conference, myTrackIds);
                return myTrack;
            });
        },

        createMyTrack: function(conference, myTrackIds) {
            return createMyTrack(conference, myTrackIds);
        }

    };
}]);
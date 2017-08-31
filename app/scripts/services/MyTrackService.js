'use strict';

angular.module('conferenceBuddyApp').factory('MyTrackService',
['$http', '$location', 'UserService', 'ROUTES', 'REST_URL', function($http, $location, userService, ROUTES, REST_URL) {

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

    /**
     * Returns an array of presentations create from myTrackIds. The
     * result will contain every common talk and all presentation
     * referenced by given myTrackIds. Presumes myTrackIds to be
     * ordered according to time.start.
     */
    function createMyTrack(conference, myTrackIds) {
        var result = [];
        if (!myTrackIds) {
            return result;
        }
        // pick first track as reference (which actually doesn't matter)
        var presentations = conference.tracks[0].presentations;
        var talk, myTrackPid, myTrackPres;
        for (var i = 0; i < presentations.length; i++) {
            talk = lookupTalk(conference, presentations[i].talkId);
            if (talk.common) {
                result.push(presentations[i]);
            } else if (myTrackIds.length > 0) {
                // parseInt to accept both strings and ints
                myTrackPid = myTrackIds[0];
                myTrackPres = lookupPresentation(conference, myTrackPid);
                if (i == (presentations.length - 1) || startsBefore(myTrackPres, presentations[i + 1])) {
                    myTrackIds = myTrackIds.slice(1);
                    result.push(myTrackPres);
                }
            }
        }
        return result;
    }

    function startsBefore(pres1, pres2) {
        return pres1.time.start < pres2.time.start;
    }

    return {
        load: function() {
            if (!userService.isRegistered()) {
                $location.url(ROUTES.REGISTER);
            }
            return $http.get(REST_URL + '/mytrack', {cache: true}).then(function(result) {
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

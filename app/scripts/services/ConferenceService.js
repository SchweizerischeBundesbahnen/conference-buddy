'use strict';

angular.module('conferenceBuddyApp').factory('ConferenceService', ['$http', '$q', function($http, $q) {

    // will be initialized the first time load() is called
    var conference;

    // points to the last loaded Track
    var currentTrackIndex = -1;

    // ref to selected presentation
    var currentPresentation;

    return {

        load: function() {

            if (conference) {
                var deferred = $q.defer();
                deferred.resolve(conference);
                return deferred.promise;
            }

            return $http.get('api/conference.json').then(function(result) {

                conference = result.data;
                currentTrackIndex = 0;

                var speakerMap = {};
                conference.speakers.forEach(function(speaker) {
                    speakerMap[speaker.id] = speaker;
                });

                var talkMap = {};
                conference.talks.forEach(function(talk) {
                    talk.speakers = [];
                    talk.speakerIds.forEach(function(speakerId) {
                        talk.speakers.push(speakerMap[speakerId]);
                    });
                    talkMap[talk.id] = talk;
                });

                conference.tracks.forEach(function(track) {
                    track.presentations.forEach(function(presentation) {
                        var talk = talkMap[presentation.talkId];
                        presentation.common = talk.common;
                        presentation.title = talk.title;
                        presentation.abstract = talk.abstract;
                        presentation.speakers = talk.speakers;
                    });
                });

                return conference;
            });
        },

        currentTrack: function() {
            return conference.tracks[currentTrackIndex];
        },

        currentPresentation: function() {
            return currentPresentation;
        },

        nextTrack: function() {
            if (hasNextTrack()) {
                currentTrackIndex += 1;
                return this.currentTrack();
            } else {
                currentTrackIndex = 0;
                return this.currentTrack();
            }
        },

        previousTrack: function() {
            if (hasPreviousTrack()) {
                currentTrackIndex -= 1;
                return this.currentTrack();
            } else {
                currentTrackIndex = conference.tracks.length - 1;
                return this.currentTrack();
            }
        },

        selectPresentation: function(presentation) {
            currentPresentation = presentation;
        }
    };

    function hasNextTrack() {
        return conference.tracks.length > currentTrackIndex + 1;
    }

    function hasPreviousTrack() {
        return currentTrackIndex > 0;
    }

}]);

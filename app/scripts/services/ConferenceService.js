'use strict';

angular.module('conferenceBuddyApp').factory('ConferenceService', ['$http', function($http) {

    return  {
        load: function() {
            return $http.get('api/conference.json').then(function(result) {
                var conference = result.data;

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
                        var talk = talkMap[presentation.talkId]
                        presentation.common = talk.common;
                        presentation.title = talk.title;
                        presentation.abstract = talk.abstract;
                        presentation.speakers = talk.speakers;
                    });
                });
                return conference;
            });
        }
    };

}]);

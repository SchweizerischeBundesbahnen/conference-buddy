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
                    talkMap[talk.id] = talk;
                });

                conference.tracks.forEach(function(track) {
                    track.talks.forEach(function(talk) {
                        talk.title = talkMap[talk.talkId].title;
                        talk.abstract = talkMap[talk.talkId].abstract;
                        talk.speakers = [];
                        talk.speakerIds.forEach(function(speakerId) {
                            talk.speakers.push(speakerMap[speakerId]);
                        });
                    });
                });
                return conference;
            });
        }
    };

}]);

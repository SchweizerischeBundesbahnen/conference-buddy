'use strict';

angular.module('conferenceBuddyApp').controller('MyTrackController',
['$scope', '$location', 'ConferenceService', 'MyTrackService', 'DialogService', function($scope, $location, conferenceService, myTrackService,
                                                                                         dialogService) {

    $scope.conference = {};
    $scope.myTrack = {};

    $scope.showTracks = function() {
        $location.path('/');
    };

    $scope.hasAbstract = function(presentation) {
        return presentation.abstract && presentation.abstract.length > 0;
    };

    $scope.showDetails = function(index) {
        var presentation = $scope.myTrack.presentations[index];
        if ($scope.hasAbstract(presentation)) {
            var options = {
                talk: presentation,
                formatSpeakers: $scope.formatSpeakers
            };
            dialogService.showModal({templateUrl: 'partials/talk.html'}, options);
        }
    };

    conferenceService.load().then(function(conference) {
        $scope.conference = conference;
        myTrackService.loadResolved($scope.conference).then(function(myTrack) {
            $scope.myTrack = myTrack;
        }).catch(function(err) {
            dialogService.showError('Backend Error', 'Failed to load myTrack data from the backend', err.data + ' HTTP-Status:' + err.status);
        });
    }).catch(function(err) {
        dialogService.showError('Backend Error', 'Failed to load conference data from the backend', err.data + ' HTTP-Status:' + err.status);
    });

    $scope.formatSpeakers = function(presentation) {
        var speakers = '';
        presentation.speakers.forEach(function(speaker) {
            if (speakers !== '') {
                speakers += ' & ';
            }
            speakers += speaker.name + ' ' + speaker.surname;
        });
        return speakers;
    };

}]);

'use strict';

angular.module('conferenceBuddyApp').controller('PresentationController', ['$scope', '$location', 'ConferenceService', function($scope, $location, conferenceService) {

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

    $scope.hasAbstract = function(presentation) {
        return presentation.abstract && presentation.abstract.length > 0;
    };

    $scope.showDetails = function(index, track) {
        var presentation = track.presentations[index];
        if ($scope.hasAbstract(presentation)) {
            conferenceService.selectPresentation(presentation);
            $location.path('/details');
        }
    };

}]);

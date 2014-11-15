'use strict';

angular.module('conferenceBuddyApp').controller('PresentationController', ['$scope', '$location', 'ConferenceService', 'ROUTES',
    function($scope, $location, conferenceService, ROUTES) {

    $scope.ROUTES = ROUTES;

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

    $scope.showDetails = function(index, track, contextUrl) {
        var presentation = track.presentations[index];
        if ($scope.hasAbstract(presentation)) {
            conferenceService.selectPresentation(presentation);
            $location.path(contextUrl + ROUTES.DETAILS);
        }
    };

}]);

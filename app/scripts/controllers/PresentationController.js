'use strict';

angular.module('conferenceBuddyApp').controller('PresentationController', ['$scope', 'DialogService', function($scope, dialogService) {

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
            var options = {
                talk: presentation,
                formatSpeakers: $scope.formatSpeakers
            };
            dialogService.showModal({templateUrl: 'partials/talk.html'}, options);
        }
    };

}]);

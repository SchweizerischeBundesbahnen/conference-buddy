'use strict';

angular.module('conferenceBuddyApp')
	.controller('TrackController', ['$scope', 'ConferenceService', 'DialogService', function ($scope, conferenceService, dialogService) {

		$scope.conference = {tracks: [ ]};
		$scope.currentTrack = null;
		$scope.showDetailsIndex = -1;

		var currentTrackIndex = 0;

		conferenceService.load().then(function (conference) {
			$scope.conference = conference;
			updateTrack();
		}).catch(function (err) {
			dialogService.showError('Backend Error', 'Failed to load conference data from the backend',
					err.data + ' HTTP-Status:' + err.status);
		});

		$scope.formatSpeakers = function (talk) {
			var speakers = '';
			talk.speakers.forEach(function (speaker) {
				if (speakers !== '') {
					speakers += ' & ';
				}
				speakers += speaker.name + ' ' + speaker.surname;
			});
			return speakers;
		};

		$scope.nextTrack = function () {
			if ($scope.hasNextTrack()) {
				currentTrackIndex += 1;
				updateTrack();
			}
		};

		$scope.hasNextTrack = function () {
			return $scope.conference.tracks.length > currentTrackIndex + 1;
		};

		$scope.previousTrack = function () {
			if ($scope.hasPreviousTrack()) {
				currentTrackIndex -= 1;
				updateTrack();
			}
		};

		$scope.hasPreviousTrack = function () {
			return currentTrackIndex > 0;
		};

		$scope.showDetails = function (index) {
			var options = {
				talk: $scope.currentTrack.talks[index],
				formatSpeakers: $scope.formatSpeakers
			};
			dialogService.showModal({templateUrl: 'partials/talk.html'}, options);
		};
		function updateTrack() {
			$scope.currentTrack = $scope.conference.tracks[currentTrackIndex];
		}

	}]);

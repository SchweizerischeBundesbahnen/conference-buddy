'use strict';

describe('Controller: PresentationController', function() {

    var scope, createController, rootScope, conferenceService;

    beforeEach(function() {

        angular.mock.module('conferenceBuddyApp', function(/*$provide*/) {
            // configure $provide constants etc..
        });

        angular.mock.inject(function($rootScope, $q, $controller, ConferenceService) {

            var mockedConf = {tracks: [
                {id: 'teaTime', title: 'The Art of Tea',
                    presentations: [
                        {speakers: [
                            {name: 'Mr.', surname: 'Pink'},
                            {name: 'Mr.', surname: 'White'}
                        ],
                            abstract: 'hello'},
                        {speakers: [
                            {name: 'Mr.', surname: 'White'}
                        ]}
                    ]},
                {id: 'coffeeTime', title: 'A Rush of Caffeine to the Head'}
            ]};

            rootScope = $rootScope;

            // new scope
            scope = $rootScope.$new();

            scope.conference = mockedConf;
            scope.currentTrack = mockedConf.tracks[0];

            // controller initialization
            createController = function() {
                return $controller('PresentationController', {$scope: scope});
            };

            conferenceService = ConferenceService;

        });
    });

    afterEach(function() {
    });

    it('should format speakers name correctly ', function() {
        createController();
        rootScope.$apply();

        var speaker = scope.formatSpeakers(scope.conference.tracks[0].presentations[0]);
        expect(speaker).toBe('Mr. Pink & Mr. White');

        speaker = scope.formatSpeakers(scope.conference.tracks[0].presentations[1]);
        expect(speaker).toBe('Mr. White');

        speaker = scope.formatSpeakers({speakers: []});
        expect(speaker).toBe('');
    });

    it('should show the details view', function() {
        createController();
        rootScope.$apply();
        spyOn(conferenceService, 'selectPresentation');

        expect(scope.currentTrack.presentations.length).toBe(2);
        scope.showDetails(0, scope.currentTrack);

        expect(conferenceService.selectPresentation).toHaveBeenCalled();
        var args = conferenceService.selectPresentation.calls[0].args;
        expect(args[0]).toEqual(scope.currentTrack.presentations[0]);
    });

    it('should not show the details modal dialog', function() {
        createController();
        rootScope.$apply();
        spyOn(conferenceService, 'selectPresentation');

        expect(scope.currentTrack.presentations.length).toBe(2);
        scope.showDetails(1, scope.currentTrack);

        expect(conferenceService.selectPresentation).not.toHaveBeenCalled();
    });

});

'use strict';

describe('Controller: PresentationController', function() {

    var scope, createController, rootScope, dialogService;

    beforeEach(function() {

        angular.mock.module('conferenceBuddyApp', function(/*$provide*/) {
            // configure $provide constants etc..
        });

        angular.mock.inject(function($rootScope, $q, $controller, DialogService) {

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

            dialogService = DialogService;

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

    it('should show the details modal dialog', function() {
        createController();
        rootScope.$apply();
        spyOn(dialogService, 'showModal');

        expect(scope.currentTrack.presentations.length).toBe(2);
        scope.showDetails(0, scope.currentTrack);

        expect(dialogService.showModal).toHaveBeenCalled();
        var args = dialogService.showModal.calls[0].args;
        expect(args[0]).toEqual({templateUrl: 'partials/talk.html'});
        expect(args[1]).toEqual(jasmine.objectContaining({
            talk: scope.currentTrack.presentations[0]
        }));
    });

    it('should not show the details modal dialog', function() {
        createController();
        rootScope.$apply();
        spyOn(dialogService, 'showModal');

        expect(scope.currentTrack.presentations.length).toBe(2);
        scope.showDetails(1, scope.currentTrack);

        expect(dialogService.showModal).not.toHaveBeenCalled();
    });

});

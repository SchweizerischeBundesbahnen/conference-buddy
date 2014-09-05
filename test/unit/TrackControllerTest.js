'use strict';

describe('Controller: TrackController', function() {

    var scope, createController, rootScope, dialogService, favoritesService;

    beforeEach(function() {

        angular.mock.module('conferenceBuddyApp', function(/*$provide*/) {
            // configure $provide constants etc..
        });

        angular.mock.inject(function($rootScope, $q, $controller, ConferenceService, DialogService, FavoritesService) {

            var mockedConf = {tracks: [
                {id: 'teaTime', title: 'The Art of Tea',
                    talks: [
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

            // mock 'load' resolving the promise
            var deferred = $q.defer();
            deferred.resolve(mockedConf);
            spyOn(ConferenceService, 'load').andReturn(deferred.promise);

            var mockedFavs = ['a1', 'a2', 'a3'];

            // mock 'load' resolving the promise
            deferred = $q.defer();
            deferred.resolve(mockedFavs);
            spyOn(FavoritesService, 'load').andReturn(deferred.promise);

            rootScope = $rootScope;

            // new scope
            scope = $rootScope.$new();

            // controller initialization
            createController = function() {
                return $controller('TrackController', {$scope: scope});
            };

            dialogService = DialogService;
            favoritesService = FavoritesService;

        });
    });

    afterEach(function() {
    });

    it('should load JSON initially', function() {
        createController();
        rootScope.$apply();
        expect(scope.conference).toBeDefined();
        expect(scope.conference.tracks.length).toBe(2);
        expect(scope.conference.tracks[0].title).toBe('The Art of Tea');
    });

    it('should start with first Track initially', function() {
        createController();
        rootScope.$apply();
        expect(scope.currentTrack.id).toBe('teaTime');
    });

    it('should change to next and prev track', function() {
        createController();
        rootScope.$apply();

        expect(scope.hasNextTrack()).toBe(true);
        scope.nextTrack();
        expect(scope.currentTrack.id).toBe('coffeeTime');
        expect(scope.hasNextTrack()).toBe(false);

        scope.previousTrack();
        expect(scope.currentTrack.id).toBe('teaTime');
        expect(scope.hasPreviousTrack()).toBe(false);
    });

    it('should format speakers name correctly ', function() {
        createController();
        rootScope.$apply();

        var speaker = scope.formatSpeakers(scope.conference.tracks[0].talks[0]);
        expect(speaker).toBe('Mr. Pink & Mr. White');

        speaker = scope.formatSpeakers(scope.conference.tracks[0].talks[1]);
        expect(speaker).toBe('Mr. White');

        speaker = scope.formatSpeakers({speakers: []});
        expect(speaker).toBe('');
    });

    it('should show the details modal dialog', function() {
        createController();
        rootScope.$apply();
        spyOn(dialogService, 'showModal');

        expect(scope.currentTrack.talks.length).toBe(2);
        scope.showDetails(0);

        expect(dialogService.showModal).toHaveBeenCalled();
        var args = dialogService.showModal.calls[0].args;
        expect(args[0]).toEqual({templateUrl: 'partials/talk.html'});
        expect(args[1]).toEqual(jasmine.objectContaining({
            talk: scope.currentTrack.talks[0]
        }));
    });

    it('should not show the details modal dialog', function() {
        createController();
        rootScope.$apply();
        spyOn(dialogService, 'showModal');

        expect(scope.currentTrack.talks.length).toBe(2);
        scope.showDetails(1);

        expect(dialogService.showModal).not.toHaveBeenCalled();
    });
});

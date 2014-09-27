'use strict';

describe('Controller: TrackController', function() {

    var scope, createController, rootScope, location, dialogService, myTrackService, commentService;

    beforeEach(function() {

        angular.mock.module('conferenceBuddyApp', function(/*$provide*/) {
            // configure $provide constants etc..
        });

        angular.mock.inject(function($rootScope, $q, $controller, $location, ConferenceService, DialogService, MyTrackService, CommentService) {

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

            // mock 'load' resolving the promise
            var deferred = $q.defer();
            deferred.resolve(mockedConf);
            spyOn(ConferenceService, 'load').andReturn(deferred.promise);

            var mockedMyTrack = ['a1', 'a2', 'a3'];

            // mock 'load' resolving the promise
            deferred = $q.defer();
            deferred.resolve(mockedMyTrack);
            spyOn(MyTrackService, 'load').andReturn(deferred.promise);

            var mockedComments = [
                {
                    'author': {
                        'name': 'Ellie McInelli'
                    },
                    'comment': 'Supergeil!',
                    'timestamp': '2014-09-08T18:25:00.000Z'
                },
                {
                    'author': {
                        'name': 'Axel Schweiss'
                    },
                    'comment': 'Worum gehts hier?',
                    'timestamp': '2014-09-08T18:26:00.000Z'
                }
            ];

            // mock 'load' resolving the promise
            deferred = $q.defer();
            deferred.resolve(mockedComments);
            spyOn(CommentService, 'load').andReturn(deferred.promise);

            rootScope = $rootScope;

            location = $location;

            // new scope
            scope = $rootScope.$new();

            // controller initialization
            createController = function() {
                return $controller('TrackController', {$scope: scope});
            };

            dialogService = DialogService;
            myTrackService = MyTrackService;
            commentService = CommentService;

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

});

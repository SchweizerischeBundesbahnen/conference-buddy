'use strict';

describe('Controller: TrackController', function() {

    var scope, createController, rootScope, location, httpBackend, dialogService, myTrackService, commentService, conferenceService;

    beforeEach(function() {

        angular.mock.module('conferenceBuddyApp', function(/*$provide*/) {
            // configure $provide constants etc..
        });

        angular.mock.inject(function($rootScope, $q, $controller, $location, $httpBackend, ConferenceService, DialogService, MyTrackService,
                                     CommentService) {

            var mockedConf = {
                speakers: [
                    {id: 1, name: 'Mr.', surname: 'Pink'},
                    {id: 2, name: 'Mr.', surname: 'White'}
                ],
                talks: [
                    {id: 'teaTime', title: 'The Art of Tea', speakerIds: [1, 2]},
                    {id: 'coffeeTime', title: 'The Merits of Coffee', speakerIds: [1]}
                ],
                tracks: [
                    {id: 'tea4Life', title: 'Tea for life', presentations: [
                        {talkId: 'teaTime', roomId: '1'}
                    ]},
                    {id: 'coffeeFTW', title: 'CoffeeEEEEE', presentations: [
                        {talkId: 'coffeeTime', roomId: '2'}
                    ]}
                ],
                rooms: [
                    {id: 1, name: 'Future Lounge'},
                    {id: 2, name: 'Sky Lounge'}
                ]
            };

            $httpBackend.whenGET('api/conference.json').respond(mockedConf);

            var mockedMyTrack = ['a1', 'a2', 'a3'];

            // mock 'load' resolving the promise
            var deferred = $q.defer();
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

            httpBackend = $httpBackend;
            dialogService = DialogService;
            myTrackService = MyTrackService;
            commentService = CommentService;
            conferenceService = ConferenceService;

        });
    });

    afterEach(function() {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should load JSON initially', function() {
        createController();
        rootScope.$apply();
        httpBackend.flush();
        expect(scope.conference).toBeDefined();
        expect(scope.conference.tracks.length).toBe(2);
        expect(scope.conference.tracks[0].title).toBe('Tea for life');
    });

    it('should start with first Track initially', function() {
        createController();
        rootScope.$apply();
        httpBackend.flush();
        expect(scope.currentTrack.id).toBe('tea4Life');
    });

    it('should change to next and prev track', function() {
        createController();
        rootScope.$apply();
        httpBackend.flush();
        scope.nextTrack();
        expect(scope.currentTrack.id).toBe('coffeeFTW');
        scope.nextTrack();
        expect(scope.currentTrack.id).toBe('tea4Life');

        scope.previousTrack();
        scope.previousTrack();
        expect(scope.currentTrack.id).toBe('tea4Life');
        scope.previousTrack();
        expect(scope.currentTrack.id).toBe('coffeeFTW');
    });

    it('should have room', function() {
        createController();
        rootScope.$apply();
        httpBackend.flush();
        expect(scope.currentTrack.presentations[0].room.name).toBe('Future Lounge');
    });
});

'use strict';

describe('Controller: TrackSwitchController', function() {

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
                return $controller('TrackSwitchController', {$scope: scope});
            };

            dialogService = DialogService;
            myTrackService = MyTrackService;
            commentService = CommentService;

        });
    });

    afterEach(function() {
    });

    it('should navigate to mytrack view', function() {
        createController();
        expect(location.path()).toBe('');
        scope.showMyTrack();
        expect(location.path()).toBe('/mytrack');
    });
});

'use strict';

describe('Controller: MyTrackService', function() {

    var myTrackService, httpBackend, conference;

    conference = {
        'talks': [
            {
                'id': 'pause',
                'common': true
            },
            {
                "id": "mittagessen",
                "common": true
            },
            {
                'id': 'keynote',
                'common': true
            },
            {
                'id': 'a1',
                'common': false
            },
            {
                'id': 'a2',
                'common': false
            },
            {
                'id': 'b1',
                'common': false
            },
            {
                'id': 'b2',
                'common': false
            }
        ],
        'tracks': [
            {
                'id': 'kunden',
                'presentations': [
                    {
                        'id': 5,
                        'talkId': 'keynote'
                    },
                    {
                        'id': 15,
                        'talkId': 'a1',
                        "time": {
                            "start": "10:00"
                        }
                    },
                    {
                        'id': 20,
                        'talkId': 'pause',
                        "time": {
                            "start": "10:40"
                        }
                    },
                    {
                        'id': 25,
                        'talkId': 'a2',
                        "time": {
                            "start": "11:00"
                        }
                    },
                    {
                        'id': 30,
                        'talkId': 'mittagessen',
                        "time": {
                            "start": "12:00"
                        }
                    }
                ]
            },
            {
                'id': 'innovation',
                'presentations': [
                    {
                        'id': 6,
                        'talkId': 'keynote'
                    },
                    {
                        'id': 16,
                        'talkId': 'b1',
                        "time": {
                            "start": "10:00"
                        }
                    },
                    {
                        'id': 21,
                        'talkId': 'pause',
                        "time": {
                            "start": "10:40"
                        }
                    },
                    {
                        'id': 26,
                        'talkId': 'b2',
                        "time": {
                            "start": "11:00"
                        }
                    },
                    {
                        'id': 31,
                        'talkId': 'mittagessen',
                        "time": {
                            "start": "12:00"
                        }
                    }
                ]
            }
        ]
    };

    // load the controller's module
    beforeEach(function() {
        angular.mock.module('conferenceBuddyApp', function($provide) {
            $provide.constant('REST_URL', '/api-mock');
        });
        angular.mock.inject(function(_MyTrackService_, $httpBackend) {
            myTrackService = _MyTrackService_;
            httpBackend = $httpBackend;
        });

        // myTrackJson = [25];

        // httpBackend.whenGET('/api-mock/mytrack').respond(myTrackJson);
    });

    afterEach(function() {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should fetch JSON on load ', function() {
        var myTrackIds = [15, 26];
        httpBackend.whenGET('/api-mock/mytrack').respond(myTrackIds);
        var myTrack = loadJsonMock();
        expect(myTrack).toBeDefined();
        expect(myTrack).toEqual(myTrackIds);
    });

    it('should create MyTrack correct with complete registration', function() {

        var myTrackIds = [15, 26];
        httpBackend.whenGET('/api-mock/mytrack').respond(myTrackIds);

        var myTrack = loadJsonMock();
        var result = myTrackService.createMyTrack(conference, myTrack);

        expect(result.length).toBe(5);
        expect(result[0].talkId).toBe('keynote');
        expect(result[1].talkId).toBe('a1');
        expect(result[2].talkId).toBe('pause');
        expect(result[3].talkId).toBe('b2');
        expect(result[4].talkId).toBe('mittagessen');
    });

    it('should create MyTrack correct with noncomplete registration', function() {

        // registrated to only one presentation (instead of 2)
        var myTrackIds = [26];
        httpBackend.whenGET('/api-mock/mytrack').respond(myTrackIds);

        var myTrack = loadJsonMock();
        var result = myTrackService.createMyTrack(conference, myTrack);

        expect(result.length).toBe(4);
        expect(result[0].talkId).toBe('keynote');
        expect(result[1].talkId).toBe('pause');
        expect(result[2].talkId).toBe('b2');
        expect(result[3].talkId).toBe('mittagessen');
    });


    function loadJsonMock() {
        httpBackend.expectGET('/api-mock/mytrack');
        var myTrack;
        myTrackService.load().then(function(result) {
            myTrack = result;
        });
        httpBackend.flush();
        return myTrack;
    }
});
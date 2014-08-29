'use strict';

describe('Controller: ConferenceService', function() {

    var conferenceService, httpBackend, conferenceJson;

    // load the controller's module
    beforeEach(function() {
        angular.mock.module('conferenceBuddyApp');
        angular.mock.inject(function(_ConferenceService_, $httpBackend) {
            conferenceService = _ConferenceService_;
            httpBackend = $httpBackend;
        });

        conferenceJson = {
            speakers: [
                {id: 1, name: 'Mr.', surname: 'Pink'},
                {id: 2, name: 'Mr.', surname: 'White'}
            ],
            talks: [
                {id: 1, title: 'Picking your own color'},
                {id: 2, title: 'Bam.Bam.Bam'}
            ],
            tracks: [
                {id: 'coreTec', talks: [
                    {talkId: 1, speakerIds: [1, 2]},
                    {talkId: 2, speakerIds: [2]}
                ]},
                {id: 'javaTec', talks: [
                    {talkId: 2, speakerIds: [1]}
                ]}
            ]
        };
        httpBackend.whenGET('api/conference.json').respond(conferenceJson);
    });

    afterEach(function() {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should fetch JSON on load ', function() {
        var conference = loadJsonMock();
        expect(conference).toBeDefined();
    });

    it('should transform/denormalize JSON on load', function() {
        var conference = loadJsonMock();
        expect(conference.speakers.length).toBe(2);
        expect(conference.talks.length).toBe(2);
        expect(conference.tracks.length).toBe(2);
        expect(conference.tracks[0].id).toBe('coreTec');
        expect(conference.tracks[0].talks.length).toBe(2);

        expect(conference.tracks[0].talks[0].title).toBe(conferenceJson.talks[0].title);
        expect(conference.tracks[0].talks[0].speakers.length).toBe(2);
        expect(conference.tracks[0].talks[0].speakers[0].surname).toBe('Pink');
        expect(conference.tracks[0].talks[0].speakers[1].surname).toBe('White');

        expect(conference.tracks[0].talks[1].title).toBe(conferenceJson.talks[1].title);
        expect(conference.tracks[0].talks[1].speakers.length).toBe(1);
        expect(conference.tracks[0].talks[1].speakers[0].surname).toBe('White');
    });

    function loadJsonMock() {
        httpBackend.expectGET('api/conference.json');
        var conference;
        conferenceService.load().then(function(result) {
            conference = result;
        });
        httpBackend.flush();
        return conference;
    }
});

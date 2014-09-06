'use strict';

describe('Controller: MyTrackService', function() {

    var myTrackService, httpBackend, myTrackJson;

    // load the controller's module
    beforeEach(function() {
        angular.mock.module('conferenceBuddyApp');
        angular.mock.inject(function(_MyTrackService_, $httpBackend) {
            myTrackService = _MyTrackService_;
            httpBackend = $httpBackend;
        });

        myTrackJson = [
            'a1', 'a2', 'a3'
        ];
        httpBackend.whenGET('api/myTrack.json').respond(myTrackJson);
    });

    afterEach(function() {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should fetch JSON on load ', function() {
        var myTrack = loadJsonMock();
        expect(myTrack).toBeDefined();
    });

    it('should transform/denormalize JSON on load', function() {
        var myTrack = loadJsonMock();
        expect(myTrack.length).toBe(3);
        expect(myTrack[0]).toBe('a1');
        expect(myTrack[2]).toBe('a3');
    });

    function loadJsonMock() {
        httpBackend.expectGET('api/myTrack.json');
        var myTrack;
        myTrackService.load().then(function(result) {
            myTrack = result;
        });
        httpBackend.flush();
        return myTrack;
    }
});
'use strict';

describe('Controller: MyTrackService', function() {

    var myTrackService, httpBackend, myTrackJson;

    // load the controller's module
    beforeEach(function() {
        angular.mock.module('conferenceBuddyApp', function($provide) {
            $provide.constant('REST_URL', '/api-mock');
        });
        angular.mock.inject(function(_MyTrackService_, $httpBackend) {
            myTrackService = _MyTrackService_;
            httpBackend = $httpBackend;
        });

        myTrackJson = [
            'a1', 'a2', 'a3'
        ];
        httpBackend.whenGET('/api-mock/mytrack').respond(myTrackJson);
    });

    afterEach(function() {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should GET mytrack on load ', function() {
        httpBackend.expectGET('/api-mock/mytrack');
        myTrackService.load().then(function(result) {
            expect(result).toBeDefined();
            expect(result).toEqual(myTrackJson);
        });
        httpBackend.flush();
    });
});
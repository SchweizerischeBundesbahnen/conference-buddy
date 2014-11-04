'use strict';

describe('Controller: RatingService', function() {

    var ratingService, httpBackend, ratingsJson;

    // load the controller's module
    beforeEach(function() {
        angular.mock.module('conferenceBuddyApp', function($provide) {
            $provide.constant('REST_URL', '/api-mock');
        });
        angular.mock.inject(function(_RatingService_, $httpBackend) {
            ratingService = _RatingService_;
            httpBackend = $httpBackend;
        });

        ratingsJson = {
            'pid': 13,
            'average': 2.5,
            'myRating': 3
        };
        httpBackend.whenGET('/api-mock/rating/13').respond(ratingsJson);
    });

    afterEach(function() {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should fetch JSON on load ', function() {
        httpBackend.expectGET('/api-mock/rating/13');
        ratingService.load(13).then(function(result) {
            expect(result).toBeDefined();
            expect(result).toEqual(ratingsJson);
        });
        httpBackend.flush();
    });
});

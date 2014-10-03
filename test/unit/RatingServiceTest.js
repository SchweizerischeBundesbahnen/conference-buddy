'use strict';

describe('Controller: RatingService', function() {

    var ratingService, httpBackend, ratingsJson;

    // load the controller's module
    beforeEach(function() {
        angular.mock.module('conferenceBuddyApp');
        angular.mock.inject(function(_RatingService_, $httpBackend) {
            ratingService = _RatingService_;
            httpBackend = $httpBackend;
        });

        ratingsJson = [
            {
                'presentationId': 1,
                'average': 3.5
            },
            {
                'presentationId': 2,
                'average': 2.5,
                'myRating': 3
            },
            {
                'presentationId': 3
            }
        ];
        httpBackend.whenGET('api/ratings.json').respond(ratingsJson);
    });

    afterEach(function() {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should fetch almost-empty JSON if loading with nonexistent ID', function() {
        var ratings = loadJsonMock(99);
        expect(ratings).toEqual({'presentationId': 99});
    });

    it('should fetch JSON on load ', function() {
        var ratings = loadJsonMock(1);
        expect(ratings).toBeDefined();
    });

    it('should transform/denormalize JSON on load', function() {
        var ratings = loadJsonMock(2);
        expect(ratings.presentationId).toBe(2);
        expect(ratings.average).toBe(2.5);
        expect(ratings.myRating).toBe(3);
    });

    // Tests for update() and save() must be written later, when the real functionality is implemented

    function loadJsonMock(presentationId) {
        httpBackend.expectGET('api/ratings.json');
        var ratings;
        ratingService.load(presentationId).then(function(result) {
           ratings = result;
       });
        httpBackend.flush();
        return ratings;
    }
});

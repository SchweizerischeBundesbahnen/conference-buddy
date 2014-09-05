'use strict';

describe('Controller: FavoritesService', function() {

    var favoritesService, httpBackend, favoritesJson;

    // load the controller's module
    beforeEach(function() {
        angular.mock.module('conferenceBuddyApp');
        angular.mock.inject(function(_FavoritesService_, $httpBackend) {
            favoritesService = _FavoritesService_;
            httpBackend = $httpBackend;
        });

        favoritesJson = [
            'a1', 'a2', 'a3'
        ];
        httpBackend.whenGET('api/favorites.json').respond(favoritesJson);
    });

    afterEach(function() {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should fetch JSON on load ', function() {
        var favorites = loadJsonMock();
        expect(favorites).toBeDefined();
    });

    it('should transform/denormalize JSON on load', function() {
        var favorites = loadJsonMock();
        expect(favorites.length).toBe(3);
        expect(favorites[0]).toBe('a1');
        expect(favorites[2]).toBe('a3');
    });

    function loadJsonMock() {
        httpBackend.expectGET('api/favorites.json');
        var favorites;
        favoritesService.load().then(function(result) {
            favorites = result;
        });
        httpBackend.flush();
        return favorites;
    }
});
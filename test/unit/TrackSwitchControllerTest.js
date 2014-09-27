'use strict';

describe('Controller: TrackSwitchController', function() {

    var scope, createController, rootScope, location;

    beforeEach(function() {

        angular.mock.module('conferenceBuddyApp', function(/*$provide*/) {
            // configure $provide constants etc..
        });

        angular.mock.inject(function($rootScope, $q, $controller, $location) {

            rootScope = $rootScope;

            location = $location;

            // new scope
            scope = $rootScope.$new();

            // controller initialization
            createController = function() {
                return $controller('TrackSwitchController', {$scope: scope});
            };

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

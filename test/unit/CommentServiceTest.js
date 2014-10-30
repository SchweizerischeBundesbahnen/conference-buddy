'use strict';

describe('Controller: CommentService', function() {

    var commentService, httpBackend, commentsJson;

    // load the controller's module
    beforeEach(function() {
        angular.mock.module('conferenceBuddyApp', function($provide) {
            $provide.constant('REST_URL', '/api-mock');
        });
        angular.mock.inject(function(_CommentService_, $httpBackend) {
            commentService = _CommentService_;
            httpBackend = $httpBackend;
        });

        commentsJson = [
            { comment: "c1"},
            { comment: "c2"}
        ];
        httpBackend.whenGET('/api-mock/comment/13').respond(commentsJson);
        httpBackend.whenPUT('/api-mock/comment').respond(commentsJson);
        httpBackend.whenPOST('/api-mock/comment/33').respond({});
        httpBackend.whenDELETE('/api-mock/comment/33').respond({});
    });

    afterEach(function() {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should GET comments on load ', function() {
        httpBackend.expectGET('/api-mock/comment/13');
        commentService.load(13).then(function(result) {
            expect(result).toBeDefined();
            expect(result).toEqual(commentsJson);
        });
        httpBackend.flush();
    });

    it('should POST comment on update ', function() {
        httpBackend.expectPOST('/api-mock/comment/33', {comment: 'foo'});
        commentService.update(33, 'foo').then(function(result) {
            expect(result).toBeDefined();
            expect(result).toEqual({});
        });
        httpBackend.flush();
    });

    it('should PUT comment on save ', function() {
        httpBackend.expectPUT('/api-mock/comment', {pid: 333, comment: 'foo'});
        commentService.save(333, 'foo').then(function(result) {
            expect(result).toBeDefined();
            expect(result).toEqual(commentsJson);
        });
        httpBackend.flush();
    });

    it('should DELETE comment on delete ', function() {
        httpBackend.expectDELETE('/api-mock/comment/33');
        commentService.delete(33).then(function(result) {
            expect(result).toBeDefined();
            expect(result).toEqual({});
        });
        httpBackend.flush();
    });

});

'use strict';

describe('Controller: CommentService', function() {

    var commentService, httpBackend, commentsJson;

    // load the controller's module
    beforeEach(function() {
        angular.mock.module('conferenceBuddyApp');
        angular.mock.inject(function(_CommentService_, $httpBackend) {
            commentService = _CommentService_;
            httpBackend = $httpBackend;
        });

        commentsJson = [
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
        httpBackend.whenGET('api/comments.json').respond(commentsJson);
    });

    afterEach(function() {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should fetch JSON on load ', function() {
        var comments = loadJsonMock();
        expect(comments).toBeDefined();
    });

    it('should transform/denormalize JSON on load', function() {
        var comments = loadJsonMock();
        expect(comments.length).toBe(2);

        expect(comments[0].author.name).toBe(commentsJson[0].author.name);
        expect(comments[1].comment).toBe(commentsJson[1].comment);
    });

    function loadJsonMock() {
        httpBackend.expectGET('api/comments.json');
        var comments;
        commentService.load().then(function(result) {
            comments = result;
        });
        httpBackend.flush();
        return comments;
    }
});

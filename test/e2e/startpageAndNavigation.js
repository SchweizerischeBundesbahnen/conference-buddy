'use strict';

describe('Tracks page ', function() {

    beforeEach(function() {
        browser.get('http://localhost:9000');
    });

    it('should have a title', function() {
        expect(browser.getTitle()).toEqual('conference-buddy');
    });

    it('should show first Track', function() {
        expect(element(by.binding('currentTrack.title')).getText()).toEqual('Core Tec');
    });

    it('should only show nextTrack arrow', function() {
        var previousTrack = element(by.css('.glyphicon-chevron-left'));
        expect(previousTrack.isDisplayed()).toBe(false);
        var nextTrack = element(by.css('.glyphicon-chevron-right'));
        expect(nextTrack.isDisplayed()).toBe(true);
    });

    it('should navigate to next track on click until last one', function() {
        var nextTrack = element(by.css('.glyphicon-chevron-right'));
        nextTrack.click();
        expect(element(by.binding('currentTrack.title')).getText()).toEqual('Java Tec');
        nextTrack.click();
        expect(element(by.binding('currentTrack.title')).getText()).toEqual('Beyond Now');
        nextTrack.click();
        expect(element(by.binding('currentTrack.title')).getText()).toEqual('Engineering Arts');
        expect(nextTrack.isDisplayed()).toBe(false);
    });

    it('should popup details of talk on click', function() {
        var talk = element.all(by.css('.clickable')).first();
        var talkTitle = talk.element(by.css('.header'));
        talk.click();
        var talkDetailsTitle = element(by.css('.modal-title')).element(by.tagName('h4'));
        expect(talkDetailsTitle.getText()).toEqual(talkTitle.getText());
    });

    var hasClass = function(element, cls) {
        return element.getAttribute('class').then(function(classes) {
            return classes.split(' ').indexOf(cls) !== -1;
        });
    };
});
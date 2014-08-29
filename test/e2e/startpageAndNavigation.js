'use strict';

describe('Tracks page ', function() {

    beforeEach(function() {
        browser.get('http://localhost:9000');
    });

    it('should have a title', function() {
        expect(browser.getTitle()).toEqual('conference-buddy');
    });

    it('should show first Track', function() {
        expect(element(by.binding('currentTrack.title')).getText()).toEqual('Track: Core Tec');
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
        expect(element(by.binding('currentTrack.title')).getText()).toEqual('Track: Java Tec');
        nextTrack.click();
        expect(element(by.binding('currentTrack.title')).getText()).toEqual('Track: Beyond Now');
        nextTrack.click();
        expect(element(by.binding('currentTrack.title')).getText()).toEqual('Track: Engineering Arts');
        expect(nextTrack.isDisplayed()).toBe(false);
    });

    it('should select talk on click', function() {
        var elem = element.all(by.repeater('talk in currentTrack.talks')).first();
        expect(hasClass(elem, 'selected')).toBe(false);
        elem.click();
        expect(hasClass(elem, 'selected')).toBe(true);
        elem.click();
        expect(hasClass(elem, 'selected')).toBe(false);
    });

    var hasClass = function(element, cls) {
        return element.getAttribute('class').then(function(classes) {
            return classes.split(' ').indexOf(cls) !== -1;
        });
    };
});
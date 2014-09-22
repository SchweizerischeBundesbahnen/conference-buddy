'use strict';

angular.module('conferenceBuddyApp').directive('collapsibleText', ['$compile', function($compile) {
    return {
        restrict: 'A',
        replace: true,
        link: function(scope, element, attrs) {

            // initialize collapsed status
            scope.collapsed = false;

            // function to toggle text collapse
            scope.toggle = function() {
                scope.collapsed = !scope.collapsed;
            };

            attrs.$observe('collapsibleText', function(maxLength) {
                // get text content
                var text = element.text();

                if (text.length > maxLength) {
                    var shortText = String(text).substring(0, maxLength);
                    var longText = String(text).substring(maxLength, text.length);

                    var shortTextElement = $compile('<span>' + shortText + '</span>')(scope);
                    var longTextElement = $compile('<span ng-if="collapsed">' + longText + ' </span>')(scope);
                    var moreIndicator = $compile('<span ng-if="!collapsed">... </span>')(scope);
                    var toggleTextButton = $compile('<span class="collapsible-text-toggle" ng-click="toggle()"><small>{{collapsed ? "(weniger)" : "(mehr)"}}</small></span>')(scope);

                    element.empty();
                    element.append(shortTextElement);
                    element.append(longTextElement);
                    element.append(moreIndicator);
                    element.append(toggleTextButton);
                }
            });
        }
    };
}]);
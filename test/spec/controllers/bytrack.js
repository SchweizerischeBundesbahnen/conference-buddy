'use strict';

describe('Controller: BytrackCtrl', function () {

  // load the controller's module
  beforeEach(module('konferenzBuddyApp'));

  var BytrackCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    BytrackCtrl = $controller('BytrackCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});

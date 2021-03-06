'use strict';

// Karma configuration
// http://karma-runner.github.io/0.10/config/configuration-file.html

module.exports = function(config) {
    config.set({
        // base path, that will be used to resolve files and exclude
        basePath: '',

        // testing framework to use (jasmine/mocha/qunit/...)
        frameworks: ['jasmine'],

        // list of files / patterns to load in the browser
        files: [
            '../app/bower_components/angular/angular.js',
            '../app/bower_components/angular-touch/angular-touch.js',
            '../app/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            '../app/bower_components/angular-mocks/angular-mocks.js',
            '../app/bower_components/angular-route/angular-route.js',
            '../app/bower_components/angular-animate/angular-animate.js',
            '../app/bower_components/angular-cookies/angular-cookies.js',
            '../app/bower_components/angular-loading-bar/build/loading-bar.js',
            '../app/bower_components/angular-local-storage/dist/angular-local-storage.js',
            '../app/scripts/*.js',
            '../app/scripts/**/*.js',
            'unit/**/*.js'
        ],

        // list of files / patterns to exclude
        exclude: [],

        // web server port
        port: 8080,

        // level of logging
        // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
        logLevel: config.LOG_INFO,

        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: false,

        // Start these browsers, currently available:
        // - Chrome
        // - ChromeCanary
        // - Firefox
        // - Opera
        // - Safari (only Mac)
        // - PhantomJS
        // - IE (only Windows)
        browsers: ['Chrome' ],

        // Continuous Integration mode
        // if true, it capture browsers, run tests and exit
        singleRun: true,

        reporters: ['progress', 'junit', 'html', 'coverage'],

        htmlReporter: {
            outputFile: 'reports/junit/units.html'
        },

        junitReporter: {
            outputFile: 'reports/junit/TESTS-xunit.xml'
        },

        preprocessors: {
          // source files, that you wanna generate coverage for
          // do not include tests or libraries
          // (these files will be instrumented by Istanbul)
          '../app/scripts/**/*.js': ['coverage']
        },

        // optionally, configure the reporter
        coverageReporter: {
          type:   'lcov',
          dir:    'reports',
          subdir: 'coverage'
        }
    });
};

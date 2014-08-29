# Conference-Buddy
Conference-Buddy, your buddy app for conferences!

[![Build Status](https://travis-ci.org/SchweizerischeBundesbahnen/conference-buddy.svg?branch=feature%2FSTZE%2Fmake_npm_test_success)](https://travis-ci.org/SchweizerischeBundesbahnen/conference-buddy)

### Installation instructions
```
git clone https://github.com/SchweizerischeBundesbahnen/conference-buddy.git
cd conference-buddy
npm install
```

### Run it
```
npm serve
```

### Running unit tests
```
npm test
```

### Running E2E tests
First install Protractor
```
npm install -g protractor
```
Selenium needs to be setup manually (see [here](https://github.com/angular/protractor/issues/1005)). Download it from http://docs.seleniumhq.org/download/ and ChromeDriver from  http://chromedriver.storage.googleapis.com/index.html. Put them under `\npm\node_modules\protractor\selenium`. 

Now start E2E tests with
```
npm run protractor
```

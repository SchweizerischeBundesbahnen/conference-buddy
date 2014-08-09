'use strict';

var module = angular.module('konferenzBuddyApp');

module.controller('ModalInstanceCtrl', function ($scope, $modalInstance, talk) {
    $scope.talk = talk;

    $scope.ok = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.toMyTrack = function() {
        $modalInstance.close($scope.talk);
    };

});

module.controller('BytrackCtrl', function ($scope, $modal, $log) {
        $scope.tracks = [
            {
                name: 'Gutenbergsaal 3',
                id: 1,
                talks: [
                    {
                        name: 'Moderne Android Builds mit Gradle',
                        block: '9:45 - 11:00',
                        description: 'Das neue Android Build System ist angetreten, um viele Probleme der Vergangenheit zu lösen. Mit Gradle als Basis, einem komplexen Plug-in speziell für Android und einer tiefen Integration in Android Studio kann man schon von einem "Next Generation" Build-System sprechen. Doch was bringt die neue Technologie in der Praxis, und wie kann ich die neuen Features in meinem Entwicklungsprozess nutzen? Wir zeigen praktische Beispiele vom Projekt-Setup über den Build-Prozess und Konfigurationsmanagement bis hin zur CI-Integration sowie Erfahrungen mit der Migration existierender Projekte.',
                        track: 1,
                        referent: 'Dominik Helleberg'
                    },
                    {
                        name: 'Der Store ist das Ziel',
                        block: '11:45 - 12:45',
                        description: 'Das Ziel eines jeden App-Entwicklers ist in der Android-Welt der Google Play Store. Doch der Weg bis dahin ist nicht leicht und so kommen natürlich viele Fragen auf: Wie fängt man an, was benötigt man, wo findet man Informationen, und wie kommt die App dann endlich in den Store? In dieser Session wird die App-Entwicklung von der Idee bis hin zum Veröffentlichen im Schnelldurchlauf dargestellt. Es werden verschiedene Features des Play Stores, beispielsweise die Alpha- und Beta-Tests, vorgestellt. Zusätzlich ist die Session mit hilfreichen Anekdoten und „lessons learned“ gespickt und verweist an vielen Stellen auf Fremdbibliotheken, die den Android-Entwickleralltag erleichtern.',
                        track: 1,
                        referent: 'Adam Giemza'
                    },
                    {
                        name: 'Mobile meets Enterprise',
                        block: '15:00 - 16:00',
                        description: 'Mobile Clients werden zunehmend für Unternehmen interessant, die sich bisher nur auf Enterprise Computing fokussiert haben. Insbesondere, wenn mobile Clients einen Mehrwert gegenüber klassischen Enterprise-Anwendungen bieten, rentiert sich eine Investition in mobile Technologien. Mehrwert kann vor allem dadurch erzeugt werden, dass Daten zwischen Client und Server ausgetauscht werden. Die Session zeigt am Beispiel der Kommunikation eines Android-Clients mit einem Java-EE-Server, wie eine solche Kommunikation realisiert werden kann, und stellt dabei Best Practices und Fallstricke sowie deren Lösung vor.',
                        track: 1,
                        referent: 'Arne Limburg'
                    }
                ]
            },
            {
                name: 'Goldsaal C',
                id: 2,
                talks: [
                    {
                        name: 'Lean Business Development',
                        block: '9:45 - 11:00',
                        description: 'Business Development lebt von der Geschwindigkeit, mit der neue Ideen in den Markt gebracht und monetarisiert werden könnnen. Immer seltener treffen die Prognosen zu oder werden die Erwartungen übertroffen. Vielmehr werden Produkte mit hohem Aufwand und geringer Treffsicherheit entwickelt. Die Unberechenbarkeit der Kunden und die Komplexität des Marktes erschweren es dem Business Developer, erfolgreiche Geschäftsmodelle aufzubauen. Wie man diese Komplexität bewältigen kann und wie man sich regelmäßig an die Gegebenheiten des Markts anpassen kann, zeigt Lean Business Development. Agile Ansätze können im Business Development dazu führen, dass neue Produkte schnell und zielsicher in die Märkte gebracht werden können: Adaption statt Frustration lautet das Credo.Der Vortrag illustriert, wie sich Business Development wandeln muss, um erfolgreich ein neues Geschäft aufzubauen, und welche Rolle eine oftmals schwergewichtige IT dabei spielt.',
                        track: 2,
                        referent: 'Dietmar Matzke'
                    },
                    {
                        name: 'Agile Enterprise War Stories',
                        block: '11:45 - 12:45',
                        description: 'Redet man über Agilität in großen Unternehmen, geht es häufig darum wie man die Prozesse und Methoden skaliert, damit Teams effektiv an Produkten arbeiten können. Das ist aber nur ein Aspekt ... und wahrscheinlich der Leichtere. Im Gegensatz zu kleinen Unternehmen zeichnen sich große Organisationen nämlich durch starke Strukturen und eine gesetzte Kultur aus, die es gilt zu berücksichtigen, will man nachhaltig ein agiles Mindset etablieren.Der Vortrag ist eine Sammlung von War Stories, also bildhaften Geschichten, die den Weg der E-POST seit der Einführung agiler Methoden Ende 2012 beschreiben. Wer also schon immer einmal wissen wollte, wie Agilität und eine IT-Revision zusammenpassen, ist hier bestens aufgehoben.',
                        track: 2,
                        referent: 'André Neubauer'
                    },
                    {
                        name: 'Lhotse - Manager and Nerds',
                        block: '15:00 - 16:00',
                        description: 'Wir haben bei otto.de einfach mal die gesamte E-Commerce-Plattform ausgetauscht. 2 Jahre Projekt, 3 Monate schneller als geplant fertig, 7 agile Teams, 17 vertikale Systeme. In Budget, in Quality, before Time... Darüber erzähle ich ein bißchen. Dann über die Bewältigung meiner Identitätskrise als Manager in einer agilen Organisation und über Nerds und nerdiges. Dahinter steht für mich immer mehr die Frage, warum wir das eigentlich alles machen. Hier einige Stichworte:...',
                        track: 2,
                        referent: 'Johannes Mainusch'
                    }
                ]

            }
        ];
        var isTouchDevice = !!("ontouchstart" in window);

        var currentTrackId = 0;
        var updateTrack = function () {
            $scope.currentTrack = $scope.tracks[currentTrackId];
        };

        updateTrack();

        $scope.nextTrack = function () {
            currentTrackId += 1;
            updateTrack();
        };

        $scope.hasNextTrack = function () {
            return $scope.tracks.length > currentTrackId + 1;
        };

        $scope.previousTrack = function () {
            currentTrackId -= 1;
            updateTrack();
        };

        $scope.hasPreviousTrack = function () {
            return currentTrackId > 0;
        };

        $scope.showTalkDetails = function (talk) {
            if (!isTouchDevice) {
                alert('isTouchDevice');
                return;
            }

            var modalInstance = $modal.open({
                templateUrl: 'myModalContent.html',
                controller: 'ModalInstanceCtrl',
                resolve: {
                    talk: function () {
                        return talk;
                    }
                }
            });

            modalInstance.result.then(function (talk) {
                $log.info('Modal ok with talk: ' + talk.name);
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };
    });

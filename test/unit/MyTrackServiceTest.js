'use strict';

describe('Controller: MyTrackService', function() {

    var myTrackService, httpBackend, myTrackJson, conference;

    conference = {
        'id': 1,
        'name': 'Tech4',
        'location': 'Stade de Suisse Bern',
        'date': '17.11.2014',
        'speakers': null,
        'talks': [
            {
                'id': 'pause',
                'title': '*** PAUSE ***',
                'abstract': '',
                'speakerIds': [],
                'common': true
            },
            {
                'id': 'keynote',
                'title': 'Keynote - SBB IT - Mehr Wert für den Kunden',
                'abstract': 'Zugang zu Informations- und Kommunikationstechnologien bedeutet für Menschen mit Behinderungen mehr als für alle anderen. Der barrierefreie Zugang ermöglicht mehr Chancengleichheit und Selbstständigkeit im privaten und beruflichen Leben. Im ersten Teil der Keynote wird aufgezeigt, für was das Konzept Accessibility steht, wer alles davon profitiert, welche unterschiedlichen Bedürfnisse es gibt und wie diese durch assistierende Technologien unterstützt werden können. Im zweiten Teil wird live vorgestellt, was es heisst „blind“ einen Computer oder ein iPhone zu bedienen und wie Websites und Apps benutzt werden können oder wo teils unüberwindbare Hürden auftauchen. Am Schluss werden häufige Stolpersteine bei der Umsetzung von Rich Internet Applications in Websites durch den Einsatz von JS und AJAX aufgezeigt.',
                'speakerIds': [1],
                'common': true
            },
            {
                'id': 'a1',
                'title': 'A1 – UX@SBB',
                'abstract': 'Die Themen Usability und User Experience (UX) stellen eine zentrale Herausforderung dar, und zwar sowohl bei kommerziellen Anwendungen wie auch bei spezifischen Geschäftsapplikationen. Grosskonzerne wie Apple haben mit ihren Produkten neue Standards geschaffen worden, die erlebnisreiche Erfahrungen auslösen und die Ansprüche der Kunden und Anwender im Hinblick auf Bedienung und Ästhetik signifikant erhöht haben. Diese Erfahrungen und Erwartungen tragen sie auch in ihren beruflichen Kontext hinein. Selbst von betrieblichen Anwendungen erwarten sie heute ein modernes, einfaches und ansprechendes User Interface, wie sie von aktuellen Web- und Mobile Applikationen gewohnt sind. Bei der Entwicklung oder der Beschaffung von immer komplexeren Produkten und immer umfassenderen Software-Lösungen wird die Einbeziehung der Endkunden und der Endanwender immer wichtiger bzw. unabdingbar, und zwar so früh wie möglich und im gesamten Entwicklungsprozess. Seit Beginn 2014 widmet sich eine zentrale Einheit diesem Themenfeld. Das aus Informatikern, Psychologen, Ingenieuren, Designern und Ökonomen bestehende Team verfolgt die Vision, User Centered Design innerhalb der SBB-IT zu verankern und somit eine wesentlichen Beitrag zur Kunden- und Mitarbeiterzufriedenheit zu leisten. In der Präsentation stellt sich das neue UX-Team vor. Es zeigt auf was seine Dienstleistungen sind und was es mit diesen erreichen will.',
                'speakerIds': [2, 3],
                'common': false
            },
            {
                'id': 'a2',
                'title': 'A2 - ePOS, Nutzen für den Kunden',
                'abstract': 'Die SBB ersetzt zwischen 2014 und 2015 schweizweit 1‘000 der alten Billettautomaten BATS (1. Generation Touch Screen Automaten) durch Automaten der neusten Generation – dem ePOS. Unsere Kunden profitieren von zahlreichen Verbesserungen: Das Bezahlen mit grossen Noten wird möglich, da das Rückgeld neu auch in Noten erfolgt. Daneben ermöglicht der neue Automat kontaktloses Bezahlen mit Kreditkarten und erfüllt die Anforderungen des Behindertengesetzes. Durch den Einbau eines zweiten Druckers, dem erhöhten Aufbruchschutz und der Notenausgabe reduziert sich die Störungsanfälligkeit und somit die Verfügbarkeit der Automaten für unsere Kundinnen und Kunden. Aber auch unser Servicepersonal, welches tagtäglich den Unterhalt sicherstellt, kann sich freuen: Komponentenwechsel sind ohne Werkzeug möglich.',
                'speakerIds': [8, 9],
                'common': false
            },
            {
                'id': 'b1',
                'title': 'B1 - Open Data',
                'abstract': 'Wie komme ich von A nach B? Was man sich früher über x Kombinationsmöglichkeiten mühsam von Hand aus dem Kursbuch heraussuchen musste ist heute per Knopfdruck verfügbar. Diese Information will nicht nur die SBB den Schweizerinnen und Schweizer zur Verfügung stellen. Neben Big Players wie Google und Amadeus wollen auch Open Source Projekte, KMU’s und Privatpersonen eine qualitativ hochwertige Reiseinformation anbieten. Intermodaler Routenplaner, Tür zu Tür Navigation, jeder Anbieter will sich durch Zusatznutzen beim Kunden profilieren. Was sind die innovativsten/kreativsten Umsetzungen auf Basis der ÖV Information und wie positioniert sich die SBB in diesem umkämpften Markt? Welche SBB-Informationen interessieren die Open Data Community nebst dem Fahrplan und wie geht die SBB mit diesen Anfragen um. ',
                'speakerIds': [4],
                'common': false
            },
            {
                'id': 'b2',
                'title': 'B2 - Innovative Kundenlenkung',
                'abstract': 'Die Systeme der SBB sind hochgradig vernetzt und erfassen eine Vielzahl von Daten. Im ersten Schritt der Kundeninformation hat man den Automatisierungsgrad für die Kundeninformation ausgebaut um die visuellen und akustischen Ausgaben zentral in Echtzeit steuern zu können. Im zweiten Schritt wird ein weiterer Teil der bestehenden Daten verwendet, um die Kundeninformationen für die Kundenlenkung anzureichern. Konkret soll die Auslastung der Personenwagen in Echtzeit mit den heutigen Systemen ermittelt werden. Hierbei werden unterschiedliche Messwerte analysiert und mittels selbstlernender Algorithmen aus der künstlichen Intelligenz trainiert. Ziel ist es auf dieser Basis dann die Auslastung in Echtzeit zu kennen um z.B. im Ereignisfall abschätzen zu können, wie viele Reisende betroffen sind um die benötigten Ersatzbusse aufzubieten.',
                'speakerIds': [10, 11],
                'common': false
            }
        ],
        'tracks': [
            {
                'id': 'kunden',
                'title': 'Mehr Wert für die Kunden',
                'presentations': [
                    {
                        'id': 5,
                        'talkId': 'keynote',
                        'time': {
                            'start': '09:00',
                            'end': '09:45'
                        },
                        'room': 'Saal 1'
                    },
                    {
                        'id': 15,
                        'talkId': 'a1',
                        'time': {
                            'start': '10:45',
                            'end': '11:30'
                        },
                        'room': 'Saal Mailand'
                    },
                    {
                        'id': 20,
                        'talkId': 'pause',
                        'time': {
                            'start': '11:30',
                            'end': '11:45'
                        },
                        'room': ''
                    },
                    {
                        'id': 25,
                        'talkId': 'a2',
                        'time': {
                            'start': '11:45',
                            'end': '12:30'
                        },
                        'room': 'Saal Rom'
                    }
                ]
            },
            {
                'id': 'innovation',
                'title': 'Innovative (IT-) Projekte aus dem Business / Was bewegt das Bahnhofsgeschäft',
                'presentations': [
                    {
                        'id': 6,
                        'talkId': 'keynote',
                        'time': {
                            'start': '09:00',
                            'end': '09:45'
                        },
                        'room': 'Saal 1'
                    },
                    {
                        'id': 16,
                        'talkId': 'b1',
                        'time': {
                            'start': '10:45',
                            'end': '11:30'
                        },
                        'room': 'Saal Lausanne'
                    },
                    {
                        'id': 21,
                        'talkId': 'pause',
                        'time': {
                            'start': '11:30',
                            'end': '11:45'
                        },
                        'room': ''
                    },
                    {
                        'id': 26,
                        'talkId': 'b2',
                        'time': {
                            'start': '11:45',
                            'end': '12:30'
                        },
                        'room': 'Saal Genf'
                    }
                ]
            }
        ]
    };

    // load the controller's module
    beforeEach(function() {
        angular.mock.module('conferenceBuddyApp', function($provide) {
            $provide.constant('REST_URL', '/api-mock');
        });
        angular.mock.inject(function(_MyTrackService_, $httpBackend) {
            myTrackService = _MyTrackService_;
            httpBackend = $httpBackend;
        });

        myTrackJson = [
            15, 16
        ];

        httpBackend.whenGET('/api-mock/mytrack').respond(myTrackJson);
    });

    afterEach(function() {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should fetch JSON on load ', function() {
        var myTrack = loadJsonMock();
        expect(myTrack).toBeDefined();
        expect(myTrack).toEqual(myTrackJson);
    });

    it('should create MyTrack correct', function() {
        var myTrack = loadJsonMock();

        var result = myTrackService.createMyTrack(conference, myTrack);

        expect(result.length).toBe(conference.tracks[0].presentations.length);
        expect(result[0].talkId).toBe('keynote');
        expect(result[1].talkId).toBe('a1');
        expect(result[2].talkId).toBe('pause');
        expect(result[3].talkId).toBe('b1');
    });

    function loadJsonMock() {
        httpBackend.expectGET('/api-mock/mytrack');
        var myTrack;
        myTrackService.load().then(function(result) {
            myTrack = result;
        });
        httpBackend.flush();
        return myTrack;
    }
});
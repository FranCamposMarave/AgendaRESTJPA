/**
 * Created by oscar on 13/11/14.
 */

(function() {
    var agendaApp = angular.module('agendaApp', []);
    agendaApp.baseURI = 'http://localhost:8080/Agenda/personas/';

    agendaApp.controller('ContactosCtrl', ['$scope', 'AgendaService', function ($scope, AgendaService) {
        var self = this;
        $scope.contacts = AgendaService.retrieveAll()
            .success(function(data){
                $scope.contacts = data.persona;
            });

        self.create = function (nombre, apellidos, nif) {
            AgendaService.create(nombre, apellidos, nif)
                .success(function (data) {
                    AgendaService.retrieveAll()
                        .success(function (data) {
                            $scope.contacts = data.persona;
                    });
                });
        };

        self.delete = function (nif) {
            AgendaService.delete(nif)
                .success(function (data) {
                    AgendaService.retrieveAll()
                        .success(function (data) {
                            $scope.contacts = data.persona;
                    });
                });
        }

        self.retreiveContact = function(nif) {
            AgendaService.retrieveContact(nif)
                .success(function(data) {
                    console.log(data);
                    $scope.currentContact = data;
                });
        }

        self.update = function(nombre, apellidos, nif) {
            AgendaService.update(nombre, apellidos, nif)
                .success(function(data) {
                    AgendaService.retrieveAll()
                        .success(function (data) {
                            $scope.contacts = data.persona;
                        });
                });
        };

    }]);

    agendaApp.service('AgendaService', ['$http', function($http) {
        this.create = function(nombre, apellidos, nif) {
            dato = {'persona': {'nombre': nombre, 'apellidos': apellidos, 'nif': nif}};
            var url = agendaApp.baseURI + nif;
            return $http.put(url, dato);
        }

        this.retrieveAll = function() {
            return $http.get(agendaApp.baseURI);
        }

        this.retrieveContact = function(nif) {
            var url = agendaApp.baseURI + nif;
            return $http.get(url);
        }

        this.delete = function(nif) {
            var url = agendaApp.baseURI + nif;
            var dato = {'nif': nif}
            return $http.delete(url, dato);
        }

        this.update = function (persona) {
            var url = agendaApp.baseURI + persona.persona.nif;
            return $http.put(url, persona);
        }
    }]);

})();
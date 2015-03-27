/**
 * Created by oscar on 13/11/14.
 */

(function() {
    var agendaApp = angular.module('agendaApp', []);

    agendaApp.controller('ContactosCtrl', function ($scope, $http) {
        $http.get('http://localhost:8080/Agenda/personas').success(function (data) {
            $scope.contacts = data.persona;
        });

        $scope.coso = function (nombre, apellidos, nif) {
            dato = {'persona': {'nombre': nombre, 'apellidos': apellidos, 'nif': nif}};
            var url = 'http://localhost:8080/Agenda/personas/' + nif;
            $http.put(url, dato).
                success(function (data) {
                    $http.get('http://localhost:8080/Agenda/personas').success(function (data) {
                        $scope.contacts = data.persona;
                    });
                });
        };

        $scope.borrar = function (nif) {
            var url = 'http://localhost:8080/Agenda/personas/' + nif;
            var dato = {'nif': nif}
            $http.delete(url, dato).
                success(function (data) {
                    $http.get('http://localhost:8080/Agenda/personas').success(function (data) {
                        $scope.contacts = data.persona;
                    });
                });
        }
    });
})();
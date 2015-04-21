/**
 * Created by oscar on 13/11/14.
 */

var app = angular.module('controllers',
    ['rest', 'ui.bootstrap', 'angularFileUpload','angular-loading-bar', 'ngAnimate', 'toastr']);

app.controller('headerCtrl', ['$scope', '$location',
    function($scope, $location){
        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
    }
]);

app.controller('homeCtrl', ['$scope',
    function($scope){
        $scope.helloWorld = "We are going to change the world";
    }
]);

app.controller('activitiesCtrl', ['$scope', 'ActivityService',
    function($scope, ActivityService){
        $scope.retrieveAll = function () {
            ActivityService.retrieveAll()
            .success(function(data) {
                $scope.activities = data.activity;
                console.log("Retrieve activities (count): " + $scope.activities.length);
            })
            .error(function(data, status, headers, config) {
                console.log("Error deleting activity. Error code: " + status );
                if ( status == 404 ){
                    toastr.error('No existe la actividad', 'Borrar');
                }else if ( status == 500 ){
                    toastr.error('Error interno del servidor', 'Borrar');
                }else{
                    toastr.error('Error en la conexión al servidor', 'Borrar');
                }
            });
        };
        $scope.retrieveAll();
    }
]);
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
// Create the module
var app = angular.module('app', ['ngRoute', 'app']);

// Config routes
app.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl	: 'templates/home.html',
			controller 	: 'homeController'
		})
		.when('/activity/:activityId', {
			templateUrl : 'templates/activity.html',
			controller 	: 'activityController'
		})
		.otherwise({
			redirectTo: '/'
		});
});

app.controller('homeController', ['$scope', '$http',
    function ($scope, $http) {
        $http.get('http://localhost:8080/naturAdventure/activities').success(function (data) {
            $scope.activities = data.activity;
        });
    }
]);


app.controller('activityController', ['$scope', '$http', '$routeParams',
    function ($scope, $http, $routeParams) {
        var url = 'http://localhost:8080/naturAdventure/activities/' + $routeParams.id;
        $http.get( url ).success(function (data) {
            $scope.activity = data.activity;
        });
    }
]);
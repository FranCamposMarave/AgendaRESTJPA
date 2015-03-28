
// Create the module
var app = angular.module('app', ['ngRoute', 'app', 'ui.bootstrap']);

// Config routes
app.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl	: 'templates/home.html',
			controller 	: 'homeCtrl'
		})
		.when('/activity/:activityId', {
			templateUrl : 'templates/activity.html',
			controller 	: 'activityCtrl'
		})
		.otherwise({
			redirectTo: '/'
		});
});

app.controller('homeCtrl', ['$scope', '$http',
    function ($scope, $http) {
        $http.get('http://localhost:8080/naturAdventure/activities').success(function (data) {
            $scope.activities = data.activity;
        });
    }
]);


app.controller('activityCtrl', ['$scope', '$http', '$routeParams',
    function ($scope, $http, $routeParams) {
        var url = 'http://localhost:8080/naturAdventure/activities/' + $routeParams.id;
        $http.get( url ).success(function (data) {
            $scope.activity = data.activity;
        });
    }
]);

app.controller('DropdownCtrl', function ($scope, $log) {
      $scope.items = [
        {link:'1', name:'Piragüismo'},
        {link:'2', name:'Tiro con arco'}
      ];

      $scope.toggleDropdown = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.status.isopen = !$scope.status.isopen;
      };
    });
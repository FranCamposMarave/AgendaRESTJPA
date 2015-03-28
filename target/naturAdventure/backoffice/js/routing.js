
// Create the module
var app = angular.module('app', ['ngRoute', 'app', 'ui.bootstrap']);

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

app.controller('DropdownController', function ($scope, $log) {
      $scope.items = [
        ['services/piragüismo', 'Piragüismo'],
        ['services/Tiro con arco', 'Tiro con arco'],
        ['services/Espelología', 'Espelología']
      ];

      $scope.toggleDropdown = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.status.isopen = !$scope.status.isopen;
      };
    });
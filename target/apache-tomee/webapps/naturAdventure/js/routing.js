// Create the module
var app = angular.module('app', ['ngRoute', 'app.controllers', 'app.services']);

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


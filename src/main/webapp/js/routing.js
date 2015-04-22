
// Create the module
var app = angular.module('app',
    ['controllers','ngRoute', 'rest', 'ui.bootstrap', 'angularFileUpload','angular-loading-bar', 'ngAnimate', 'toastr']);

app.config(function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.latencyThreshold = 10;
});
// Config routes
app.config(function($routeProvider) {
	$routeProvider
		.when('/', {
            templateUrl : 'templates/home.html',
            controller 	: 'homeCtrl'
        })
        .when('/activities', {
            templateUrl : 'templates/activities.html',
            controller 	: 'activitiesCtrl'
        })
        .when('/activity/:id', {
            templateUrl : 'templates/activity.html',
            controller 	: 'activityCtrl'
        })

		.otherwise({
			redirectTo: '/'
		});
});

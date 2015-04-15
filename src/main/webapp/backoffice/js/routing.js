
// Create the module
var app = angular.module('bk-app',
    ['bk-controllers','ngRoute']);

app.config(function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.latencyThreshold = 10;
});
// Config routes
app.config(function($routeProvider) {
	$routeProvider
		.when('/backoffice', {
            templateUrl : 'backoffice/templates/home.html',
            controller 	: 'backofficeCtrl'
        })
        .when('/backoffice/activity/:id', {
            templateUrl : 'backoffice/templates/activity.html',
            controller 	: 'activityCtrl'
        })
        .when('/backoffice/activity/', {
            templateUrl : 'backoffice/templates/activity.html',
            controller 	: 'activityCtrl'
        })
		.otherwise({
			redirectTo: '/backoffice'
		});
});


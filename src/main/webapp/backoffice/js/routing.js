
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
            templateUrl : 'templates/home.html',
            controller 	: 'backofficeCtrl'
        })
        .when('/backoffice/activity/:id', {
            templateUrl : 'templates/activity.html',
            controller 	: 'activityCtrl'
        })
        .when('/backoffice/activity/', {
            templateUrl : 'templates/activity.html',
            controller 	: 'activityCtrl'
        })
        .when('/backoffice/category/', {
            templateUrl : 'templates/category.html',
            controller 	: 'categoryCtrl'
        })
		.otherwise({
			redirectTo: '/backoffice'
		});
});


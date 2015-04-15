
// Create the module
var app = angular.module('app',
    ['controllers','ngRoute', 'rest', 'ui.bootstrap', 'angularFileUpload','angular-loading-bar', 'ngAnimate', 'toastr']);

app.config(function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.latencyThreshold = 10;
});
// Config routes
app.config(function($routeProvider) {
	$routeProvider
		.when('/index', {
            templateUrl : 'templates/home.html',
            controller 	: 'homeCtrl'
        })

		.otherwise({
			redirectTo: '/index'
		});
});

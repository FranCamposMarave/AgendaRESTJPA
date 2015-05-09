// Create the module
var app = angular.module('bk-app',
    ['bk-controllers','ngRoute']);

app.config(function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.latencyThreshold = 10;
});
// Config routes
app.config(function($routeProvider) {
	$routeProvider
		.when('/', {
            templateUrl : 'templates/home.html',
            controller 	: 'backofficeCtrl'
        })
        .when('/activity/:id', {
            templateUrl : 'templates/activity.html',
            controller 	: 'activityCtrl'
        })
        .when('/activity', {
            templateUrl : 'templates/activity.html',
            controller 	: 'activityCtrl'
        })
        .when('/activities', {
            templateUrl : 'templates/activities.html',
            controller 	: 'activitiesCtrl'
        })
        .when('/category', {
            templateUrl : 'templates/category.html',
            controller 	: 'categoryCtrl'
        })
        .when('/category/:id', {
            templateUrl : 'templates/category.html',
            controller 	: 'categoryCtrl'
        })
        .when('/categories', {
            templateUrl : 'templates/categories.html',
            controller 	: 'categoriesCtrl'
        })
        .when('/monitor', {
            templateUrl : 'templates/monitor.html',
            controller 	: 'monitorCtrl'
        })
        .when('/monitors', {
            templateUrl : 'templates/monitors.html',
            controller 	: 'monitorsCtrl'
        })
        .when('/monitor/:id', {
            templateUrl : 'templates/monitor.html',
            controller 	: 'monitorCtrl'
        })
        .when('/assignMonitors', {
            templateUrl : 'templates/assignMonitors.html',
            controller 	: 'activitiesCtrl'
        })
        .when('/users', {
            templateUrl : 'templates/users.html',
            controller 	: 'usersCtrl'
        })
        .when('/user/:id', {
            templateUrl : 'templates/user.html',
            controller 	: 'userCtrl'
        })
        .when('/user', {
            templateUrl : 'templates/user.html',
            controller 	: 'userCtrl'
        })
		.otherwise({
			redirectTo: '/'
		});
});


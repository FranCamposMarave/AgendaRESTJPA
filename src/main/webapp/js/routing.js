
// Create the module
var app = angular.module('app', ['ngRoute', 'rest', 'ui.bootstrap']);

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
		.when('/backoffice', {
            templateUrl : 'backoffice/templates/home.html',
            controller 	: 'backofficeCtrl'
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

app.controller('backofficeCtrl', ['$scope', '$modal' ,'ActivityService',
    function ($scope, $modal, ActivityService) {
        $scope.retrieveAll = function () {
            ActivityService.retrieveAll()
                .success(function(data) {
                   $scope.activities = data.activity;
                   console.log("Retrieve activities (count): " + $scope.activities.length);
            })
        };
        $scope.retrieveAll();
        $scope.retrieve = function(id) {
            alert("OK");
            ActivityService.retrieveActivity(id)
                .success(function(data) {
                   $scope.currentActivity = data.activity;
                   console.log("Retrieved activity: " + $scope.currentActivity.title);
            })
        };
        $scope.update = function(activity) {
            ActivityService.updateActivity(activity)
                .success(function(data) {
                    console.log("Activity updated");
                    $scope.retrieveAll();
                })
                .error(function(data, status, headers, config) {
                    alert("Error updating");
                    $scope.addAlert("danger", "No se ha podido actualizar la actividad.");
                });
        };
        $scope.delete = function(id) {
            ActivityService.deleteActivity(id)
                .success(function(data) {
                    console.log("Activity deleted");
                    $scope.retrieveAll();
                });
        };
        $scope.openModal = function (act) {
            var modalInstance = $modal.open({
                templateUrl: 'activityModalContent.html',
                controller: 'activityModalCtrl',
                resolve: {
                    'activity': function () {
                     return angular.copy(act);;
                    }
                }
            });

            modalInstance.result.then(function (activity) {
                ActivityService.updateActivity(activity)
                    .success(function(data) {
                        console.log("Actividad actualizada");
                });
            });
        };
        $scope.alerts = [
        ];

        $scope.addAlert = function(messageType, messageContent) {
            $scope.alerts.push({type:messageType, msg: messageContent});
        };

        $scope.closeAlert = function(index) {
            $scope.alerts.splice(index, 1);
        };
    }
]);

app.controller('activityModalCtrl', function ($scope, $modalInstance, activity) {

    $scope.activity = activity;

    $scope.modalOk = function () {
        $modalInstance.close($scope.activity);
    };

    $scope.modalCancel = function () {
        $modalInstance.dismiss('cancel');
     };

    $scope.today = function() {
        $scope.activity.date = new Date();
    };

     $scope.clear = function () {
         $scope.activity.date = null;
     };

    // Disable weekend selection
    $scope.disabled = function(date, mode) {
        return ( false );
    };

    $scope.toggleMin = function() {
        $scope.minDate = $scope.minDate ? null : new Date();
    };
    $scope.toggleMin();

    $scope.dateOptions = {
        formatDay: 'dd',
        formatYear: 'mm',
        formatYear: 'yyyy',
        startingDay: 1
    };
    $scope.open = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.opened = true;
    };
});


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
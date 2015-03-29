
// Create the module
var app = angular.module('app', ['ngRoute', 'rest', 'ui.bootstrap', 'angularFileUpload']);

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

app.controller('backofficeCtrl', ['$scope', '$modal' ,'ActivityService', 'FileUploader',
    function ($scope, $modal, ActivityService, FileUploader) {
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

        $scope.openActivityModal = function (act) {
            var modalInstance = $modal.open({
                templateUrl: 'activityModalContent.html',
                controller: 'activityModalCtrl',
                resolve: {
                    'activity': function () {
                        return angular.copy(act);
                    },
                    'fileUploader' : function () {
                        return FileUploader;
                    },
                }
            });
            if ( act ){
                modalInstance.result.then(function (activity) {
                    ActivityService.updateActivity(activity)
                        .success(function(data) {
                            console.log("Activity updated");
                            $scope.retrieveAll();
                    });
                });
            }else{
                modalInstance.result.then(function (activity) {
                    ActivityService.addActivity(activity)
                        .success(function(data) {
                            console.log("Activity created");
                            $scope.retrieveAll();
                    });
                });
            }
        };

        $scope.openConfirmationModal = function (act) {
            var modalInstance = $modal.open({
                templateUrl: 'confirmationModalContent.html',
                controller: 'confirmationModalCtrl',
                resolve: {
                    'activity': function () {
                        return act;
                    },
                }
            });

            modalInstance.result.then(function (activity) {
                ActivityService.deleteActivity(activity.id)
                    .success(function(data) {
                        console.log("Activity deleted");
                        $scope.retrieveAll();
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
        $scope.file = null;
        $scope.uploadImage = function(){

        }
    }
]);

app.controller('activityModalCtrl', function ($scope, $modalInstance, activity, FileUploader) {

    if ( activity ){
        $scope.activity = activity;
        $scope.action = "Editar";
    }else{
        $scope.action = "Crear";
        $scope.activity = {};
    }

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

    var uploader = $scope.uploader = new FileUploader({
        url: 'http://localhost:8080/naturAdventure/activities/image'
    });

    // CALLBACKS
    uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
        console.info('onWhenAddingFileFailed', item, filter, options);
    };
    uploader.onAfterAddingFile = function(fileItem) {
        this.uploadAll();
        console.info('onAfterAddingFile', fileItem);
    };
    uploader.onBeforeUploadItem = function(item) {
        console.info('onBeforeUploadItem', item);
    };
    uploader.onProgressItem = function(fileItem, progress) {
        console.info('onProgressItem', fileItem, progress);
    };
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        $scope.activity.picture = response;
        console.info('onSuccessItem', fileItem, response, status, headers);
    };
    uploader.onErrorItem = function(fileItem, response, status, headers) {
        console.info('onErrorItem', fileItem, response, status, headers);
    };

    console.info('uploader', uploader);
});

app.controller('confirmationModalCtrl', function ($scope, $modalInstance, activity) {

    $scope.activity = activity;

    $scope.modalOk = function (activity) {
        $modalInstance.close($scope.activity);
    };

    $scope.modalCancel = function () {
        $modalInstance.dismiss('cancel');
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
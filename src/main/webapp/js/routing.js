
// Create the module
var app = angular.module('app',
    ['ngRoute', 'rest', 'ui.bootstrap', 'angularFileUpload','angular-loading-bar', 'ngAnimate', 'toastr']);

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

app.controller('headerCtrl', ['$scope', '$location',
    function($scope, $location){
        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
    }
]);

app.controller('homeCtrl', ['$scope', '$http',
    function ($scope, $http) {
        $http.get('http://localhost:8080/naturAdventure/activities').success(function (data) {
            $scope.activities = data.activity;
        });
    }
]);


app.controller('backofficeCtrl', ['$scope', '$modal' ,'ActivityService', 'FileUploader', 'toastr',
    function ($scope, $modal, ActivityService, FileUploader, toastr) {
        $scope.retrieveAll = function () {
            ActivityService.retrieveAll()
                .success(function(data) {
                   $scope.activities = data.activity;
                   console.log("Retrieve activities (count): " + $scope.activities.length);
            })
        };
        $scope.retrieveAll();
        $scope.retrieve = function(id) {
            ActivityService.retrieveActivity(id)
                .success(function(data) {
                   $scope.currentActivity = data.activity;
                   console.log("Retrieved activity: " + $scope.currentActivity.title);
            })
        };
        $scope.delete = function(id) {
            ActivityService.deleteActivity(id)
                .success(function(data) {
                    console.log("Activity deleted");
                    $scope.retrieveAll();
                });
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
                        toastr.success('La actividad ha sido borrada', 'Borrar');
                        console.log("Activity deleted");
                        $scope.retrieveAll();
                    }).error(function(data, status, headers, config) {
                      console.log("Error deleting activity");
                      if ( status == 404 ){
                        toastr.error('No existe la actividad', 'Borrar');
                      }else{
                        toastr.error('Error en la conexión al servidor', 'Borrar');
                      }
                  });;
            });
        };

        $scope.file = null;
        $scope.uploadImage = function(){

        }
    }
]);

app.controller('activityCtrl', function ($scope, $routeParams, FileUploader, ActivityService, $location, toastr) {

    if ( $routeParams.id ){
        $scope.action = "Editar";
        ActivityService.retrieveActivity($routeParams.id)
            .success(function(data) {
               $scope.activity = data.activity;
               console.log("Retrieved activity: " + $scope.activity.title);
        })
    }else{
        $scope.action = "Crear";
        $scope.activity = {};
    }

    $scope.accept = function () {
        if ( $scope.action == 'Crear' ){
            ActivityService.addActivity($scope.activity)
                .success(function(data) {
                    console.log("Activity added");
                    toastr.success('La actividad ha sido añadida!', 'Añadir');
                }).error(function(data, status, headers, config) {
                    console.log("Error adding activity");
                    toastr.error('Error en la conexión al servidor', 'Añadir');
                });
        }else{
            ActivityService.updateActivity($scope.activity)
                .success(function(data) {
                    console.log("Activity updated");
                    toastr.success('La actividad ha sido actualizada!', 'Actualizar');
                }).error(function(data, status, headers, config) {
                    console.log("Error updating activity");
                    if ( status == 400 ){
                        toastr.error('La actividad a modificar no existe', 'Actualizar');
                    }else{
                        toastr.error('Error en la conexión al servidor', 'Actualizar');
                    }
                });
        }
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
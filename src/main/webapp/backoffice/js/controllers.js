/**
 * Created by oscar on 13/11/14.
 */

var app = angular.module('bk-controllers',
    ['rest', 'ui.bootstrap', 'angularFileUpload','angular-loading-bar', 'ngAnimate', 'toastr']);


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


app.controller('backofficeCtrl', ['$scope', '$rootScope', '$timeout', '$modal' ,'ActivityService', 'FileUploader', 'toastr',
    function ($scope, $rootScope, $timeout, $modal, ActivityService, FileUploader, toastr) {
        $scope.retrieveAll = function () {
            ActivityService.retrieveAll()
                .success(function(data) {
                   $scope.activities = data.activity;
                   console.log("Retrieve activities (count): " + $scope.activities[0].title);
            });
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
                    }
                }
            });

            modalInstance.result.then(function (activity) {
                ActivityService.deleteActivity(activity.id)
                    .success(function(data) {
                        toastr.success('La actividad ha sido borrada', 'Borrar');
                        console.log("Activity deleted");
                        $scope.retrieveAll();
                    }).error(function(data, status, headers, config) {
                      console.log("Error deleting activity. Error code: " + status );
                      if ( status == 404 ){
                        toastr.error('No existe la actividad', 'Borrar');
                      }else if ( status == 500 ){
                        toastr.error('Error interno del servidor', 'Borrar');
                      }else{
                        toastr.error('Error en la conexión al servidor', 'Borrar');
                      }
                  });
            });
        };

        $scope.file = null;
        $scope.uploadImage = function(){
        }

        $rootScope.$on('toastMessage', function(event, toast){
            toast();
            event.preventDefault();
        });
    }
]);


app.controller('activitiesCtrl', ['$scope', '$rootScope', '$timeout', '$modal' ,'ActivityService', 'FileUploader', 'toastr',
    function ($scope, $rootScope, $timeout, $modal, ActivityService, FileUploader, toastr) {
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
                    }
                }
            });

            modalInstance.result.then(function (activity) {
                ActivityService.deleteActivity(activity.id)
                    .success(function(data) {
                        toastr.success('La actividad ha sido borrada', 'Borrar');
                        console.log("Activity deleted");
                        $scope.retrieveAll();
                    }).error(function(data, status, headers, config) {
                      console.log("Error deleting activity. Error code: " + status );
                      if ( status == 404 ){
                        toastr.error('No existe la actividad', 'Borrar');
                      }else if ( status == 500 ){
                        toastr.error('Error interno del servidor', 'Borrar');
                      }else{
                        toastr.error('Error en la conexión al servidor', 'Borrar');
                      }
                  });
            });
        };

        $scope.file = null;
        $scope.uploadImage = function(){
        }

        $rootScope.$on('toastMessage', function(event, toast){
            $timeout( toast, 1000 );
        });
    }
]);

app.controller('categoriesCtrl', ['$scope', '$rootScope', '$timeout', '$modal' ,'CategoryService', 'FileUploader', 'toastr',
    function ($scope, $rootScope, $timeout, $modal, CategoryService, FileUploader, toastr) {
        $scope.retrieveAll = function () {
            CategoryService.retrieveAll()
                .success(function(data) {
                    $scope.categories = data.category;
                    console.log("Retrieve categories (count): " + $scope.categories.length);
                })
        };
        $scope.retrieveAll();
        $scope.retrieve = function(id) {
            CategoryService.retrieveCategory(id)
                .success(function(data) {
                    $scope.currentCategory = data.category;
                    console.log("Retrieved category: " + $scope.currentCategory.title);
                })
        };
        $scope.delete = function(id) {
            CategoryService.deleteCategory(id)
                .success(function(data) {
                    console.log("Category deleted");
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
                    }
                }
            });

            modalInstance.result.then(function (category) {
                CategoryService.deleteCategory(category.id)
                    .success(function(data) {
                        toastr.success('La categoria ha sido borrada', 'Borrar');
                        console.log("Category deleted");
                        $scope.retrieveAll();
                    }).error(function(data, status, headers, config) {
                        console.log("Error deleting category. Error code: " + status );
                        if ( status == 404 ){
                            toastr.error('No existe la categoria', 'Borrar');
                        }else if ( status == 500 ){
                            toastr.error('Error interno del servidor', 'Borrar');
                        }else{
                            toastr.error('Error en la conexión al servidor', 'Borrar');
                        }
                    });
            });
        };

        $scope.file = null;
        $scope.uploadImage = function(){
        }

        $rootScope.$on('toastMessage', function(event, toast){
            $timeout( toast, 1000 );
        });
    }
]);



app.controller('activityCtrl', function ($scope, $rootScope, $routeParams, FileUploader, ActivityService, CategoryService, $location, toastr) {

    CategoryService.retrieveAll()
    .success(function(data) {
        $scope.categories = data.category;
        console.log("Retrieve categories (count): " + $scope.categories.length);
    });
    if ( $routeParams.id ){
        $scope.action = "Editar";
        ActivityService.retrieveActivity($routeParams.id)
        .success(function(data) {
           $scope.activity = data.activity;
           console.log("Retrieved activity: " + $scope.activity.title);
        });
    }else{
        $scope.action = "Crear";
        $scope.activity = {};
    }

    $scope.remainingChars = { "title" : 255, "description" : 255 };
    $scope.$watch(
        function(scope){ return scope.activity.title },
        function(newValue, oldValue){
            $scope.remainingChars.title = 255 - newValue.length;
        }
    );
    $scope.$watch(
        function(scope){ return scope.activity.description },
        function(newValue, oldValue){
            $scope.remainingChars.description = 255 - newValue.length;
        }
    );

    $scope.submit = function () {
        if ( $scope.action == 'Crear' ){
            ActivityService.addActivity($scope.activity)
            .success(function(data) {
                console.log("Activity added");
                $rootScope.$broadcast('toastMessage', function(){
                    toastr.success('La actividad ha sido añadida!', 'Añadir');
                });
                $location.path('activities');
                $scope.activityForm.$submitted = true;
            }).error(function(data, status, headers, config) {
                if ( status == 500 ){
                    toastr.error('Error interno del servidor', 'Actualizar');
                }else{
                    console.log("Error adding activity. Error code: " + status );
                    toastr.error('Error en la conexión al servidor', 'Añadir');
                }
            });
        }else{
            ActivityService.updateActivity($scope.activity)
            .success(function(data) {
                console.log("Activity updated");
                $rootScope.$broadcast('toastMessage', function(){
                    toastr.success(' La actividad ha sido actualizada!', 'Actualizar');
                });
                $location.path('activities');
            }).error(function(data, status, headers, config) {
                console.log("Error updating activity. Error code: " + status );
                if ( status == 400 ){
                    toastr.error('La actividad a modificar no existe', 'Actualizar');
                }else if ( status == 500 ){
                    toastr.error('Error interno del servidor', 'Actualizar');
                }else{
                    toastr.error('Error en la conexión al servidor', 'Actualizar');}
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
        toastr.warning('No se ha podido subir la foto', 'Actividad');
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

app.controller('categoryCtrl', function ($scope, $rootScope, $routeParams, CategoryService, $location, toastr) {

    if ( $routeParams.id ){
        $scope.action = "Editar";
        CategoryService.retrieveCategory($routeParams.id)
            .success(function(data) {
                $scope.category = data.category;
                console.log("Retrieved category: " + $scope.category.title);
            })
    }else{
        $scope.action = "Crear";
        $scope.category = {};
    }

    $scope.remainingChars = { "title" : 255};
    $scope.$watch(
        function(scope){ return scope.category.title },
        function(newValue, oldValue){
            $scope.remainingChars.title = 255 - newValue.length;
        }
    );

    $scope.submit = function () {

        if ( $scope.action == 'Crear' ){
            CategoryService.addCategory($scope.category)
                .success(function(data) {
                    console.log("Category added");
                    $rootScope.$broadcast('toastMessage', function(){
                        toastr.success('La categoria ha sido añadida!', 'Añadir');
                    });
                    $location.path('categories');
                    $scope.categoryForm.$submitted = true;
                }).error(function(data, status, headers, config) {
                    if ( status == 500 ){
                        toastr.error('Error interno del servidor', 'Actualizar');
                    }else{
                        console.log("Error adding category. Error code: " + status );
                        toastr.error('Error en la conexión al servidor', 'Añadir');
                    }
                });
        }else{
            CategoryService.updateCategory($scope.category)
                .success(function(data) {
                    console.log("Category updated");
                    $rootScope.$broadcast('toastMessage', function(){
                        toastr.success(' La categoría ha sido actualizada!', 'Actualizar');
                    });
                    $location.path('categories');
                }).error(function(data, status, headers, config) {
                    console.log("Error updating category. Error code: " + status );
                    if ( status == 400 ){
                        toastr.error('La categoría a modificar no existe', 'Actualizar');
                    }else if ( status == 500 ){
                        toastr.error('Error interno del servidor', 'Actualizar');
                    }else{
                        toastr.error('Error en la conexión al servidor', 'Actualizar');}
                });
        }
    };

});

app.controller('monitorCtrl', function ($scope, $rootScope, $routeParams, MonitorService, $location, toastr) {

    if ( $routeParams.id ){
        $scope.action = "Editar";
        MonitorService.retrieveMonitor($routeParams.id)
            .success(function(data) {
                $scope.monitor = data.monitor;
                console.log("Retrieved monitor: " + $scope.monitor.title);
            })
    }else{
        $scope.action = "Crear";
        $scope.monitor = {};
    }

    $scope.remainingChars = { "name" : 255};
    $scope.$watch(
        function(scope){ return scope.monitor.name },
        function(newValue, oldValue){
            $scope.remainingChars.title = 255 - newValue.length;
        }
    );

    $scope.submit = function () {
        if ( $scope.action == 'Crear' ){
            console.log("Submint monitor")
            MonitorService.addMonitor($scope.monitor)
                .success(function(data) {
                    console.log("Monitor added");
                    $rootScope.$broadcast('toastMessage', function(){
                        toastr.success('El monitor ha sido añadido!', 'Añadir');
                    });
                    $location.path('monitors');
                    $scope.monitorForm.$submitted = true;
                }).error(function(data, status, headers, config) {
                    if ( status == 500 ){
                        toastr.error('Error interno del servidor', 'Actualizar');
                    }else{
                        console.log("Error adding monitor. Error code: " + status );
                        toastr.error('Error en la conexión al servidor', 'Añadir');
                    }
                });
        }else{
            MonitorService.updateMonitor($scope.monitor)
                .success(function(data) {
                    console.log("Monitor updated");
                    $rootScope.$broadcast('toastMessage', function(){
                        toastr.success(' El monitor ha sido actualizado!', 'Actualizar');
                    });
                    $location.path('monitors');
                }).error(function(data, status, headers, config) {
                    console.log("Error updating monitor. Error code: " + status );
                    if ( status == 400 ){
                        toastr.error('El monitor a modificar no existe', 'Actualizar');
                    }else if ( status == 500 ){
                        toastr.error('Error interno del servidor', 'Actualizar');
                    }else{
                        toastr.error('Error en la conexión al servidor', 'Actualizar');}
                });
        }
    };

});


app.controller('monitorsCtrl', ['$scope', '$rootScope', '$timeout', '$modal' ,'MonitorService', 'FileUploader', 'toastr',
    function ($scope, $rootScope, $timeout, $modal, MonitorService, FileUploader, toastr) {
        $scope.retrieveAll = function () {
            MonitorService.retrieveAll()
                .success(function(data) {
                    $scope.monitors = data.monitor;
                    console.log("Retrieve monitors (count): " + $scope.monitors.length);
                })
        };

        $scope.retrieveAll();
        $scope.retrieve = function(id) {
            MonitorService.retrieve(id)
                .success(function(data) {
                    $scope.currentMonitor = data.monitor;
                    console.log("Retrieved monitor: " + $scope.currentMonitor.title);
                })
        };
        $scope.delete = function(id) {
            MonitorService.deleteMonitor(id)
                .success(function(data) {
                    console.log("Monitor deleted");
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
                    }
                }
            });

            modalInstance.result.then(function (monitor) {
                MonitorService.deleteMonitor(monitor.id)
                    .success(function(data) {
                        toastr.success('El monitor ha sido borrado', 'Borrar');
                        console.log("Monitor deleted");
                        $scope.retrieveAll();

                    }).error(function(data, status, headers, config) {
                        console.log("Error deleting monitor. Error code: " + status );
                        if ( status == 404 ){
                            toastr.error('No existe el monitor', 'Borrar');
                        }else if ( status == 500 ){
                            toastr.error('Error interno del servidor', 'Borrar');
                        }else{
                            toastr.error('Error en la conexión al servidor', 'Borrar');
                        }
                    });
            });
        };

        $scope.file = null;
        $scope.uploadImage = function(){
        }

        $rootScope.$on('toastMessage', function(event, toast){
            $timeout( toast, 1000 );
        });
    }
]);
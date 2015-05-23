
var app = angular.module('controllers',
    ['rest', 'ui.bootstrap', 'angularFileUpload','angular-loading-bar', 'ngAnimate', 'toastr']);

app.controller('headerCtrl', ['$scope', '$location',
    function($scope, $location){
        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
    }
]);

app.controller('homeCtrl', ['$scope',
    function($scope){
        $scope.helloWorld = "We are going to change the world";
    }
]);

app.controller('activitiesCtrl', ['$scope', 'ActivityService', 'CategoryService', '$routeParams',
    function($scope, ActivityService, CategoryService, $routeParams){
        CategoryService.retrieveAll()
        .success(function(data) {
            $scope.categories = data.category;
            console.log("Retrieve categories (count): " + $scope.categories.length);
        });
        $scope.retrieveByCategory = function (categoryName) {
            ActivityService.retrieveByCategory(categoryName)
            .success(function(data) {
                $scope.activities = data.activity;
                console.log("Retrieve activities (count): " + $scope.activities.length);
            })
            .error(function(data, status, headers, config) {
                console.log("Error retrieving activities. Error code: " + status );
                if ( status == 500 ){
                    console.log('Error interno del servidor');
                }else{
                    console.log('Error con la conexión al servidor');
                }
            });
        };
        $scope.retrieveAll = function () {
            ActivityService.retrieveAll()
            .success(function(data) {
                $scope.activities = data.activity;
                console.log("Retrieve activities (count): " + $scope.activities.length);
            })
            .error(function(data, status, headers, config) {
                console.log("Error deleting activity. Error code: " + status );
                if ( status == 404 ){
                    toastr.error('No existe la actividad', 'Borrar');
                }else if ( status == 500 ){
                    toastr.error('Error interno del servidor', 'Borrar');
                }else{
                    toastr.error('Error en la conexión al servidor', 'Borrar');
                }
            });
        };
        if ($routeParams.categoryName){
            $scope.retrieveByCategory($routeParams.categoryName);
        }else{
            $scope.retrieveAll();
        }
    }
]);


app.controller('searchMenuCtrl', ['$scope', '$routeParams', 'CategoryService',
    function($scope, $routeParams, CategoryService){
        CategoryService.retrieveAll()
        .success(function(data) {
            $scope.categories = data.category;
            console.log("Retrieve categories (count): " + $scope.categories.length);
        });
        $scope.selectedCategory = $routeParams.categoryName ? $routeParams.categoryName : '';
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
                    $location.path('/');
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
                    $location.path('/');
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


app.controller('reservationCtrl', function ($scope, $rootScope, $routeParams, FileUploader, ActivityService, ReservationService, $location, toastr) {


    $scope.reservation ={};

    ActivityService.retrieveActivity($routeParams.id)
        .success(function(data) {
            $scope.activity = data.activity;
            console.log("Retrieved activity: " + $scope.activity.title);
        });

    $scope.validateDNI = function( dni ) {
        var dni_letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        var letter = dni_letters.charAt( parseInt( dni, 10 ) % 23 );

        return letter == dni.charAt(8);
    };

    $scope.validateEmail = function( email ) {
        expr = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        if ( !expr.test(email) )
            return false;
        return true;
    };

    $scope.submit = function () {
        $scope.reservation.activity = $scope.activity;
        ReservationService.addReservation( $scope.reservation)
            .success(function(data) {
                console.log("Activity added");
                toastr.success('La reserva ha sido añadida!', 'Añadir');
                /*
                $rootScope.$broadcast('toastMessage', function(){
                    toastr.success('La reserva ha sido añadida!', 'Añadir');
                });*/
                $location.path('activities');
                $scope.activityForm.$submitted = true;
            }).error(function(data, status, headers, config) {
                if ( status == 500 ){
                    toastr.error('Error interno del servidor', 'Actualizar');
                }else{
                    console.log("Error al hacer la reserva. Error code: " + status );
                    toastr.error('Error en la conexión al servidor', 'Añadir');
                }
            });
        $scope.activity.remainingPlaces = $scope.activity.remainingPlaces - $scope.reservation.places;
        ActivityService.updateActivity($scope.activity);
    };

});


app.controller('loginCtrl', ['$scope', '$routeParams', 'LoginService',
    function($scope, $routeParams, LoginService){

        $scope.submit = function () {
            console.log("USer: " + $scope.user);
            LoginService.login($scope.user)
                .success(function(data) {
                    localStorage.setItem("token",data);
                    console.log("Retrieve login token: " + data);
                });
        };
    }
]);


app.controller('registrationCtrl', ['$scope', '$routeParams', 'RegistrationService',
    function($scope, $routeParams, RegistrationService, toastr){

        $scope.submit = function () {
            console.log("User: " + $scope.user);
            RegistrationService.registration($scope.user)
                .success(function(data) {
                    toastr.success('La reserva ha sido añadida!', 'Añadir');
                    console.log("Registration: " + data);
                });
        };
    }
]);


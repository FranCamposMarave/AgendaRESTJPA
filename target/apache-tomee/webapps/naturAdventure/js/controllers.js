/**
 * Created by oscar on 13/11/14.
 */

(function() {
    var appControllers = angular.module('app', []);

    appControllers.controller('homeController', ['$scope', '$http',
        function ($scope, $http) {
            $http.get('http://localhost:8080/naturAdventure/activities').success(function (data) {
                    $scope.activities = data.activity;
                });
        }
    ]);

    appControllers.controller('activityController', ['$scope', '$http',
        function ($scope, $http) {
        }
    ]);
})();
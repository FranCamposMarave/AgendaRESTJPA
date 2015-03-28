/**
 * Created by oscar on 13/11/14.
 */

(function() {
    var appControllers = angular.module('app', []);

    appControllers.controller('homeController', ['$scope', '$http',
        function ($scope, $http) {
            getAllActivities();
        }
    ]);

    appControllers.controller('activityController', ['$scope', '$http',
        function ($scope, $http) {
        }
    ]);
})();
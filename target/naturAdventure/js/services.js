/**
 * Created by oscar on 13/11/14.
 */

(function() {
    var app = angular.module('app', []);
    app.baseURI = 'http://localhost:8080/natureAdventure/activities';

    app.controller('homeController', ['$scope', 'Service', function ($scope, ActivityService) {
        var self = this;
        self.getAllActivities = function(){
            ActivityService.retrieveAll()
                    .success(function(data){
                        console.log(data);
                        $scope.activities = data.activity;
                    });
        }
    }]);

    app.controller('activityController', ['$scope', 'Service', function ($scope, ActivityService) {
            var self = this;
            self.retreiveActivity = function(id) {
                ActivityService.retrieveActivity(id)
                    .success(function(data) {
                        console.log(data);
                        $scope.currentActivity = data;
                    });
            }

        }]);

    app.service('ActivityService', ['$http', function($http) {
        this.retrieveAll = function() {
            return $http.get(app.baseURI);
        }

        this.retrieveActivity = function(id) {
            var url = app + id;
            return $http.get(url);
        }

    }]);

})();
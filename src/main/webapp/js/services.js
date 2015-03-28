/**
 * Created by oscar on 13/11/14.
 */

(function() {
    var app = angular.module('app.services', []);
    app.baseURI = 'http://localhost:8080/natureAdventure/activities';

    app.controller('homeController', ['$scope', 'Service', function ($scope, AgendaService) {
        var self = this;
        $scope.activities = ActivityService.retrieveAll()
            .success(function(data){
                console.log(data);
                $scope.activities = data.activities;
            });

        self.retreiveActivity = function(id) {
            ActivityService.retrieveActivity(id)
                .success(function(data) {
                    console.log(data);
                    $scope.currentActivity = data;
                });
        }

    }]);

    app.service('activityService', ['$http', function($http) {
        this.retrieveAll = function() {
            return $http.get(app.baseURI);
        }

        this.retrieveActivity = function(id) {
            var url = app + id;
            return $http.get(url);
        }

    }]);

})();

(function() {
    var app = angular.module('rest', []);

    app.baseURI = 'http://localhost:8080/naturAdventure/';
    app.activityURI = "activities/";
    app.categoryURI = "categories/";

    app.service('ActivityService', ['$http', function($http) {

        this.retrieveAll = function() {
            return $http.get(app.baseURI + app.activityURI);
        }

        this.addActivity = function (activity) {
            var url = app.baseURI + app.activityURI;
            var data = "{activity:" + JSON.stringify(activity) + "}";
            console.log(activity);
            return $http.post(url, data);
        }

        this.retrieveActivity = function(id) {
            var url = app.baseURI + app.activityURI + id;
            return $http.get(url);
        }

        this.deleteActivity = function(id) {
            var url = app.baseURI + app.activityURI + id;
            var data = {'id': id}
            return $http.delete(url, data);
        }

        this.updateActivity = function (activity) {
            var url = app.baseURI + app.activityURI + activity.id ;
            var data = "{activity:" + JSON.stringify(activity) + "}";
            return $http.put(url, data );
        }
    }]);

    app.service('CategoryService', ['$http', function($http) {

        this.retrieveAll = function() {
            console.log(app.baseURI + app.categoryURI);
            return $http.get(app.baseURI + app.categoryURI);
        }

        this.addCategory = function (category) {
            var url = app.baseURI + app.categoryURI;
            console.log(url);
            var data = "{category:" + JSON.stringify(category) + "}";
            console.log(category);
            return $http.post(url, data);
        }

        this.retrieveCategory = function(id) {
            var url = app.baseURI + app.categoryURI + id;
            return $http.get(url);
        }

        this.deleteCategory = function(id) {
            var url = app.baseURI + app.categoryURI + id;
            var data = {'id': id}
            return $http.delete(url, data);
        }

        this.updateCategory = function (category) {
            var url = app.baseURI + app.categoryURI + category.id ;
            var data = "{category:" + JSON.stringify(category) + "}";
            return $http.put(url, data );
        }
    }]);

    app.service('MonitorService', ['$http', function($http) {

        this.retrieveAll = function() {
            console.log(app.baseURI + app.monitorURI);
            return $http.get(app.baseURI + app.monitorURI);
        }

        this.addMonitor = function (monitor) {
            var url = app.baseURI + app.monitorURI;
            console.log(url);
            var data = "{monitor:" + JSON.stringify(monitor) + "}";
            console.log(monitor);
            return $http.post(url, data);
        }

        this.retrieveMonitor = function(id) {
            var url = app.baseURI + app.monitorURI + id;
            return $http.get(url);
        }

        this.deleteMonitor = function(id) {
            var url = app.baseURI + app.monitorURI + id;
            var data = {'id': id}
            return $http.delete(url, data);
        }

        this.updateMonitor = function (monitor) {
            var url = app.baseURI + app.monitorURI + monitor.id ;
            var data = "{monitor:" + JSON.stringify(monitor) + "}";
            return $http.put(url, data );
        }
    }]);
})();
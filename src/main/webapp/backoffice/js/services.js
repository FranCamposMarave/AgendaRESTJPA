/**
 * Created by oscar on 13/11/14.
 */

(function() {
    var app = angular.module('rest', []);
    app.baseURI = 'http://localhost:8080/naturAdventure/activities/';

    app.service('ActivityService', ['$http', function($http) {
       this.retrieveAll = function() {
           return $http.get(app.baseURI);
       }

       this.addActivity = function (activity) {
          var url = app.baseURI;
          var data = "{activity:" + JSON.stringify(activity) + "}";
           console.log(activity);
          return $http.post(url, data);
       }

       this.retrieveActivity = function(id) {
           var url = app.baseURI + id;
           return $http.get(url);
       }

       this.deleteActivity = function(id) {
           var url = app.baseURI + id;
           var data = {'id': id}
           return $http.delete(url, data);
       }

       this.updateActivity = function (activity) {
           var url = app.baseURI + activity.id;
           var data = "{activity:" + JSON.stringify(activity) + "}";
           return $http.put(url, data );
       }
    }]);
})();
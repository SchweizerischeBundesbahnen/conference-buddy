'use strict';

angular.module('conferenceBuddyApp').factory('CommentService', ['$http', 'REST_URL', function($http, REST_URL) {

    return {
        load: function(pid) {
            return $http.get(REST_URL + '/comment/' + pid).then(function(result) {
                return result.data;
            });
        },
        save: function(pid, comment) {
            return $http.put(REST_URL + '/comment', {pid: pid, comment: comment}).then(function(result) {
                return result.data;
            });
        },
        update: function(cid, comment) {
            return $http.post(REST_URL + '/comment/' + cid, {comment: comment}).then(function(result) {
                return result.data;
            });
        },
        delete: function(cid) {
            return $http.delete(REST_URL + '/comment/' + cid).then(function(result) {
                return result.data;
            });
        }
    };

}]);
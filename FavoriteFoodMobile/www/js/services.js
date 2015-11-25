angular.module('starter.services', ['starter.config'])


.config(function ($sceDelegateProvider) {
    $sceDelegateProvider.resourceUrlWhitelist(['self', new RegExp('^(http[s]?):\/\/(w{3}.)?youtube\.com/.+$')]);
})

/**
 * A simple example service that returns some data.
 */
.factory('Friends', function() {
    // Might use a resource here that returns a JSON array

    // Some fake testing data
    var friends = [{
        id: 0,
        name: 'Scruff McGruff'
    }, {
        id: 1,
        name: 'G.I. Joe'
    }, {
        id: 2,
        name: 'Miss Frizzle'
    }, {
        id: 3,
        name: 'Ash Ketchum'
    }];

    return {
        all: function() {
            return friends;
        },
        get: function(friendId) {
            // Simple index lookup
            return friends[friendId];
        }
    }
})


.factory('Data', function() {
  var sharedData={};
  return sharedData;
  
})


.factory('MenuItemService', function($http, ENV) {
	
	console.log("Response  inside MenuItemService :" + ENV.apiUrl);
   var menuItemService = {};
  
    menuItemService.getMenuItems= function() {
       
        return $http({
            url: ENV.apiUrl + 'menu/getMenuItems',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            data: {
                "request_data_type": "menuItem",
                "request_data_method": "getMenuItems",
                 "user_data": {
						}
            }
        }).then(function(response) {
            console.log("Response  getMenuItems :" + response.data.request_data_result);
            return response;
        });
    };
	 return menuItemService;
})
	
.factory('ItemDetailService', function($http, ENV) {
	
	console.log("Response  inside ItemDetailCtrl :" + ENV.apiUrl);
   var ItemDetailService = {};
  
    ItemDetailService.getItemDetails= function($stateParams) {
       
        return $http({
            url: ENV.apiUrl + 'menu/getItemDetails',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            data: {
                "request_data_type": "getItemDetails",
                "request_data_method": "getItemDetails",
                 "menu_data": {
					 "itemId":$stateParams.itemId
						}
            }
        }).then(function(response) {
            console.log("Response  getMenuItems :" + response.data.request_data_result);
            return response;
        });
    };
 
 
  return ItemDetailService;
})




// Service to create and destroy sessions
.service('Session', function() {
    this.create = function(sessionId, userName, userRole,userId) {
        this.id = sessionId;
        this.userName = userName;
        this.userRole = userRole;
        this.userId = userId;
    };
    this.destroy = function() {
        this.id = null;
        this.userName = null;
        this.userRole = null;
        this.userId = null;
    };
    return this;
});

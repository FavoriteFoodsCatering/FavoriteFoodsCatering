angular.module('ffcWebApp.services', ['ffcWebApp.config'])


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
   // ENV.apiUrl='http://54.68.90.137:8181/ffc/services/';
    menuItemService.getMenuItems = function() {
    	console.log("Response  inside MenuItemService :" + ENV.apiUrl);
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
	
	menuItemService.addItem = function(userId,itemId,orderType) {
       
        return $http({
            url: ENV.apiUrl + 'cart/addToCart',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            data: {
                "request_data_type": "menuItem",
                "request_data_method": "addItem",
                 "cart_data": {
				        	"userId":userId,
                            "itemId":itemId,
                            "qty":"1",
                            "orderType":orderType
				 }
            }
        }).then(function(response) {
            console.log("Response  addItem :" + response.data.request_data_result);
            return response;
        });
    };
	
	
	 return menuItemService;
})


.factory('LoginService', function($http, ENV) {
	
   console.log("Response  inside LoginService :" + ENV.apiUrl);
   var logInService = {};
  
    logInService.login= function(loginData) {
       
        return $http({
            url: ENV.apiUrl + 'user/checkUser',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            data: {
                "request_data_type": "User",
                "request_data_method": "checkUser",
                 "user_data": {
					 "userName":loginData.username,
					 "password":loginData.password
				}
            }
        }).then(function(response) {
            console.log("Response  login :" + response.data.request_data_result);
            return response;
        });
    };
	
	logInService.addUser= function(loginData) {
       
        return $http({
            url: ENV.apiUrl + 'user/addUser',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            data: {
                "request_data_type": "User",
                "request_data_method": "addUser",
                 "user_data": {
					 "userName":loginData.username,
					 "password":loginData.password
				}
            }
        }).then(function(response) {
            console.log("Response  SignUp :" + response.data.request_data_result);
            return response;
        });
    };
	
	 return logInService;
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


.factory('CheckOutService', function($http, ENV) {
	
	console.log("Response  inside CheckOutService :" + ENV.apiUrl);
   var checkOutService = {};
  
   checkOutService.checkOut= function(userId,cartId) {
       
        return $http({
            url: ENV.apiUrl + 'cart/checkOut',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            data: {
                "request_data_type": "checkOut",
                "request_data_method": "checkOut",
                 "cart_data": {
					 "userId":userId,
					 "cartId":cartId
						}
            }
        }).then(function(response) {
            console.log("Response  Checkout :" + response.data.request_data_result);
            return response;
        });
    };
 
 
    checkOutService.deleteItem = function(userId, cartId, itemId) {
 	   return $http({
 		   url : ENV.apiUrl + 'cart/deleteItem' ,
 		   method : 'POST' ,
 		   headers : {
 			   'Content-Type':'application/json'
 		   },
 		   data:{
 			   "request_data_type":"",
 			   "request_data_method":"",
 			   "cart_data":{
 				   "userId" : userId,
 				   "cartId" : cartId,
 				   "itemId" : itemId
 			   }
 		   }
 	   }).then(function(response) {
 		   return response;
 	   });
    };
    
    checkOutService.submitOrder = function(paymentId, expDate, netAmt) {
  	   return $http({
  		   url : ENV.apiUrl + 'cart/chargeCreditCard' ,
  		   method : 'POST' ,
  		   headers : {
  			   'Content-Type':'application/json'
  		   },
  		   data:{
  			   "request_data_type":"",
  			   "request_data_method":"",
  			   "cart_data":{
  				   "userPaymentId" : paymentId,
  				   "expDate" : expDate,
  				   "netAmount" : netAmt
  			   }
  		   }
  	   }).then(function(response) {
  		   return response;
  	   });
     };
     
  return checkOutService;
})



.factory('OrderService', function($http, ENV) {
	
	console.log("Response  inside OrderService :" + ENV.apiUrl);
   var orderService = {};
  
   orderService.reviewOrder = function(userId,cartId, numPpl) {
 	   console.log("UserId :" + userId + " cartId " + cartId + " no of ppl " + numPpl);
       return $http({
           url: ENV.apiUrl + 'cart/previewMenu',
           method: 'POST',
           headers: {
               'Content-Type': 'application/json'
           },
           data: {
               "request_data_type": "checkOut",
               "request_data_method": "checkOut",
                "cart_data": {
					 "userId":userId,
					 "cartId":cartId,
					 "numPpl":numPpl
						}
           }
       }).then(function(response) {
           console.log("Response  Checkout :" + response.data.request_data_result);
           return response;
       });
   };
   
   orderService.deleteItem = function(userId, cartId, itemId) {
	   return $http({
		   url : ENV.apiUrl + 'cart/deleteItem' ,
		   method : 'POST' ,
		   headers : {
			   'Content-Type':'application/json'
		   },
		   data:{
			   "request_data_type":"",
			   "request_data_method":"",
			   "cart_data":{
				   "userId" : userId,
				   "cartId" : cartId,
				   "itemId" : itemId
			   }
		   }
	   }).then(function(response) {
		   return response;
	   });
   };
 
 
  return orderService;
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

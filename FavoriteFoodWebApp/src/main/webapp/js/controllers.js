angular.module('ffcWebApp.controllers', []);
/**
 * Controls the Blog
 */





app.controller('BlogCtrl', function (/* $scope, $location, $http */) {
	  console.log("Blog Controller reporting for duty.");
	});

	/**
	 * Controls all other Pages
	 */

app.controller('PageCtrl', function (/* $scope, $location, $http */) {
	  console.log("Page Controller reporting for duty.");

	  // Activates the Carousel
	  $('.carousel').carousel({
	    interval: 5000
	  });

	  // Activates Tooltips for Social Links
	  $('.tooltip-social').tooltip({
	    selector: "a[data-toggle=tooltip]"
	  });
	  
	  
	  
	});


app.controller('checkCtrl', function (/* $scope, $location, $http */) {
	  console.log("chk Controller reporting for duty.");

	  // Activates the Carousel
	  $('.carousel').carousel({
	    interval: 5000
	  });

	  // Activates Tooltips for Social Links
	  $('.tooltip-social').tooltip({
	    selector: "a[data-toggle=tooltip]"
	  });
	});


app.controller('LoginCtrl', function ( $scope, $rootScope,  $facebook, $location, $window, GooglePlus, LoginService) {
	
	$scope.templates = [{
        name: 'header.html',
        url: 'header.html'
    }];
	$scope.loginData = {};
	$scope.loginStatus = 'disconnected';
    $scope.facebookIsReady = false;
    $scope.user = null;
    console.log('login ctrl');
	$scope.login = function() {
	      // From now on you can use the Facebook service just as Facebook api says
    	console.log('raja');
	    	$facebook.login().then(function() {
	            // alert("yes");
	            refresh();
	        }, function() {
	            alert('ERr');
	        });
	    };

    
    $scope.login_google = function() {
        GooglePlus.login().then(function(authResult) {
            console.log(authResult);

            GooglePlus.getUser().then(function(user) {
            	console.log(user)
                $scope.welcomeMsg = "Welcome " + user.name;
                // $location.url('form.html');
                $scope.user = user.name;
		        $rootScope.user =  user.name;
		        $rootScope.loginData = user;
				$scope.loginData = user;
				$rootScope.actType = 'gmail';
				$scope.actType = 'gmail';
				$rootScope.accountId = user.id;
				$scope.accountId = user.id;
                document.getElementById('loginStatus').innerHTML = "Welcome " + user.name;
                if($scope.accountId != null) {
                	LoginService.checkAndInsert($scope.loginData, $scope.actType).then(function(response){
                    	console.log('User successfully resgistered in ffc : ' + response);
                    });
                }
                
                console.log(user);
            });
        }, function(err) {
            console.log(err);
        });
    };
	    
    function refresh() {
        $facebook.api("/me").then(
            function(response) {
                console.log(response);
                $scope.user = response.name;
		        $rootScope.user =  response.name;
		        $rootScope.loginData = response;
				$scope.loginData = response;
				$rootScope.actType = 'fb';
				$scope.actType = 'fb';
				$rootScope.accountId = response.id;
				$scope.accountId = response.id;
                document.getElementById('loginStatus').innerHTML = "Welcome " + response.name;
                $scope.isLoggedIn = true;
                LoginService.checkAndInsert($scope.loginData, $scope.actType).then(function(response){
                	console.log('User successfully resgistered in ffc : ' + response);
                });
            },
            function(err) {
                $scope.welcomeMsg = "Please log in";
            });
    }
	
});

  
  app.controller('MenuCtrl', function ( $scope,MenuItemService,$rootScope,SessionService) {
	  console.log("Menu Controller");
	  console.log("ROOR"+$rootScope.cartSize);
	 if( $rootScope.cartSize){}
	 else{
	 	 $rootScope.cartSize=0;
	 	 SessionService.createCart();
	 	 $rootScope.cartId=1;
     }
	 $rootScope.orderType = "cater";
	 $rootScope.userId=36;
	
	 
	 	 
	  MenuItemService.getMenuItems().then(function(response) {
			$scope.request_data_type = response.data.request_data_type;
			$scope.results = response.data.request_data_result;
		//	$state.go('app.ffcmenuitems');
			
		console.log($scope.results);	
		  });
	  
	  $scope.setSelectedType = function (value) {
		  console.log('ordertype change ' + value);
		  $rootScope.menu.orderType = value;
	  };
	  
	//  $scope.addItem=function(itemId,rate){
	//	  console.log('add item :'+itemId +' '+ $rootScope.userId + 'order type'+ $rootScope.orderType);
		  		  
	//	  MenuItemService.addItem($rootScope.userId,itemId, $rootScope.orderType).then(function(response){
	//		  if(response.data.request_data_result.status==true){
	//			  $rootScope.cartSize += 1;
	//			  $rootScope.cartId=response.data.request_data_result.cartId;
	//			  console.log('Cart Size : ' +$rootScope.cartSize +' and cartId'+$rootScope.cartId);
	//		  }else{
	//			  console.log('Item already added to the cart');
	//		  }
	//	  });
		 
	//	}; 
		
	  $scope.addItem=function(itemId,itemName,itemDesc,imageUrl,categoryId){
		  console.log('add item :'+itemId +' '+ $rootScope.userId + ' order type'+ $rootScope.orderType);
		  SessionService.updateCart(itemId,itemName,itemDesc,imageUrl,categoryId);
		  $rootScope.cartSize +=1;
		  var caterArr = new Array("2","3","1","0");
		  console.log(SessionService);
	  };
	  
	});
  
  
  app.controller('CheckOutCtrl', function ($scope,CheckOutService,$rootScope,SessionService,OrderService) {
	  console.log("CheckOutCtrl Controller reporting for duty." + $rootScope.userId);

	//  CheckOutService.checkOut($rootScope.userId,$rootScope.cartId).then(function(response){
	//	  $scope.request_data_type = response.data.request_data_type;
	//		$scope.coresults = response.data.request_data_result;
	//	  console.log('checkout res : '+$scope.coresults.itemsObj[0].itemName);
		  
	//  });
	
	  
	  $scope.coresults = SessionService.cart;
	  console.log('Bala :'+$scope.coresults);
	  console.log(SessionService.cart.cartItem);
	  
	  
		 $scope.deleteItem = function(itemId) { 
			 console.log("CheckOutCtrl Controller deleting order item." + itemId);
			 CheckOutService.deleteItem($rootScope.userId,$rootScope.cartId,itemId).then(function(response){
				$rootScope.itemDeleted = response.data.request_data_result;
			  console.log('Item deleted : '+$scope.itemDeleted);
			  if(response.data.request_data_result==true){
				  $rootScope.cartSize -= 1;
				  $location.path('/reviewOrder');
				  $route.reload();
			  }
		  });
		 };
	  
		 $scope.submitOrder = function() { 
			 console.log("CheckOutCtrl Controller submitting order.");
			 console.log(SessionService.cart.cartItem);
			 CheckOutService.submitOrder($rootScope.userId,$rootScope.cartId,null,SessionService.cart.cartItem).then(function(response){
				$rootScope.itemDeleted = response.data.request_data_result;
			  console.log('Item deleted : '+$scope.itemDeleted);
			  if(response.data.request_data_result==true){
				  $rootScope.cartSize -= 1;
				  $location.path('/reviewOrder');
				  $route.reload();
			  }
		  });
		 };
		 
	});

  
  
  app.controller('OrderCtrl', function ($scope,OrderService,$rootScope, $location, $route,SessionService, flash) {
	 
	  $rootScope.coresults = SessionService.cart;
	 
		 $scope.reviewOrder = function(NumPpl) {
			 console.log("dd CheckOutCtrl Controller reporting for duty." + NumPpl);
			 console.log($rootScope.coresults);
			 
			 if(NumPpl < 15) {
				 flash('warning', 'catered event is for minimum 15 people');
				 return;
			 } else {
				 var allowFlag = validateItems(NumPpl);
				 console.log("Items allowed : " + allowFlag);
				 if(allowFlag) {
					 OrderService.reviewOrder($rootScope.userId,$rootScope.cartId,$scope.NumPpl).then(function(response){
							$rootScope.orderRes = response.data.request_data_result;
							$rootScope.coresults = SessionService.cart;
							
						  console.log('checkout res : '+$scope.orderRes.shipAddress.add1);
						 // $state.go('reviewOrder');
						$location.path('/reviewOrder');
						$route.reload();
					  });
				 } else {
					 return;
				 }
			 }
			 
		 };
	 
	 $scope.deleteItem = function(itemId) { 
		 console.log("OrderCtrl Controller deleting order item." + itemId);
	  OrderService.deleteItem($rootScope.userId,$rootScope.cartId,itemId).then(function(response){
			$rootScope.itemDeleted = response.data.request_data_result;
		  console.log('Item deleted : '+$scope.itemDeleted);
		  if(response.data.request_data_result==true){
			  $rootScope.cartSize -= 1;
			  $location.path('/orders');
			  $route.reload();
		  }
	  });
	 };
	 
	 	function validateItems(NumPpl) {
	 		var entrees = 0;
	 		var sides = 0;
	 		var appetizers = 0;
	 		var desserts = 0;
	 		var sandwiches = 0;
	 		var total = 0;
	 		var allowedItems = true;
	 		$rootScope.coresults.cartItem.forEach(function(entry) {
	 			if (entry.categoryId == 1) {	 		
	 				appetizers += 1;
	 				total += 1;
	 			}
	 			if (entry.categoryId == 2) {	 		
	 				sides += 1;
	 				total += 1;
	 			}
	 			if (entry.categoryId == 3) {	 		
	 				desserts += 1;
	 				total += 1;
	 			}
	 			if (entry.categoryId == 4) {	 		
	 				entrees += 1;
	 				total += 1;
	 			}
	 			if (entry.categoryId == 5) {	 		
	 				sandwiches += 1;
	 				total += 1;
	 			}
	 		});
	 		console.log("No of appetizers :" + appetizers + "No of sides :" + sides + "No of desserts :" + desserts + "No of entrees :" + entrees + "No of sandwiches :" + sandwiches);
	 		if($rootScope.orderType == "cater") {
	 			switch(true) {
		 			case (NumPpl >= 15 && NumPpl <= 99): 
		 				if(appetizers > 2) {
		 					flash('warning', 'Maximum only 2 appetizers are allowed');
		 					allowedItems = false;
		 				}
			 			if(sides > 2) {
		 					flash('warning', 'Maximum only 2 sides are allowed');
					 		allowedItems = false;
		 				}
			 			if(desserts > 2) {
		 					flash('warning', 'Maximum only 2 desserts are allowed');
		 					allowedItems = false;
		 				}	
			 			if(entrees > 2) {
		 					flash('warning', 'Maximum only 3 entrees are allowed');		
		 					allowedItems = false;
		 				}	
			 			return allowedItems;
		 				break;
		 			case (NumPpl >= 100 && NumPpl <= 199):
		 				if(appetizers > 3) {
		 					flash('warning', 'Maximum only 3 appetizers are allowed');
		 					allowedItems = false;
		 				}	
			 			if(sides > 3) {
		 					flash('warning', 'Maximum only 3 sides are allowed');
		 					allowedItems = false;
		 				}	
			 			if(desserts > 3) {
		 					flash('warning', 'Maximum only 3 desserts are allowed');
		 					allowedItems = false;
		 				}
			 			if(entrees > 4) {
		 					flash('warning', 'Maximum only 4 entrees are allowed');
		 					allowedItems = false;
		 				}	
			 			if(total > 12) {
		 					flash('warning', 'Maximum only 12 items are allowed');
		 					allowedItems = false;
		 				}	
			 			return allowedItems;
		 				break;
		 			case (NumPpl >= 200 && NumPpl <= 299):
		 				if(appetizers > 3) {
		 					flash('warning', 'Maximum only 3 appetizers are allowed');
		 					allowedItems = false;
		 				}
			 			if(sides > 4) {
		 					flash('warning', 'Maximum only 4 sides are allowed');
		 					allowedItems = false;
		 				}
			 			if(desserts > 3) {
		 					flash('warning', 'Maximum only 3 desserts are allowed');
		 					allowedItems = false;
		 				}
			 			if(entrees > 5) {
		 					flash('warning', 'Maximum only 5 entrees are allowed');
		 					allowedItems = false;
		 				}
			 			if(total > 15) {
		 					flash('warning', 'Maximum only 15 items are allowed');
		 					allowedItems = false;
		 				}
			 			return allowedItems;
		 				break;
		 			case (NumPpl >= 300 && NumPpl <= 399):
		 				if(appetizers > 4) {
		 					flash('warning', 'Maximum only 4 appetizers are allowed');
		 					allowedItems = false;
		 				}
			 			if(sides > 5) {
		 					flash('warning', 'Maximum only 5 sides are allowed');
		 					allowedItems = false;
		 				}
			 			if(desserts > 4) {
		 					flash('warning', 'Maximum only 4 desserts are allowed');
		 					allowedItems = false;
		 				}
			 			if(entrees > 5) {
		 					flash('warning', 'Maximum only 5 entrees are allowed');
		 					allowedItems = false;
		 				}
			 			if(total > 18) {
		 					flash('warning', 'Maximum only 18 items are allowed');
		 					allowedItems = false;
		 				}
			 			return allowedItems;
		 				break;
		 				default: 	
		 					return allowedItems;
	 			}
	 		}
	 	}
	 
	});


 
/**
 * AngularJS Tutorial 1
 * @author Nick Kaye <nick.c.kaye@gmail.com>
 */

/**
 * Main AngularJS Web Application
 */
var app=angular.module('ffcWebApp', [
	'ngRoute', 'ngFacebook', 'googleplus', 'ffcWebApp.services'
]).service('sharedProperties', function () {
    var uniEdit = 0;

    return {
        getUni: function () {
            return uniEdit;
        },
        setUni: function(value) {
            uniEdit = value;
        }
    };
});

/**
 * Configure the Routes
 */
app.config(['$routeProvider', function ($routeProvider) {
  $routeProvider
    // Home
    .when("/", {templateUrl: "partials/menu.html", controller: "MenuCtrl"})
    .when("/contacts", {templateUrl: "partials/contacts.html", controller: "PageCtrl"})
    .when("/orders", {templateUrl: "partials/orders.html", controller: "CheckOutCtrl"})
    .when("/testimonials", {templateUrl: "partials/testimonials.html", controller: "PageCtrl"})
    .when("/reviewOrder", { templateUrl: "partials/checkout.html", controller: "OrderCtrl"})
    .otherwise("/404", {templateUrl: "partials/404.html", controller: "PageCtrl"});
}]);


app.config(function($facebookProvider, $routeProvider) {
    $facebookProvider.setAppId('1811588739076937');
});

app.config(['GooglePlusProvider', function(GooglePlusProvider) {
    GooglePlusProvider.init({
        clientId: '316428149214-peqajmbm5idn9d4peo1j1ue2jm6i00cu.apps.googleusercontent.com',
        apiKey: 'AIzaSyBkRYegvffwjfRw0KYYYFycrTO19JbvlwE'
    });
}]);

app.run(function($rootScope) {
    // Load the facebook SDK asynchronously	
	(function(d, s, id) {
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id))
			return;
		js = d.createElement(s);
		js.id = id;
		js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6&appId=1811588739076937"
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));

    // alert("hello");  
});


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
	  })

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
	  })

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
	var actType = '';
	$scope.loginData = {};
	$scope.loginStatus = 'disconnected';
    $scope.facebookIsReady = false;
    $scope.user = null;
    console.log('login ctrl')
	$scope.login = function() {
	      // From now on you can use the Facebook service just as Facebook api says
    	console.log('raja')
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
                document.getElementById('loginStatus').innerHTML = "Welcome " + response.name;
                LoginService.checkAndInsert($scope.loginData, $scope.actType).then(function(response){
                	console.log('User successfully resgistered in ffc : ' + response)
                });
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
                	console.log('User successfully resgistered in ffc : ' + response)
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
	  }
	  
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
		
	  $scope.addItem=function(itemId,itemName,itemDesc,imageUrl){
		  console.log('add item :'+itemId +' '+ $rootScope.userId + ' order type'+ $rootScope.orderType);
		  SessionService.updateCart(itemId,itemName,itemDesc,imageUrl);
		  $rootScope.cartSize +=1;
		  
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

  
  
  app.controller('OrderCtrl', function ($scope,OrderService,$rootScope, $location, $route,SessionService) {
	  
	  $rootScope.coresults = SessionService.cart;
	 
		 $scope.reviewOrder = function(NumPpl) {
			 console.log("dd CheckOutCtrl Controller reporting for duty." + NumPpl);
			 console.log($rootScope.coresults);
		
			 OrderService.reviewOrder($rootScope.userId,$rootScope.cartId,$scope.NumPpl).then(function(response){
				$rootScope.orderRes = response.data.request_data_result;
				$rootScope.coresults = SessionService.cart;
				
			  console.log('checkout res : '+$scope.orderRes.shipAddress.add1);
			 // $state.go('reviewOrder');
			$location.path('/reviewOrder');
			$route.reload();
		  });
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
	 
	});

 
/**
 * AngularJS Tutorial 1
 * @author Nick Kaye <nick.c.kaye@gmail.com>
 */

/**
 * Main AngularJS Web Application
 */
var app=angular.module('ffcWebApp', [
	'ngRoute','ffcWebApp.services'
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




  
  app.controller('MenuCtrl', function ( $scope,MenuItemService,$rootScope) {
	  console.log("Menu Controller");
	  console.log("ROOR"+$rootScope.cartSize);
	 if( $rootScope.cartSize){}
	 else
	 	 $rootScope.cartSize=0;
     
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
	  
	  $scope.addItem=function(itemId,rate){
		  console.log('add item :'+itemId +' '+ $rootScope.userId + 'order type'+ $rootScope.orderType);
		  		  
		  MenuItemService.addItem($rootScope.userId,itemId, $rootScope.orderType).then(function(response){
			  if(response.data.request_data_result.status==true){
				  $rootScope.cartSize += 1;
				  $rootScope.cartId=response.data.request_data_result.cartId;
				  console.log('Cart Size : ' +$rootScope.cartSize +' and cartId'+$rootScope.cartId);
			  }else{
				  console.log('Item already added to the cart');
			  }
		  });
		 
		}; 
		  
	  
	});
  
  
  app.controller('CheckOutCtrl', function ($scope,CheckOutService,$rootScope) {
	  console.log("CheckOutCtrl Controller reporting for duty." + $rootScope.userId);

	  CheckOutService.checkOut($rootScope.userId,$rootScope.cartId).then(function(response){
		  $scope.request_data_type = response.data.request_data_type;
			$scope.coresults = response.data.request_data_result;
		  console.log('checkout res : '+$scope.coresults.itemsObj[0].itemName);
		  
	  });
	  
		 $scope.deleteItem = function(itemId) { 
			 console.log("OrderCtrl Controller deleting order item." + itemId);
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
			 console.log("OrderCtrl Controller submitting order.");
			 CheckOutService.submitOrder($rootScope.userId,$rootScope.cartId).then(function(response){
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

  
  
  app.controller('OrderCtrl', function ($scope,OrderService,$rootScope, $location, $route) {
	 
	  $scope.reviewOrder = function(NumPpl) {
		 console.log("OrderCtrl Controller reporting for duty." + NumPpl);
	  OrderService.reviewOrder($rootScope.userId,$rootScope.cartId,$scope.NumPpl).then(function(response){
			$rootScope.orderRes = response.data.request_data_result;
		  console.log('checkout res : '+$scope.orderRes.shipAddress.add1);
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
  
 
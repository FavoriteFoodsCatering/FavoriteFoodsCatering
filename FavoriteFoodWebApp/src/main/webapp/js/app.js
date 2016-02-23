/**
 * AngularJS Tutorial 1
 * @author Nick Kaye <nick.c.kaye@gmail.com>
 */

/**
 * Main AngularJS Web Application
 */
var app=angular.module('ffcWebApp', [
	'ngRoute','ffcWebApp.services'
]);

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
     
	  
	 $rootScope.userId=36;

	 
	  MenuItemService.getMenuItems().then(function(response) {
			$scope.request_data_type = response.data.request_data_type;
			$scope.results = response.data.request_data_result;
		//	$state.go('app.ffcmenuitems');
			
		console.log($scope.results);	
		  });
	  
	  
	  $scope.addItem=function(itemId,rate){
		  console.log('add item :'+itemId +' ' +rate+' '+ $rootScope.userId);
		  
		  MenuItemService.addItem($rootScope.userId,itemId,rate).then(function(response){
			  if(response.data.request_data_result.status==true){
				  $rootScope.cartSize += 1;
				  $rootScope.cartId=response.data.request_data_result.cartId;
				  console.log('Cart Size : ' +$rootScope.cartSize +' and cartId'+$rootScope.cartId);
			  }else{
				  console.log('error adding item');
			  }
		  }		  
		  );
		  
		 
		}; 
		  
	  
	});
  
  
  app.controller('CheckOutCtrl', function ($scope,CheckOutService,$rootScope) {
	  console.log("CheckOutCtrl Controller reporting for duty.");

	  CheckOutService.checkOut($rootScope.userId,$rootScope.cartId).then(function(response){
		  $scope.request_data_type = response.data.request_data_type;
			$scope.coresults = response.data.request_data_result;
		  console.log('checkout res : '+$scope.coresults.itemsObj[0].itemName);
		  
	  });
	 
	});

  
  
 
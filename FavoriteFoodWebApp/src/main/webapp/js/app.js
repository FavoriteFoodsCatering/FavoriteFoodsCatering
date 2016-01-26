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
    .when("/orders", {templateUrl: "partials/orders.html", controller: "PageCtrl"})
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



  app.controller('MenuCtrl', function ( $scope,MenuItemService) {
	  console.log("Menu Controller");
	  
	  
	  MenuItemService.getMenuItems().then(function(response) {
			$scope.request_data_type = response.data.request_data_type;
			$scope.results = response.data.request_data_result;
		//	$state.go('app.ffcmenuitems');
			
		console.log($scope.results);	
		  });
	  
	
	});
  
  
  
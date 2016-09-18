/**
 * AngularJS Tutorial 1
 * @author Nick Kaye <nick.c.kaye@gmail.com>
 */

/**
 * Main AngularJS Web Application
 */
var app=angular.module('ffcWebApp', [
	'ngRoute', 'ngFacebook', 'googleplus', 'flash', 'ffcWebApp.services','ffcWebApp.controllers'
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

/*app.config('flash', function($rootScope) {
	  var queue = [];
	  var currentMessage = "";

	  $rootScope.$on("$routeChangeSuccess", function() {
	    currentMessage = queue.shift() || "";
	  });

	  return {
	    setMessage: function(message) {
	      queue.push(message);
	    },
	    getMessage: function() {
	      return currentMessage;
	    }
	  };
});*/

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
		js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6&appId=1811588739076937";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));

    // alert("hello");  
});



 
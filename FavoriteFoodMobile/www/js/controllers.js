angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout,$state,$rootScope) {

console.log('in login');
  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});
$rootScope.cartSize=0;
  // Form data for the login modal
  $scope.loginData = {};

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
	    console.log('Close');
  // $state.go('ffcmenuitems');
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    console.log('Doing login', $scope.loginData);
	$rootScope.cartSize=0;
	$scope.loginData={};
   $state.go('app.ffcmenuitems');
    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
   // $timeout(function() {
   ///   $scope.closeLogin();
  //  }, 1000);
  };
  
  $scope.addItem=function(){
	  console.log('add item');
	  $rootScope.cartSize = $rootScope.cartSize+1;
  };
})

.controller('PlaylistsCtrl', function($scope) {
	console.log('ddd');
  $scope.playlists = [
    { title: 'Reggae', id: 1 },
    { title: 'Chill', id: 2 },
    { title: 'Dubstep', id: 3 },
    { title: 'Indie', id: 4 },
    { title: 'Rap', id: 5 },
    { title: 'Cowbell', id: 6 },
	{ title: 'BALA', id: 7 }
  ];
})

.controller('LoginCtrl', function($scope, $stateParams) {
	console.log('LoginCtrl');
})

.controller('FFCMenuCtrl', function($scope, $stateParams) {
	console.log('FFCMenuCtrl');
})

.controller('PlaylistCtrl', function($scope, $stateParams) {
})

.controller('MenuItemCtrl', function($scope,$stateParams, Session, $rootScope, $state, MenuItemService, $timeout,$rootScope) {
	console.log('MenuItemCtrl');
	
	$scope.addItem=function(){
	  console.log('add item');
	  $rootScope.cartSize = $rootScope.cartSize+1;
  };
  
	MenuItemService.getMenuItems().then(function(response) {
    $scope.request_data_type = response.data.request_data_type;
    $scope.results = response.data.request_data_result;
  });

 
})

.controller('ItemDetailCtrl', function($scope,$stateParams, Session, $rootScope, $state, ItemDetailService, $timeout) {
	console.log('ItemDetailService');
	ItemDetailService.getItemDetails($stateParams).then(function(response) {
    $scope.request_data_type = response.data.request_data_type;
    $scope.results = response.data.request_data_result;
	console.log($scope.results);
  });

});

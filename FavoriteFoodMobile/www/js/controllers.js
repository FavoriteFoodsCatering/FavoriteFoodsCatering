angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout,$state,$rootScope) {

console.log('in login');
  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});

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

.controller('LoginCtrl', function($scope, $ionicModal,$stateParams, LoginService, Session, $rootScope, $state, MenuItemService, $timeout,$rootScope) {
console.log('LoginCtrl');
		
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
	LoginService.login($scope.loginData).then(function(response){
		console.log(response.data.request_data_result.isexists +" "+response.data.request_data_result.userId );
		if(response.data.request_data_result.isexists=="true"){
			 $scope.loginMessage='';
			 $rootScope.userId=response.data.request_data_result.userId;
			 $state.go('app.ffcmenuitems');
		}
		else
			$scope.loginMessage="Invalid User";
	}
	
	); 	
	$scope.loginData={};
   
  };
  
  $scope.doSignUp = function() {
    console.log('Doing Sign Up', $scope.loginData);
	$rootScope.cartSize=0;
	LoginService.addUser($scope.loginData).then(function(response){
	 if(response.data.request_data_result==true){
		 $scope.loginMessage='';
		  $state.go('login');
	 }else
		$scope.loginMessage="Sign Up Failed";
	}
	
	);
	$scope.loginData={};
   
  };
  
  $scope.addItem=function(){
	  console.log('add item');
	  $rootScope.cartSize = $rootScope.cartSize+1;
  };
})

		

.controller('FFCMenuCtrl', function($scope, $stateParams) {
	console.log('FFCMenuCtrl');
})

.controller('PlaylistCtrl', function($scope, $stateParams) {
})

.controller('MenuItemCtrl', function($scope,$stateParams, Session, $rootScope, $state, MenuItemService, $timeout,$rootScope,$ionicHistory,$ionicActionSheet) {
	console.log('MenuItemCtrl');
	
	
	
	if($state.current.name=='app.ffcmenuitems'){
		console.log('menu');
		 MenuItemService.getMenuItems().then(function(response) {
		$scope.request_data_type = response.data.request_data_type;
		$scope.results = response.data.request_data_result;
		$state.go('app.ffcmenuitems');
	  });
	}
	
	$scope.addItem=function(itemId,rate){
	  console.log('add item :'+itemId +' ' +rate+' '+ $rootScope.userId);
	  
	  MenuItemService.addItem($rootScope.userId,itemId,rate).then(function(response){
		  if(response.data.request_data_result.status==true){
		      $rootScope.cartSize = $rootScope.cartSize+1;
		  }else{
			  console.log('error adding item');
		  }
	  }		  
	  );
	}; 
	  
	  $scope.show = function() {
        console.log('show');
		   // Show the action sheet
		   var hideSheet = $ionicActionSheet.show({
			 buttons: [
			   { text: '<font size="3">Problem with order</font>' },
			   { text: '<font size="3">Delivery Issue</font>' }
			 ],
			 titleText: 'Email & Support',
			 cancelText: '<font size="3">Cancel</font>',
			 cancel: function() {
				  // add cancel code..
				},
			 buttonClicked: function(index) {
				 console.log(index);
					$state.go('sendmail');
			   return true;
			 }
			// buttonClicked:sendFeedback()
		   });

		   // For example's sake, hide the sheet after two seconds
		  /* $timeout(function() {
			 hideSheet();
		   }, 2000);*/

  };
  
   $scope.sendFeedback= function() {
        if(window.plugins && window.plugins.emailComposer) {
            window.plugins.emailComposer.showEmailComposerWithCallback(function(result) {
                console.log("Response -> " + result);
            }, 
            "Feedback for your App", // Subject
            "",                      // Body
            ["test@example.com"],    // To
            null,                    // CC
            null,                    // BCC
            false,                   // isHTML
            null,                    // Attachments
            null);                   // Attachment Data
        }
    };
 
	  
  
  
   $scope.doCheckOut=function(){
   console.log('Check out');
   $state.go('checkout');
   };

	$scope.getMenuItems=function(){   
	  MenuItemService.getMenuItems().then(function(response) {
		$scope.request_data_type = response.data.request_data_type;
		$scope.results = response.data.request_data_result;
		$state.go('app.ffcmenuitems');
	  });
	};
  
    $scope.backCheckOut = function(){
		console.log('back');
		$ionicHistory.goBack();
	};
	
	$scope.cancelEmail = function(){
		console.log('back');
		$ionicHistory.goBack();
	};
	
	$scope.goback = function(){
		console.log('goback');
		$ionicHistory.goBack();
	};
 
	$scope.doHelp=function(){
	 $state.go('help');
    };
 
})

.controller('ItemDetailCtrl', function($scope,$stateParams, Session, $rootScope, $state, ItemDetailService, $timeout) {
	console.log('ItemDetailService');
	ItemDetailService.getItemDetails($stateParams).then(function(response) {
    $scope.request_data_type = response.data.request_data_type;
    $scope.results = response.data.request_data_result;
	console.log($scope.results);
  });

});

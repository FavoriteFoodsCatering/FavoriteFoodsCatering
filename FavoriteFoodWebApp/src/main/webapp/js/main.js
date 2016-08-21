$(document).ready(function() {
		var id = '#dialog';
		//Get the screen height and width
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();
		//Set heigth and width to mask to fill up the whole screen
		if(!getZipFromSession()){
			$('#mask').css({'width':maskWidth,'height':maskHeight});
			//transition effect
			$('#mask').fadeIn(500);
			$('#mask').fadeTo("slow",0.9);
			//Get the window height and width
			var winH = $(window).height();
			var winW = $(window).width();
			//Set the popup window to center
			$(id).css('top',  winH/2-$(id).height()/2);
			$(id).css('left', winW/2-$(id).width()/2);
			//transition effect
			$(id).fadeIn(2000);
		}


		var zips=['94536','94555','94538','94539','94560','94025','94301','94305','94304','94306','94303',
				'94043','94035','94041','94040','94022','94024','94087','94086','94085','94089','94020','95008','94054',
				'95051','95050','95002','95035','95134','95131','95133','95112','95110','95126','95129','95130','95117',
				'95128','95124','95118','95136','95111','95121','95125','95122','95148','95116','95127','95132'];

		function checkZip(value) {
        //return (/(^\d{5}$)|(^\d{5}-\d{4}$)/).test(value);
			if(	$.inArray(value,zips)!=-1 && (/(^\d{5}$)|(^\d{5}-\d{4}$)/).test(value)){
						return true;
			}else{
					return false;
			}
    }
    $('#zip').on('click', function (e) {
        e.preventDefault;
        var value = $('#zipCode').val();
        if (checkZip(value)) {
					saveZipToSession(value);
					$('#mask').fadeOut(2000);
					$('.window').fadeOut(1000);
        }else{
						$('#zipCode').css("border", "2px solid red");
						//$('#zipCode').val('');
				}
    });
});

function saveZipToSession(zipCode){
	window.sessionStorage.setItem('zipCode',zipCode);
}

function getZipFromSession(){
	return window.sessionStorage.getItem('zipCode');
}

function onSignIn(googleUser) {
	 var   profile = googleUser.getBasicProfile();
//	 name=profile.getName();
	  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	  console.log('Name: ' + profile.getName());
	  console.log('Image URL: ' + profile.getImageUrl());
	  console.log('Email: ' + profile.getEmail());
	  
	 
	}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }


function changeLoginText() {
    console.log('change logintext function called ')
	//console.log('change logintext  response status function called '),
// document.getElementById('changetextlogin').innerHTML = "Logout";
document.getElementById('logindropdown').style.display = "none";
document.getElementById('hidetext').style.display = "none";	
//document.getElementById('showafterlogin').style.display="visible";
    }

function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);

    if (response.status === 'connected') {
      testAPI();
    } else if (response.status === 'not_authorized') {
      document.getElementById('status').innerHTML = 'Please log ' +
        'into this app.';
    } else {
      
      document.getElementById('status').innerHTML = 'Please log ' +
        'into Facebook.';
    }
  }

  function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  }

  window.fbAsyncInit = function() {
  FB.init({
    appId      : 1811588739076937,
    cookie     : true,  // enable cookies to allow the server to access 
                        // the session
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.5' // use graph api version 2.5
  });


  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });

  };

  // Load the SDK asynchronously
  (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6&appId=1811588739076937"
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));
  
  
  function testAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me', function(response) {
      console.log('Successful login for: ' + response.name);
      document.getElementById('status').innerHTML =
        'Thanks for logging in, ' + response.name + '!';
    });
  }

app.run(function($rootScope, $http, $location, Base64Service, AuthenticationService, editableOptions) {

    editableOptions.theme = 'bs3';

    $rootScope.errors = [];
    $rootScope.requests401 = [];
    $rootScope.navigateTo = "/main";
	
	/**
     * Holds all the requests which failed due to 401 response.
     */
     $rootScope.$on('event:loginRequired', function () {
    	$rootScope.requests401 = [];
    	
    	if ($location.path().indexOf("/login") == -1) {
            $rootScope.navigateTo = $location.path();
    	}

         // TODO: don't push message if already in array
        $rootScope.errors.push({ message:"Please enter a valid username / password" });
   
        $location.path('/login');
    });

    /**
     * On 'event:loginConfirmed', resend all the 401 requests.
     */
    $rootScope.$on('event:loginConfirmed', function () {
        var i,
            requests = $rootScope.requests401,
            retry = function (req) {
                $http(req.config).then(function (response) {
                    req.deferred.resolve(response);
                });
            };

        for (i = 0; i < requests.length; i += 1) {
            retry(requests[i]);
        }

        $rootScope.requests401 = [];
        $rootScope.errors = [];
    });

    /**
     * On 'event:loginRequest' send credentials to the server.
     */
    $rootScope.$on('event:loginRequest', function (event, username, password) {
    	// set the basic authentication header that will be parsed in the next request and used to authenticate
        $http.defaults.headers.common['Authorization'] = 'Basic ' + Base64Service.encode(username + ':' + password);

        AuthenticationService.getAuthenticated().then(
            function success(user) {
                $rootScope.user = user;

                $rootScope.$broadcast('event:loginConfirmed');

                console.log("You have been successfully logged in.")
            });
    });

    /**
     * On 'logoutRequest' invoke logout on the server.
     */
    $rootScope.$on('event:logoutRequest', function () {
        $http.defaults.headers.common.Authorization = null;

        AuthenticationService.logout().then(
            function success() {
                $rootScope.user = null;

                console.log("You have been successfully logged out.")
            });
    });
});
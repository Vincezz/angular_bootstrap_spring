app.controller('MainController', function ($rootScope, $scope, $location) {
	$scope.logout = function () {
		$scope.$emit('event:logoutRequest');

        $location.path("/main");
    };

    $scope.login = function (credentials) {
        $scope.$emit('event:loginRequest', credentials.username, credentials.password);

        $location.path($rootScope.navigateTo);
    };
});

app.controller('CustomerController', function ($scope, CustomerService) {
	$scope.init = function() {
        CustomerService.getCustomers().then(function(response) {
            $scope.customers = response;
        });
	};

    $scope.add = function(id) {
        CustomerService.addCustomer()
    };
    $scope.delete = function(id) {
        CustomerService.deleteCustomer(id).then(function(response) {
            if(response) {
                angular.forEach($scope.customers, function (customer, index) {
                    if (id == customer.id) {
                        $scope.customers.splice(index, 1);

                        console.info("Customer " + id + " has been deleted.")
                    }
                });
            }
            else {
                console.error("Customer " + id + " was unable to be deleted.")
            }
        });
    };

    $scope.save = function(id) {
        angular.forEach($scope.customers, function(customer) {
            if(id == customer.id) {
                CustomerService.saveCustomer(customer).then(function(response) {
                    if(response) {
                        console.info("Customer " + id + " has been saved.")
                    }
                    else {
                        console.error("Customer " + id + " was unable to be saved.")
                    }
                });
            }
        });
    };
});
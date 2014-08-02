!function () {
  angular.module('h-security',['ngResource','ngTable','h-utils'])
    .factory('AccountResource', ['hideoutConfig', '$resource', AccountResource])
    .factory('GroupResource', ['hideoutConfig', '$resource', GroupResource])
    .factory('RoleResource', ['hideoutConfig', '$resource', RoleResource])
    .controller('AccountController', ['$scope', 'AccountResource', 'ngTableParams', '$state', AccountController])
    .controller('GroupController', ['$scope', 'GroupResource', 'ngTableParams', GroupController])
    .controller('RoleController', ['$scope', 'RoleResource', 'ngTableParams', RoleController])
    .controller('UserController', ['$scope', 'AccountResource', 'errorHandlerMethodFactory', 'alertsService', '$state', UserController])
    .config(['hideoutConfig', '$stateProvider', Config]);

  function Config(hideoutConfig, $stateProvider) {

    $stateProvider
      .state('security', {
        url: hideoutConfig.consoleBaseUrl+"/security",
        views: {
          '@': {
            templateUrl: hideoutConfig.consoleBaseUrl + "/.partials/security/security-main"
          },
          '@security': {
            templateUrl: hideoutConfig.consoleBaseUrl + "/.partials/security/tabs"
          }
        }
      })
      .state('security.addUser', {
        views: {
          '' : {
            templateUrl: hideoutConfig.consoleBaseUrl + "/.partials/security/user",
            controller: 'UserController'
          }
        }
      });
  }

  function AccountResource(hideoutConfig, $resource){
    return $resource(hideoutConfig.restUrl + '/security/account/:id');
  }

  function GroupResource(hideoutConfig, $resource){
    return $resource(hideoutConfig.restUrl + '/security/group/:id');
  }
  function RoleResource(hideoutConfig, $resource){
    return $resource(hideoutConfig.restUrl + '/security/role/:id');
  }

  function AccountController($scope, AccountResource, ngTableParams, $state) {
    $scope.tableParams = new ngTableParams({
      page: 1,
      count: 10
    },{
      getData: function($defer, params) {
        AccountResource.get({page: params.page()-1, size: params.count()}, function(accountsPage){
          params.total(accountsPage.totalElements);
          $defer.resolve(accountsPage.content);
        });
      }
    });
  }
  function GroupController($scope, GroupResource, ngTableParams) {
    $scope.tableParams = new ngTableParams({
      page: 1,
      count: 10
    },{
      getData: function($defer, params) {
        GroupResource.get({page: params.page()-1, size: params.count()}, function(accountsPage){
          params.total(accountsPage.totalElements);
          $defer.resolve(accountsPage.content);
        });
      }
    });

  }
  function RoleController($scope, RoleResource, ngTableParams) {
    $scope.tableParams = new ngTableParams({
      page: 1,
      count: 10
    },{
      getData: function($defer, params) {
        RoleResource.get({page: params.page()-1, size: params.count()}, function(accountsPage){
          params.total(accountsPage.totalElements);
          $defer.resolve(accountsPage.content);
        });
      }
    });
  }

  function UserController($scope, AccountResource, errorHandlerMethodFactory, alertsService, $state) {
    $scope.model = {
      accountCreationDate: new Date()
    };

    $scope.save = function(){
      AccountResource.save($scope.model).$promise.then(function () {
        alertsService.add({
          type:'success',
          message:'Account created'
        });
        $state.go('security');
      }, errorHandlerMethodFactory($scope));
    }
  }


}();
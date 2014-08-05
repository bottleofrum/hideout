!function () {
  angular.module('h-security',['ngResource','ngTable','h-utils'])
    .factory('AccountResource', ['hideoutConfig', '$resource', AccountResource])
    .factory('GroupResource', ['hideoutConfig', '$resource', GroupResource])
    .factory('RoleResource', ['hideoutConfig', '$resource', RoleResource])
    .controller('TabsController', ['$scope', '$state', '$stateParams', TabsController])
    .controller('AccountController', ['$scope', 'AccountResource', 'ngTableParams', '$state', AccountController])
    .controller('GroupController', ['$scope', 'GroupResource', 'ngTableParams', GroupController])
    .controller('RoleController', ['$scope', 'RoleResource', 'ngTableParams', RoleController])
    .controller('UserController', ['$scope', 'AccountResource', 'errorHandlerMethodFactory', 'alertsService', '$state',
      'GroupResource', 'RoleResource', UserController])
    .config(['hideoutConfig', '$stateProvider', Config]);

  function Config(hideoutConfig, $stateProvider) {

    $stateProvider
      .state('security', {
        url: hideoutConfig.consoleBaseUrl+"/security/:tab",
        views: {
          '@': {
            templateUrl: hideoutConfig.consoleBaseUrl + "/.partials/security/security-main"
          },
          '@security': {
            templateUrl: hideoutConfig.consoleBaseUrl + "/.partials/security/tabs",
            controller: 'TabsController'
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

  function TabsController($scope, $state, $stateParams ) {
    $scope.tabsActivation = {
      'users': true,
      'groups': false,
      'roles': false
    };

    $scope.$watch('tabsActivation', function(newValue){
      if(!newValue) return;
      _.each(['users', 'groups','roles'], function(tab){
        if($scope.tabsActivation[tab]) {
          $state.go('security',{'tab':tab})
        }
      });

    }, true);

    if($stateParams['tab']) {
      setActiveTab($stateParams['tab'])
    }

    function setActiveTab (tab) {
      _.each(['users', 'groups','roles'], function(tab){
        $scope.tabsActivation[tab] = false;
      });
      $scope.tabsActivation[tab] = true;
    }

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

  function UserController($scope, AccountResource, errorHandlerMethodFactory, alertsService, $state, GroupResource,
                          RoleResource) {
    $scope.model = {
      accountCreationDate: new Date(),
      enabled: true
    };

    GroupResource.get({page: 0, size: 10000000}, function(response){
      $scope.groups = response.content;
    });
    RoleResource.get({page: 0, size: 10000000}, function (response) {
      $scope.roles = response.content;
    });

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
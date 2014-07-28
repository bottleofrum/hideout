!function () {
  angular.module('security',['ngResource','ngTable'])
    .factory('AccountResource', ['hideoutConfig', '$resource', AccountResource])
    .factory('GroupResource', ['hideoutConfig', '$resource', GroupResource])
    .factory('RoleResource', ['hideoutConfig', '$resource', RoleResource])
    .controller('AccountController', ['$scope', 'AccountResource', 'ngTableParams', AccountController])
    .controller('GroupController', ['$scope', 'GroupResource', 'ngTableParams', GroupController])
    .controller('RoleController', ['$scope', 'RoleResource', 'ngTableParams', RoleController]);

  function AccountResource(hideoutConfig, $resource){
    return $resource(hideoutConfig.restUrl + '/security/account/:id');
  }

  function GroupResource(hideoutConfig, $resource){
    return $resource(hideoutConfig.restUrl + '/security/group/:id');
  }
  function RoleResource(hideoutConfig, $resource){
    return $resource(hideoutConfig.restUrl + '/security/role/:id');
  }

  function AccountController($scope, AccountResource, ngTableParams) {
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

}();
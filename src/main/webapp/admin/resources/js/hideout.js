!function() {

  angular.module('hideout',['ui.router'])
    .constant('hideoutConfig', hideoutConfig)
    .config(['hideoutConfig', '$stateProvider', '$urlRouterProvider', '$locationProvider', Config]);

  function Config(hideoutConfig, $stateProvider, $urlRouterProvider, $locationProvider) {
    $locationProvider.html5Mode(true);
    $urlRouterProvider.otherwise(hideoutConfig.consoleBaseUrl);

    $stateProvider
      .state('main', {
        url: hideoutConfig.consoleBaseUrl,
        templateUrl: hideoutConfig.consoleBaseUrl + "/.partials/main"
      })
      .state('security', {
        url: hideoutConfig.consoleBaseUrl+"/security",
        templateUrl: hideoutConfig.consoleBaseUrl + "/.partials/security/security-main"
      })
  }

}();
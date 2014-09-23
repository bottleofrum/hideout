!function () {

    angular.module('hideout', ['ui.router', 'ui.bootstrap', 'h-security', 'h-utils'])
        .config(['hideoutConfig', '$stateProvider', '$urlRouterProvider', '$locationProvider', Config]);

    function Config(hideoutConfig, $stateProvider, $urlRouterProvider, $locationProvider) {
        $locationProvider.html5Mode(true);
        $urlRouterProvider.otherwise(hideoutConfig.consoleBaseUrl);

        $stateProvider
            .state('main', {
                url: hideoutConfig.consoleBaseUrl,
                templateUrl: hideoutConfig.consoleBaseUrl + "/.partials/main"
            })
    }

}();
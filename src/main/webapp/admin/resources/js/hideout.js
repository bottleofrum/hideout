!function() {

  angular.module('hideout',['ui.router', 'ui.bootstrap','security'])
    .constant('hideoutConfig', hideoutConfig)
    .config(['hideoutConfig', '$stateProvider', '$urlRouterProvider', '$locationProvider', Config])
    .directive('dateTime',[DateTimeDirective]);

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

  function DateTimeDirective() {

    function LinkFunction($scope, $element, $attrs){

      $scope.$watch('dateTime', function(newValue) {
        if(null == newValue) {
          return;
        }

        var date = $scope.dateTime;
        if(angular.isNumber(date) || angular.isString(date)) {
          date = new Date(date);
        }

        var year = date.getFullYear();
        var month = addLeadingZeroIfNecessary(date.getMonth()+1);
        var day = addLeadingZeroIfNecessary(date.getDate());
        var hour = addLeadingZeroIfNecessary(date.getHours())
        var minutes = addLeadingZeroIfNecessary(date.getMinutes())

        var dateAsString = year+"-"+month+"-"+day+" "+hour+":"+minutes;
        $element.html(dateAsString);
      });
    }

    function addLeadingZeroIfNecessary(number) {
      if(number < 10) {
        return "0"+number;
      } else {
        return ""+number;
      }
    }

    return {
      scope: {
        dateTime:'='
      },
      link: LinkFunction
    }
  }

}();
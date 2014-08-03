!function () {
  angular.module('h-utils', [])
    .constant('hideoutConfig', hideoutConfig)
    .factory('errorHandlerMethodFactory', [ErrorHandlerMethodFactory])
    .factory('alertsService', ['$rootScope', AlertsService])
    .filter('difference', [ArrayDifferenceFilter])
    .directive('hAlert', ['alertsService', '$timeout', AlertDirective])
    .directive('dateTime', [DateTimeDirective])
    .directive('serverError', [ServerErrorDirective])
    .directive('errorsPrinter', [ErrorsPrinterDirective])
    .directive('hMultiselect', ['hideoutConfig', MultiselectDirective]);

  function DateTimeDirective() {

    function LinkFunction($scope, $element, $attrs) {

      $scope.$watch('dateTime', function (newValue) {
        if (null == newValue) {
          return;
        }

        var date = $scope.dateTime;
        if (angular.isNumber(date) || angular.isString(date)) {
          date = new Date(date);
        }

        var year = date.getFullYear();
        var month = addLeadingZeroIfNecessary(date.getMonth() + 1);
        var day = addLeadingZeroIfNecessary(date.getDate());
        var hour = addLeadingZeroIfNecessary(date.getHours());
        var minutes = addLeadingZeroIfNecessary(date.getMinutes());

        var dateAsString = year + "-" + month + "-" + day + " " + hour + ":" + minutes;
        $element.html(dateAsString);
      });
    }

    function addLeadingZeroIfNecessary(number) {
      if (number < 10) {
        return "0" + number;
      } else {
        return "" + number;
      }
    }

    return {
      scope: {
        dateTime: '='
      },
      link: LinkFunction
    }
  }

  function ServerErrorDirective() {

    function LinkFunction($scope, $element, $attrs, $ctrl) {

      $element.on('change', function () {
        $scope.$apply(function () {
          $ctrl.$setValidity('server', true)
        })
      })

    }

    return {
      require: '?ngModel',
      link: LinkFunction
    }
  }

  function ErrorsPrinterDirective() {

    function LinkFunction($scope, $element, $attrs, $ctrl) {
      $scope.fieldName = $attrs['errorsPrinter'];
      var formName = $attrs['formName'] ? $attrs['formName'] : 'form';
      var form = $scope[formName];
      $scope.$watch(function () {
        return form[$scope.fieldName];
      }, function(newValue){
        $scope.field = newValue;
      })
    }

    return {
      scope: true,
      template: '<div class="text-danger" ng-show="field.$invalid && field.$error.server">' +
        '<div ng-repeat="error in errors[fieldName]">{{error}}</div></div>',
      link: LinkFunction
    }
  }

  function ErrorHandlerMethodFactory() {
    return function ($scope, formName) {
      $scope.errors = {};
      if (!formName) {
        formName = 'form';
      }

      return function (errorResponse) {
        if (errorResponse.status != 400) {
          return;
        }

        angular.forEach(errorResponse.data, function (errors, field) {
          if ($scope[formName][field]) {
            $scope[formName][field].$setValidity('server', false)
          }
          $scope.errors[field] = errors;
        });

      }
    }
  }

  function AlertsService($rootScope) {
    return {
      add: function (alert) {
        var alerts = $rootScope['_hideoutAlerts'];
        if (!alerts) {
          alerts = [];
          $rootScope['_hideoutAlerts'] = alerts;
        }
        alert['id'] = Math.random();
        alerts.push(alert);
      },
      remove: function (id) {
        $rootScope['_hideoutAlerts'] = _.filter($rootScope['_hideoutAlerts'], function (alert) {
          return alert['id'] != id;
        });
      }
    }
  }

  function AlertDirective(alertsService, $timeout) {
    function LinkFunction($scope, $element) {
      $element.on('closed.bs.alert', function () {
        $scope.$apply(function () {
          alertsService.remove($scope.id);
        })
      })

      $timeout(function () {
        $element.find('button.close').click();
      }, 2000);

    }

    return {
      scope: {
        id: '@',
        type: '@',
        message: '@'
      },
      link: LinkFunction,
      template: '<div class="alert alert-{{type}} alert-dismissible fade in" role="alert"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>{{message}}</div>'
    }
  }

  function MultiselectDirective(hideoutConfig) {

    function LinkFunction($scope, $element, $attrs, $ctrl) {

      $ctrl.$modelValue = $ctrl.$modelValue || [];
      $scope.selectedElements = $ctrl.$modelValue;

      var label = $scope.label || 'name';

      $scope.$watch('label',function(value) {
        if(value) {
          label = value;
        }
      })

      $ctrl.$isEmpty = function (value) {
        if (!value) {
          return true;
        }

        if (_.isArray(value) && value.length == 0) {
          return true;
        }

        return false;
      }

      $scope.select = function () {
        _.each($scope.toBeSelected, function(element){
          $scope.selectedElements.push(element)
          $ctrl.$setViewValue($scope.selectedElements)
        });
      }

      $scope.remove =function() {
        $scope.selectedElements = _.filter($scope.selectedElements, function(inElement){
          return !_.find($scope.toBeRemoved,function(outElement){
            return inElement === outElement;
          })
        });
        $ctrl.$setViewValue($scope.selectedElements)
      }

      $scope.getLabel = function(element) {
        return element[label];
      }

    }

    return {
      scope: {
        'hMultiselect': '=',
        label:'@'
      },
      require: 'ngModel',
      templateUrl: hideoutConfig.consoleBaseUrl + '/.partials/directives/multiselect',
      link: LinkFunction
    }
  }

  function ArrayDifferenceFilter() {
    return function (inputArray, outputArray) {
      return _.filter(inputArray, function(inElement){
        return !_.find(outputArray,function(outElement){
          return inElement === outElement;
        })
      });
    }
  }

}();
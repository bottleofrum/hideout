!function () {
    angular.module('h-utils', ['ngResource'])
        .constant('hideoutConfig', hideoutConfig)
        .factory('errorHandlerMethodFactory', [ErrorHandlerMethodFactory])
        .factory('alertsService', ['$rootScope', AlertsService])
        .filter('difference', [ArrayDifferenceFilter])
        .directive('hAlert', ['alertsService', '$timeout', AlertDirective])
        .directive('dateTime', [DateTimeDirective])
        .directive('serverError', [ServerErrorDirective])
        .directive('errorsPrinter', [ErrorsPrinterDirective])
        .directive('hMultiselect', ['hideoutConfig', MultiselectDirective])
        .directive('hModal', ['hideoutConfig', ModalDirective])
        .factory('modalService', ['$rootScope', ModalService])
        .factory('i18n', [InternationalizationService])
        .factory('Resource', ['$resource', Resource]);

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
            }, function (newValue) {
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
                alert['id'] = '_hideoutAlert_' + Math.random();
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

            $scope.$watch('label', function (value) {
                if (value) {
                    label = value;
                }
            });

            $ctrl.$isEmpty = function (value) {
                if (!value) {
                    return true;
                }

                return _.isArray(value) && value.length == 0;
            };

            $ctrl.$render = function () {
                if (!$ctrl.$viewValue) return;

                $scope.selectedElements = $ctrl.$viewValue;
            };

            $scope.select = function () {
                _.each($scope.toBeSelected, function (element) {
                    $scope.selectedElements.push(element);
                    $ctrl.$setViewValue($scope.selectedElements)
                });
            };

            $scope.remove = function () {
                $scope.selectedElements = _.filter($scope.selectedElements, function (inElement) {
                    return !_.find($scope.toBeRemoved, function (outElement) {
                        return inElement === outElement;
                    })
                });
                $ctrl.$setViewValue($scope.selectedElements)
            };

            $scope.getLabel = function (element) {
                return element[label];
            }

        }

        return {
            scope: {
                'hMultiselect': '=',
                label: '@'
            },
            require: 'ngModel',
            templateUrl: hideoutConfig.consoleBaseUrl + '/.partials/directives/multiselect',
            link: LinkFunction
        }
    }

    function ArrayDifferenceFilter() {
        return function (inputArray, outputArray, idFieldName) {
            return _.filter(inputArray, function (inElement) {
                return !_.find(outputArray, function (outElement) {
                    if (idFieldName) {
                        return inElement[idFieldName] === outElement[idFieldName];
                    } else {
                        return inElement === outElement;
                    }
                })
            });
        }
    }

    function ModalDirective(hideoutConfig) {
        function LinkFunction($scope) {
            $scope.acceptHandler = function () {
                if ($scope.onAccept) {
                    $scope.onAccept();
                }
            }
        }

        return {
            scope: {
                type: "=",//confirm, info
                title: "=",
                message: "=",
                onAccept: "&"
            },
            templateUrl: hideoutConfig.consoleBaseUrl + '/.partials/directives/modal',
            link: LinkFunction
        }
    }

    function ModalService($rootScope) {
        return {
            info: function (title, message) {
                $rootScope['_hideoutModal'] = {
                    type: 'info',
                    title: title,
                    message: message
                };

                $("#hideoutModalDialog").modal();
            },
            confirm: function (title, message, onAccept) {
                $rootScope['_hideoutModal'] = {
                    type: 'confirm',
                    title: title,
                    message: message,
                    onAccept: onAccept
                };

                $("#hideoutModalDialog").modal();
            }
        }
    }

    function InternationalizationService() {
        return hideoutI18n;
    }

    function Resource($resource) {
        return function (url, params, methods) {
            var defaults = {
                update: { method: 'put', isArray: false },
                create: { method: 'post' }
            };

            methods = angular.extend(defaults, methods);

            var resource = $resource(url, params, methods);

            resource.prototype.$save = function () {
                if (!this.id) {
                    return this.$create();
                } else {
                    return this.$update();
                }
            };

            return resource;
        };
    }
}();
!function () {
    angular.module('h-security', ['ngTable', 'h-utils'])
        .factory('AccountResource', ['hideoutConfig', 'Resource', AccountResource])
        .factory('GroupResource', ['hideoutConfig', 'Resource', GroupResource])
        .factory('RoleResource', ['hideoutConfig', 'Resource', RoleResource])
        .factory('WebAccessRuleResource', ['hideoutConfig', 'Resource', WebAccessRuleResource])
        .controller('TabsController', ['$scope', '$state', '$stateParams', TabsController])
        .controller('AccountController', ['$scope', 'AccountResource', 'ngTableParams', 'alertsService', 'modalService', 'i18n', ResourceController])
        .controller('GroupController', ['$scope', 'GroupResource', 'ngTableParams', 'alertsService', 'modalService', 'i18n', ResourceController])
        .controller('RoleController', ['$scope', 'RoleResource', 'ngTableParams', 'alertsService', 'modalService', 'i18n', ResourceController])
        .controller('WebAccessRuleController', ['$scope', 'WebAccessRuleResource', 'ngTableParams', 'alertsService', 'modalService', 'i18n', ResourceController])
        .controller('UserController', ['$scope', 'AccountResource', 'errorHandlerMethodFactory', 'alertsService', '$state',
            'GroupResource', 'RoleResource', 'i18n', '$stateParams', UserController])
        .controller('GroupPanelController', ['$scope', 'GroupResource', '$stateParams', 'alertsService', '$state', 'errorHandlerMethodFactory',
            'i18n', 'RoleResource', GroupPanelController])
        .controller('RolePanelController', ['$scope', 'RoleResource', '$stateParams', 'alertsService', '$state', 'errorHandlerMethodFactory',
            'i18n', RolePanelController])
        .controller('WebAccessRulePanelController', ['$scope', 'WebAccessRuleResource', '$stateParams', 'alertsService', '$state', 'errorHandlerMethodFactory',
            'i18n', WebAccessRulePanelController])
        .config(['hideoutConfig', '$stateProvider', Config]);

    function Config(hideoutConfig, $stateProvider) {

        $stateProvider
            .state('security', {
                url: hideoutConfig.consoleBaseUrl + "/security/:tab",
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
            .state('security.userPanel', {
                params: ['id', 'tab'],
                views: {
                    '': {
                        templateUrl: hideoutConfig.consoleBaseUrl + "/.partials/security/user",
                        controller: 'UserController'
                    }
                }
            })
            .state('security.groupPanel', {
                params: ['id', 'tab'],
                views: {
                    '': {
                        templateUrl: hideoutConfig.consoleBaseUrl + "/.partials/security/group",
                        controller: 'GroupPanelController'
                    }
                }
            })
            .state('security.rolePanel', {
                params: ['id', 'tab'],
                views: {
                    '': {
                        templateUrl: hideoutConfig.consoleBaseUrl + "/.partials/security/role",
                        controller: 'RolePanelController'
                    }
                }
            })
            .state('security.rulePanel', {
                params: ['id', 'tab'],
                views: {
                    '': {
                        templateUrl: hideoutConfig.consoleBaseUrl + "/.partials/security/rule",
                        controller: 'WebAccessRulePanelController'
                    }
                }
            });
    }

    function AccountResource(hideoutConfig, Resource) {
        return Resource(hideoutConfig.restUrl + '/security/account/:id');
    }

    function GroupResource(hideoutConfig, Resource) {
        return Resource(hideoutConfig.restUrl + '/security/group/:id');
    }

    function RoleResource(hideoutConfig, Resource) {
        return Resource(hideoutConfig.restUrl + '/security/role/:id');
    }

    function WebAccessRuleResource(hideoutConfig, Resource) {
        return Resource(hideoutConfig.restUrl + '/security/webaccessrule/:id');
    }

    function TabsController($scope, $state, $stateParams) {
        var tabs = ['users', 'groups', 'roles', 'webaccessrules'];

        $scope.tabsActivation = {
            'users': true,
            'groups': false,
            'roles': false,
            'webaccessrules': false
        };

        $scope.$watch('tabsActivation', function (newValue) {
            if (!newValue) return;
            _.each(tabs, function (tab) {
                if ($scope.tabsActivation[tab]) {
                    $state.go('security', {'tab': tab})
                }
            });

        }, true);

        if ($stateParams['tab']) {
            setActiveTab($stateParams['tab'])
        }

        function setActiveTab(tab) {
            _.each(tabs, function (tab) {
                $scope.tabsActivation[tab] = false;
            });
            $scope.tabsActivation[tab] = true;
        }

    }

    function ResourceController($scope, Resource, ngTableParams, alertsService, modalService, i18n) {
        $scope.tableParams = new ngTableParams({
            page: 1,
            count: 10
        }, {
            getData: function ($defer, params) {
                Resource.get({page: params.page() - 1, size: params.count()}, function (accountsPage) {
                    params.total(accountsPage.totalElements);
                    $defer.resolve(accountsPage.content);
                });
            }
        });

        $scope.delete = function (id) {
            modalService.confirm(i18n['admin.security.dialog.delete.confirmation.title'],
                i18n['admin.security.dialog.delete.confirmation.message'],
                function () {
                    Resource.delete({id: id}, function () {
                        alertsService.add({
                            type: 'success',
                            message: i18n['admin.security.message.successful.delete']
                        });
                        $scope.tableParams.reload();
                    });
                })
        }

    }

    function UserController($scope, AccountResource, errorHandlerMethodFactory, alertsService, $state, GroupResource, RoleResource, i18n, $stateParams) {
        $scope.model = {
            accountCreationDate: new Date(),
            enabled: true
        };

        if ($stateParams.id) {
            $scope.model = AccountResource.get({id: $stateParams.id});
            $scope.editMode = true;
        }

        GroupResource.get({page: 0, size: 10000000}, function (response) {
            $scope.groups = response.content;
        });
        RoleResource.get({page: 0, size: 10000000}, function (response) {
            $scope.roles = response.content;
        });

        $scope.save = function () {
            if ($scope.editMode) {
                AccountResource.update({id: $scope.model.id}, $scope.model).$promise.then(function () {
                    alertsService.add({
                        type: 'success',
                        message: i18n['admin.security.message.account.updated']
                    });
                    $state.go('security');
                }, errorHandlerMethodFactory($scope));
            } else {
                AccountResource.create($scope.model).$promise.then(function () {
                    alertsService.add({
                        type: 'success',
                        message: i18n['admin.security.message.account.created']
                    });
                    $state.go('security');
                }, errorHandlerMethodFactory($scope));
            }
        };

        $scope.openAccountExpirationDatePopup = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.dateAccountExpirationPopupOpened = true;
        };

        $scope.openCredentialsExpirationDatePopup = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.dateCredentialsExpirationPopupOpened = true;
        };

    }

    function GroupPanelController($scope, GroupResource, $stateParams, alertsService, $state, errorHandlerMethodFactory, i18n, RoleResource) {

        $scope.model = {
            name: null,
            authorities: null
        };

        RoleResource.get({page: 0, size: 10000000}, function (response) {
            $scope.roles = response.content;
        });

        if ($stateParams.id) {
            $scope.model = GroupResource.get({id: $stateParams.id});
            $scope.editMode = true;
        }

        $scope.save = function () {
            if ($scope.editMode) {
                GroupResource.update({id: $scope.model.id}, $scope.model).$promise.then(function () {
                    alertsService.add({
                        type: 'success',
                        message: i18n['admin.security.message.group.updated']
                    });
                    $state.go('security');
                }, errorHandlerMethodFactory($scope));
            } else {
                GroupResource.create($scope.model).$promise.then(function () {
                    alertsService.add({
                        type: 'success',
                        message: i18n['admin.security.message.group.created']
                    });
                    $state.go('security');
                }, errorHandlerMethodFactory($scope));
            }
        };

    }

    function RolePanelController($scope, RoleResource, $stateParams, alertsService, $state, errorHandlerMethodFactory, i18n) {

        $scope.model = {
            authority: null
        };

        if ($stateParams.id) {
            $scope.model = RoleResource.get({id: $stateParams.id});
            $scope.editMode = true;
        }

        $scope.save = function () {
            if ($scope.editMode) {
                RoleResource.update({id: $scope.model.id}, $scope.model).$promise.then(function () {
                    alertsService.add({
                        type: 'success',
                        message: i18n['admin.security.message.role.updated']
                    });
                    $state.go('security');
                }, errorHandlerMethodFactory($scope));
            } else {
                RoleResource.create($scope.model).$promise.then(function () {
                    alertsService.add({
                        type: 'success',
                        message: i18n['admin.security.message.role.created']
                    });
                    $state.go('security');
                }, errorHandlerMethodFactory($scope));
            }
        };
    }

    function WebAccessRulePanelController($scope, WebAccessRuleResource, $stateParams, alertsService, $state, errorHandlerMethodFactory, i18n) {

        $scope.model = {};
        $scope.methods = ['GET', 'POST', 'HEAD', 'OPTIONS', 'PUT', 'PATCH', 'DELETE', 'TRACE'];
        $scope.matchers = ['ANT', 'ANY', 'REGEX', 'EL', 'IP'];


        if ($stateParams.id) {
            $scope.model = WebAccessRuleResource.get({id: $stateParams.id});
            $scope.editMode = true;
        }

        $scope.save = function () {
            if ($scope.editMode) {
                WebAccessRuleResource.update({id: $scope.model.id}, $scope.model).$promise.then(function () {
                    alertsService.add({
                        type: 'success',
                        message: i18n['admin.security.message.role.updated']
                    });
                    $state.go('security');
                }, errorHandlerMethodFactory($scope));
            } else {
                WebAccessRuleResource.create($scope.model).$promise.then(function () {
                    alertsService.add({
                        type: 'success',
                        message: i18n['admin.security.message.role.created']
                    });
                    $state.go('security');
                }, errorHandlerMethodFactory($scope));
            }
        };
    }


}();
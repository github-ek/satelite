// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
angular.module('starter', ['ionic', 'starter.controllers', 'starter.services','ngCordova'])

.run(function($ionicPlatform ,$rootScope) {

      $rootScope.$on('scope.stored', function (event, data) {
        console.log("scope.stored", data);
    });

  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);

    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });
})


.config(function($stateProvider, $urlRouterProvider ,$ionicConfigProvider ) {
    $ionicConfigProvider.tabs.position('bottom');

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider

  // setup an abstract state for the tabs directive
    .state('tab', {
    url: '/tab',
    abstract: true,
    templateUrl: 'templates/tabs.html',
        controller: "ingresoController"
  })

  // Each tab has its own nav history stack:

  .state('tab.ingreso', { 
    url: '/ingreso',
    views: {
      'tab-ingreso': {
        templateUrl: 'templates/Ingreso.html',
        controller: 'ingresoController'
      }
    }
  })

  .state('tab.descarga', {
      url: '/descarga',
      views: {
        'tab-descarga': {
          templateUrl: 'templates/Descarga.html',
          controller: 'descargaController'
        }
      }
    })

  .state('tab.productos', {
      url: '/productos',
      views: {
        'tab-productos': {
          templateUrl: 'templates/Productos.html',
          controller: 'productosController'
        }
      }
    })
  .state('tab.reporte', {
      url: '/reporte',
      views: {
        'tab-reporte': {
          templateUrl: 'templates/Reporte.html',
          controller: 'reporteController'
        }
      }
    })

    .state('tab.chat-detail', {
      url: '/chats/:chatId',
      views: {
        'tab-chats': {    
          templateUrl: 'templates/chat-detail.html',
          controller: 'ChatDetailCtrl'
        }
      }
    })

  .state('tab.salida', {
    url: '/salida',
    views: {
      'tab-salida': {
        templateUrl: 'templates/Salida.html',
        controller: 'salidaController'
      }
    }
  });

  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/tab/ingreso');

});

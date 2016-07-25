angular.module('starter.services', [])
/*
.factory("vehiculosService" .function ($q){
        return {
       tipoVehiculo: [
                                   {"id":"0", "texto":"TRACTOMULA CONTENEDOR"},  
                                   {"id":"1", "texto":"TRACTOMULA CARROCERIA"},  
                                   {"id":"2", "texto":"TRACTOMULA FURGONADA"},  
                                   {"id":"3", "texto":"PATINETA"},  
                                   {"id":"4", "texto":"DOBLE TROQUE"},  
                                   {"id":"5", "texto":"SENCILLO"},  
                                   {"id":"6", "texto":"TURBO"},  
                                   {"id":"7", "texto":"CARRY"},  
                                   {"id":"8", "texto":"LUV"},  
                                   {"id":"9", "texto":"OTRO"}
                               ],

                                getVehiculos: function(){

                                  return this.tipoVehiculo;
                                },

                                 getVehiculo: function(id) {
                                    var dfd = $q.defer();
                                    this.tipoVehiculo.forEach(function(tipoVehiculo) {
                                      if (tipoVehiculo.id === id) dfd.resolve(tipoVehiculo);
                                    })

                                    return dfd.promise;
                                 }


                  }

});*/
.factory('Scopes', function ($rootScope) {
    var mem = {};
 
    return {
        store: function (key, value) {
            $rootScope.$emit('scope.stored', key);
            mem[key] = value;
        },
        get: function (key) {
            return mem[key];
        }
    };
});
/*
.factory('Chats', function() {
  // Might use a resource here that returns a JSON array

  // Some fake testing data
  var chats = [{
    id: 0,
    name: 'Ben Sparrow',
    lastText: 'You on your way?',
    face: 'img/ben.png'
  }, {
    id: 1,
    name: 'Max Lynx',
    lastText: 'Hey, it\'s me',
    face: 'img/max.png'
  }, {
    id: 2,
    name: 'Adam Bradleyson',
    lastText: 'I should buy a boat',
    face: 'img/adam.jpg'
  }, {
    id: 3,
    name: 'Perry Governor',
    lastText: 'Look at my mukluks!',
    face: 'img/perry.png'
  }, {
    id: 4,
    name: 'Mike Harrington',
    lastText: 'This is wicked good ice cream.',
    face: 'img/mike.png'
  }];

  return {
    all: function() {
      return chats;
    },
    remove: function(chat) {
      chats.splice(chats.indexOf(chat), 1);
    },
    get: function(chatId) {
      for (var i = 0; i < chats.length; i++) {
        if (chats[i].id === parseInt(chatId)) {
          return chats[i];
        }
      }
      return null;
    }
  };
});*/

/*jshint strict: true, curly: false, node: true */
/*global require, console */

'use strict';

var _super = require('superagent');
var jsdom = require('jsdom');
var utils = require('util');
var EventEmitter = require('events').EventEmitter;

var url = 'http://www.dgii.gov.do/app/WebApps/Consultas/rnc/RncWeb.aspx';

var RncQueryWrapper = function(cedula) {
  var self = this;
  
  process.nextTick(function(){
    self.emit('start');

    _super
      .post(url)
      .send({
          '__EVENTTARGET': '',
          '__EVENTARGUMENT': '',
          '__LASTFOCUS': '',
          '__VIEWSTATE': '/wEPDwUKMTY4ODczNzk2OA9kFgICAQ9kFgQCAQ8QZGQWAWZkAg0PZBYCAgMPPCsACwBkZHTpAYYQQIXs/JET7TFTjBqu3SYU',
          '__EVENTVALIDATION': '/wEWBgKl57TuAgKT04WJBAKM04WJBAKDvK/nCAKjwtmSBALGtP74CtBj1Z9nVylTy4C9Okzc2CBMDFcB',
          'rbtnlTipoBusqueda': '0',
          'txtRncCed': cedula,
          'btnBuscaRncCed': 'Buscar'
      })
      .set({
          'Content-Type': 'application/x-www-form-urlencoded'
      })
      .end(function(error, res) {
          if (error)
            self.emit('error', error);

          jsdom.env(
            res.text,
            ['http://code.jquery.com/jquery.js'],
            function (errors, window) {
                var info = {
                    cedulaRnc: window.$('#pnlResultadoRuc table .GridItemStyle td:eq(0)').html(),
                    nombre: window.$('#pnlResultadoRuc table .GridItemStyle td:eq(1)').html(),
                    nombreComercial: window.$('#pnlResultadoRuc table .GridItemStyle td:eq(2)').html(),
                    estado: window.$('#pnlResultadoRuc table .GridItemStyle td:eq(5)').html()
                };

                self.emit('complete', info);
          });
      });
  });

};

utils.inherits(RncQueryWrapper, EventEmitter);

module.exports = RncQueryWrapper;
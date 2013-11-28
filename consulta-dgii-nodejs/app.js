/*jshint strict: true, curly: false, node: true */
/*global require, console */

'use strict';

var Request = require('./RncQuery.js');
var myRequest = new Request('130744874');

myRequest.on('start', function(){
	console.log('Request comenzo!');
});

myRequest.on('error', function(err){
	console.log(err);
});

myRequest.on('complete', function(data){
	console.log(data);
})
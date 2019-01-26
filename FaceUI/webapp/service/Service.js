sap.ui.define([
	"sap/ui/base/Object"
], function (Object) {
	"use strict";

	var Service = Object.extend("be.wl.ml.FaceUI.service.Service", {
		constructor: function () {},

		setModel: function (model) {
			this.model = model;
		},
		odata: function (url) {
			var me = this;
			var core = {
				ajax: function (type, url, data, parameters) {
					var promise = new Promise(function (resolve, reject) {
						var args = [];
						var params = {};
						args.push(url);
						if (data) {
							args.push(data);
						}
						if (parameters) {
							params = parameters;
						}
						params.success = function (result, response) {
							resolve({
								data: result,
								response: response
							});
						};
						params.error = function (error) {
							reject(error);
						};
						args.push(params);
						me.model[type].apply(me.model, args);
					});
					return promise;
				}
			};

			return {
				'get': function (params) {
					return core.ajax('read', url, false, params);
				},
				'post': function (data, params) {
					return core.ajax('create', url, data, params);
				},
				'put': function (data, params) {
					return core.ajax('update', url, data, params);
				},
				'delete': function (params) {
					return core.ajax('remove', url, false, params);
				}
			};
		},
		http: function (url) {

			// A small example of object
			var core = {

				// Method that performs the ajax request
				ajax: function (method, url, headers, args, mimetype) {

					// Creating a promise
					var promise = new Promise(function (resolve, reject) {

						// Instantiates the XMLHttpRequest
						var client = new XMLHttpRequest();
						var uri = url;
						if (args && method === 'GET') {
							uri += '?';
							var argcount = 0;
							for (var key in args) {
								if (args.hasOwnProperty(key)) {
									if (argcount++) {
										uri += '&';
									}
									uri += encodeURIComponent(key) + '=' + encodeURIComponent(args[key]);
								}
							}
						}

						client.open(method, uri, true);

						if (args && (method === 'POST' || method === 'PUT')) {
							// client.setRequestHeader("accept", "application/json");
							var data = args;
							// var data = {};
							// for (var keyp in args) {
							// 	if (args.hasOwnProperty(keyp)) {
							// 		if (keyp === "files") {
							// 			var form = new FormData();
							// 			form.append(keyp, args[keyp].content, args[keyp].name);

							// 			if (form.fd) {
							// 				form = form.fd;
							// 			}
							// 		} else {
							// 			data[keyp] = args[keyp];
							// 		}
							// 	}
							// }
							// if(!form){
							// 	client.setRequestHeader("Content-Type", "application/json");
							// }
						}
						for (var keyh in headers) {
							if (headers.hasOwnProperty(keyh)) {
								client.setRequestHeader(keyh, headers[keyh]);
							}
						}
						if (data) {
							client.send(data instanceof FormData ? data : JSON.stringify(data));
						} else {
							client.send();
						}
						client.onload = function () {
							if (this.status == 200 || this.status == 201) {
								// Performs the function "resolve" when this.status is equal to 200
								resolve(this.response);
							} else {
								// Performs the function "reject" when this.status is different than 200
								reject(this);
							}
						};
						client.onerror = function () {
							reject(this);
						};
					});

					// Return the promise
					return promise;
				}
			};

			// Adapter pattern
			return {
				'get': function (headers, args) {
					return core.ajax('GET', url, headers, args);
				},
				'post': function (headers, args) {
					return core.ajax('POST', url, headers, args);
				},
				'put': function (headers, args) {
					return core.ajax('PUT', url, headers, args);
				},
				'delete': function (headers, args) {
					return core.ajax('DELETE', url, headers, args);
				}
			};
		}
	});
	return Service;
});
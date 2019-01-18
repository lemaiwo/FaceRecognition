sap.ui.define([], function () {
	'use strict';
	var hasBlobConstructor = typeof Blob !== 'undefined' && (function () {
		try {
			return Boolean(new Blob());
		} catch (e) {
			return false;
		}
	})();

	var hasArrayBufferViewSupport = hasBlobConstructor && typeof Uint8Array !== 'undefined' && (function () {
		try {
			return new Blob([new Uint8Array(100)]).size === 100;
		} catch (e) {
			return false;
		}
	})();

	var hasToBlobSupport = typeof HTMLCanvasElement !== "undefined" ? HTMLCanvasElement.prototype.toBlob : false;

	var hasBlobSupport = hasToBlobSupport || typeof Uint8Array !== 'undefined' && typeof ArrayBuffer !== 'undefined' && typeof atob !==
		'undefined';

	var hasReaderSupport = typeof FileReader !== 'undefined' || typeof URL !== 'undefined';

	return {
		resize: function (file) {
			var me = this,
				maxDimensions = {
					width: 800,
					height: 600
				};

			var maxWidth = maxDimensions.width;
			var maxHeight = maxDimensions.height;
			return new Promise(function (resolve, reject) {

				if (!me.isSupported() || !file.type.match(/image.*/)) {
					reject(file, false);
				}

				if (file.type.match(/image\/gif/)) {
					// Not attempting, could be an animated gif
					reject(file, false);
					// TODO: use https://github.com/antimatter15/whammy to convert gif to webm

				}

				// var image = document.createElement('img');

				// image.onload = function(imgEvt) {
				var reader = new FileReader();
				reader.onload = function (imgEvt) {
					var image = new Image();
					image.src = reader.result;

					image.onload = function () {
						var width = image.width;
						var height = image.height;
						var isTooLarge = false;

						if (width >= height && width > maxDimensions.width) {
							// width is the largest dimension, and it's too big.
							height *= maxDimensions.width / width;
							width = maxDimensions.width;
							isTooLarge = true;
						} else if (height > maxDimensions.height) {
							// either width wasn't over-size or height is the largest dimension
							// and the height is over-size
							width *= maxDimensions.height / height;
							height = maxDimensions.height;
							isTooLarge = true;
						}

						var canvas = document.createElement('canvas');
						canvas.width = width;
						canvas.height = height;

						var ctx = canvas.getContext('2d');
						ctx.drawImage(image, 0, 0, width, height);
						var blob = me._toBlob(canvas, file.type);

						// if (!isTooLarge) {
						// early exit; no need to resize
						resolve({
							blob: blob,
							uri: canvas.toDataURL(file.type)
						}, true);
						//resolve(imgEvt.target.result, false);
						return;
						// }

						// callback(canvas.toDataURL(file.type), true);
						// if (hasToBlobSupport) {
						// 	canvas.toBlob(function (blob) {
						// 		callback(blob, true);
						// 	}, file.type);
						// } else {
						// resolve({
						// 	blob: blob,
						// 	uri: canvas.toDataURL(file.type)
						// }, true);
						// }
					};
				};
				reader.readAsDataURL(file);
				// this._loadImage(image, file);

			});
		},

		_toBlob: function (canvas, type) {
			var dataURI = canvas.toDataURL(type);
			var dataURIParts = dataURI.split(',');
			var byteString = undefined;
			if (dataURIParts[0].indexOf('base64') >= 0) {
				// Convert base64 to raw binary data held in a string:
				byteString = atob(dataURIParts[1]);
			} else {
				// Convert base64/URLEncoded data component to raw binary data:
				byteString = decodeURIComponent(dataURIParts[1]);
			}
			var arrayBuffer = new ArrayBuffer(byteString.length);
			var intArray = new Uint8Array(arrayBuffer);

			for (var i = 0; i < byteString.length; i += 1) {
				intArray[i] = byteString.charCodeAt(i);
			}

			var mimeString = dataURIParts[0].split(':')[1].split(';')[0];
			var blob = null;

			if (hasBlobConstructor) {
				blob = new Blob([hasArrayBufferViewSupport ? intArray : arrayBuffer], {
					type: mimeString
				});
			} else {
				var bb = new BlobBuilder();
				bb.append(arrayBuffer);
				blob = bb.getBlob(mimeString);
			}

			return blob;

		},

		_loadImage: function (image, file, callback) {
			if (typeof URL === 'undefined') {
				var reader = new FileReader();
				reader.onload = function (evt) {
					image.src = evt.target.result;
					if (callback) {
						callback();
					}
				};
				reader.readAsDataURL(file);
			} else {
				image.src = URL.createObjectURL(file);
				if (callback) {
					callback();
				}
			}
		},

		isSupported: function () {
			return typeof HTMLCanvasElement !== 'undefined' && hasBlobSupport && hasReaderSupport;
		}

	};
});
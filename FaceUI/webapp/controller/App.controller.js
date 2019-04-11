sap.ui.define([
	"./BaseController",
	"sap/ui/model/base/ManagedObjectModel",
	"../model/FaceState",
	"sap/m/MessageBox",
	"../util/ImageHandler",
	"../service/FaceService"
], function (Controller, ManagedObjectModel, FaceState, MessageBox, ImageHandler, FaceService) {
	"use strict";

	return Controller.extend("be.wl.ml.FaceUI.controller.App", {
		onInit: function () {
			this._oFaceState = new FaceState();
			this.getView().setModel(this._oFaceState.getModel());
		},
		addPhoto: function (oEvent) {
			var me = this;
			var file = oEvent.getParameter("files")[0];
			//get -> /cmis/0d1793f590788bc65bc9b3c5/root/Wouter.jpg
			return ImageHandler.resize(file).then(function (image) {
				return me._oFaceState.createNewFace({
					content: image.blob,
					uri: image.uri,
					name: file.name
				});
			}).then(function () {
				return this.openFragment("be.wl.ml.FaceUI.view.dialog.Person", this._oFaceState.getModel(), true, false, {
					editable: true
				});
			}.bind(this));
		},
		checkPhoto: function (oEvent) {
			var file = oEvent.getParameter("files")[0];
			return ImageHandler.resize(file).then(function (image) {
				return this._oFaceState.compareFace(image);
				// 	{
				// 	content: image.blob,
				// 	name: file.name
				// });
			}.bind(this)).then(function () {
				return this.openFragment("be.wl.ml.FaceUI.view.dialog.Person", this._oFaceState.getModel(), true, false, {
					editable: false
				});
			}.bind(this)).catch(function (error) {
				MessageBox.show("Error while processing your image. Are you sure the image has a face on it?", {
					icon: sap.m.MessageBox.Icon.ERROR,
					title: "Error",
					actions: [sap.m.MessageBox.Action.CLOSE],
					details: error
				});
			});
		},
		onDeleteFace: function (oEvent) {
			this._oFaceState.deleteFace(oEvent.getSource().getBindingContext().getObject());
		}
	});
});
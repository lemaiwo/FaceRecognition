sap.ui.define([
	"sap/ui/base/ManagedObject",
	"../service/FaceService",
	"sap/ui/model/base/ManagedObjectModel",
	"sap/ui/core/BusyIndicator",
	"./Face"
], function (ManagedObect, FaceService, ManagedObjectModel, BusyIndicator, Face) {
	"use strict";
	return ManagedObect.extend('be.wl.ml.FaceUI.model.FaceState', {
		metadata: {
			properties: {
				newFace: 'be.wl.ml.FaceUI.model.Face',
				foundFace: 'be.wl.ml.FaceUI.model.Face'
			},
			aggregations: {
				faces: {
					type: 'be.wl.ml.FaceUI.model.Face',
					multiple: true
				}
			},
			events: {}
		},
		init: function () {
			this.getAllFaces();
		},
		getModel: function () {
			if (!this.model) {
				this.model = new ManagedObjectModel(this);
				//this.model.setData(this);
			}
			return this.model;
		},
		getAllFaces:function(){
			return FaceService.getFaces().then(function(response){
				response.data.results.forEach(function(oFace){
					this.addFace(new Face({
						faceid:oFace.ID,
						firstname:oFace.Firstname,
						lastname:oFace.Lastname,
						vector:JSON.parse(oFace.Vectors),
						image:oFace.Image
					}));
				}.bind(this));
				return this.getFaces();
			}.bind(this));
		},
		createNewFace: function (file) {
			return this.getFaceFeatures(file).then(function (response) {
				var oFace = new Face({
					image: URL.createObjectURL(file.content),
					imageString: file.uri,
					vector: JSON.parse(response).predictions[0].faces[0].face_feature
				});
				this.setNewFace(oFace);
				// this.addFace(oFace);
				return oFace;
			}.bind(this));
		},
		createFace:function(){
			return FaceService.createFace(this.getNewFace()).then(function(response){
				this.addFace(this.getNewFace());
			}.bind(this));
		},
		compareFace: function (file) {
			return this.getFaceFeatures(file).then(function (response) {
				return new Face({
					faceid:0,
					firstname: "test",
					lastname: "test",
					image: URL.createObjectURL(file.content),
					vector: JSON.parse(response).predictions[0].faces[0].face_feature
				});
			}).then(function (oFace) {
				return FaceService.compareFaces(this.tokenInfo.access_token, this.getAllFacesAsVectors(oFace));
			}.bind(this)).then(function (result) {
				result = JSON.parse(result);
				return this.getFaces().filter(function(oFace){
					return oFace.getFaceid() === result.predictions[0].similarVectors[0].id;
				})[0];
				//return result.predictions[0].similarVectors[0].id;
			}.bind(this)).catch(function (error) {
				return false;
			}).then(function(oFoundFace){
				this.setFoundFace(oFoundFace);
				return oFoundFace;
			}.bind(this));
		},
		getFaceFeatures: function (file, bAdd) {
			var me = this;
			BusyIndicator.show(0);
			return FaceService.getBearerToken().then(function (token) {
				me.tokenInfo = JSON.parse(token);
				var form = new FormData();
				form.append("files", file.content, file.name);
				if (form.fd) {
					form = form.fd;
				}
				return FaceService.getFaceFeatures(me.tokenInfo.access_token, form);
			}).catch(function (error) {
				jQuery.sap.log.error(error);
				return error.responseText ? error.responseText : error;
			}).then(function (response) {
				BusyIndicator.hide();
				return response;
			}.bind(this));
		},
		getAllFacesAsVectors: function (oFace) {
			//{"0": [{"id": "v0", "vector": [0.22, …, 0.93]}, {"id": "v1", "vector": [0.39, …, 0.69]}]}
			var aSearchFace = [];
			aSearchFace.push(oFace.getVectorObject());
			return {
				"0": aSearchFace,
				"1": this.getFaces().filter(function(oFace){
					return oFace.hasVectors();
				}).map(function (oFace) {
					return oFace.getVectorObject();
				})
			};
		}
	});
});
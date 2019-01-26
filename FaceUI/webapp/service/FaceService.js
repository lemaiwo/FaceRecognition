sap.ui.define([
	"./Service"
], function (Service) {
	"use strict";

	var FaceService = Service.extend("be.wl.ml.FaceUI.service.FaceService", {
		constructor: function () {},
		setAuthUrl: function (authURL) {
			this.authURL = authURL;
		},
		setClientID: function (clientid) {
			this.clientid = clientid;
		},
		setClientSecret: function (clientsecret) {
			this.clientsecret = clientsecret;
		},
		getMaxFaceId:function(){
			return this.odata("/Face").get({urlParameters:{
					$select:"ID",
					$top: 1,
					$orderby:"ID desc"
			}});
		},
		getFaces:function(){
			return this.odata("/Face").get();
		},
		createFace:function(oFace){
			return this.odata("/Face").post(oFace.getFlatFace());
			// return this.getMaxFaceId().then(function(response){
			// 	var oFaceObj = oFace.getFlatFace();
			// 	oFaceObj.ID = ++response.data.results[0].ID;
			// 	oFace.setFaceid(oFaceObj.ID);
			// 	return this.odata("/Face").post(oFaceObj);
			// }.bind(this));
			
		},
		deleteFace:function(oFace){
			var sObjectPath = this.model.createKey("/Face", {
				ID: oFace.getFaceid()
			});

			return this.odata(sObjectPath).delete();
		},
		getBearerToken: function () {
			// var auth = btoa(this.clientid + ":" + this.clientsecret);
			var headers = {
				// "authorization": "Basic " + auth,
				"accept": "application/json"
			};
			return this.http("/oauth/token?grant_type=client_credentials").get(headers);
		},
		getFaceFeatures: function (token, body) {
			var headers = {
				"authorization": "Bearer " + token,
				"accept": "application/json"
			};
			return this.http("/api/v2alpha1/image/face-feature-extraction/").post(headers, body).catch(function (error) {
				return error;
			});
		},
		compareFaces: function (token, oVectors) {
			var headers = {
				"authorization": "Bearer " + token//,
				// "Content-Type": "multipart/form-data",
					// "accept": "application/json"
			};
			var body = {
				"texts": JSON.stringify(oVectors),
				"option": JSON.stringify({
					"numSimilarVectors": 2
				})
			};
			var formData = new FormData();

			formData.append("texts", JSON.stringify(oVectors));
			formData.append("options", JSON.stringify({
				"numSimilarVectors": 1
			}));
			if (formData.fd) {
				formData = formData.fd;
			}
			return this.http("/api/v2/similarity-scoring/").post(headers, formData).catch(function (error) {
				return error;
			});
		}
	});
	return new FaceService();
});
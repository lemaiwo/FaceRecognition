sap.ui.define([
	"sap/ui/base/ManagedObject"
], function (ManagedObect) {
	"use strict";
	return ManagedObect.extend('be.wl.ml.FaceUI.model.Face', {
		metadata: {
			properties: {
				faceid:'int',
				firstname: 'string',
				lastname: 'string',
				image: 'string',
				imageString:'string',
				vector: 'object',
				imageblob: 'object',
				imageuri:"string"
			},
			aggregations: {
			},
			events: {
			}
		},
		init: function () {
			
		},
		getFlatFace:function(){
			return {
				ID:this.getFaceid(),
				Firstname:this.getFirstname(),
				Lastname:this.getLastname(),
				Vectors:JSON.stringify(this.getVector())
			};
		},
		generateImageuri:function(){
			this.setImageuri("/cmis/0d1793f590788bc65bc9b3c5/root/"+this.getImagename());
		},
		getImagename:function(){
			return this.getFirstname()+"-"+this.getLastname()+"-"+this.getFaceid() +".jpg";// this.getImageblob().name.substr(this.getImageblob().name.lastIndexOf("."));
		},
		hasVectors:function(){
			return this.getVector() && this.getVector().length > 0;
		},
		getVectorObject:function(){
			return {
				id:this.getFaceid(),
				vector:this.getVector()
			};
		}
	});
});
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
				imageuri:"string",
				originName:"string"
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
				Vectors:JSON.stringify(this.getVector()),
				Image:this.getImageuri()
			};
		},
		generateImageuri:function(){
			this.setImageuri(this.getGeneratedImageuri());
		},
		getGeneratedImageuri:function(){
			return "cmisproxysap/cmis/json/0d1793f590788bc65bc9b3c5/root/"+this.getImagename(); 
		},
		getImagename:function(){
			if(this.getOriginName()){
				return this.getFirstname()+"-"+this.getLastname()+"-"+this.getFaceid() +this.getOriginName().substr(this.getOriginName().lastIndexOf("."));
			}
			return false;
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
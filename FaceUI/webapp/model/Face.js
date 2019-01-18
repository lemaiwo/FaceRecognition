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
				vector: 'object'
			},
			aggregations: {
			},
			events: {
			}
		},
		getFlatFace:function(){
			return {
				Firstname:this.getFirstname(),
				Lastname:this.getLastname(),
				Vectors:JSON.stringify(this.getVector()),
				Image:this.getImageString()
			};
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
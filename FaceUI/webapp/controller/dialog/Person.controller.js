sap.ui.define(["../BaseController",
	"sap/m/MessageBox"
], function (BaseController, MessageBox, Fragment, ValidateDelegation, JSONModel) {
	"use strict";
	return BaseController.extend("be.wl.ml.FaceUI.controller.dialog.Person", {
		onBeforeShow: function (parent, fragment, callback, data) {
			this.parent = parent;
			this.fragment = fragment;
			this.callback = callback;
			if(data.editable){
				this.parent._oFaceState.setFoundFace(null);
			}else{
				this.parent._oFaceState.setNewFace(null);
			}
		},
		onAdd: function (oEvent) {
			this.parent._oFaceState.createFace();
			this.fragment.close();
		},
		onClose: function () {
			this.fragment.close();
		}

	});
});
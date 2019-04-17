sap.ui.define([
	"sap/ui/core/UIComponent",
	"sap/ui/Device",
	"./service/FaceService",
	"be/wl/ml/FaceUI/model/models"
], function (UIComponent, Device, FaceService,models) {
	"use strict";

	return UIComponent.extend("be.wl.ml.FaceUI.Component", {

		metadata: {
			manifest: "json"
		},

		/**
		 * The component is initialized by UI5 automatically during the startup of the app and calls the init method once.
		 * @public
		 * @override
		 */
		init: function () {
			// call the base component's init function
			FaceService.setModel(this.getModel());
			UIComponent.prototype.init.apply(this, arguments);
			// enable routing
			this.getRouter().initialize();

			// set the device model
			this.setModel(models.createDeviceModel(), "device");
		}
	});
});
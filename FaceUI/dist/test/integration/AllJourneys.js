/* global QUnit*/

sap.ui.define([
	"sap/ui/test/Opa5",
	"be/wl/ml/FaceUI/test/integration/pages/Common",
	"sap/ui/test/opaQunit",
	"be/wl/ml/FaceUI/test/integration/pages/App",
	"be/wl/ml/FaceUI/test/integration/navigationJourney"
], function (Opa5, Common) {
	"use strict";
	Opa5.extendConfig({
		arrangements: new Common(),
		viewNamespace: "be.wl.ml.FaceUI.view.",
		autoWait: true
	});
});
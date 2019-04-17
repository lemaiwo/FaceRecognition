function initModel() {
	var sUrl = "/FaceRecognitionCF/";
	var oModel = new sap.ui.model.odata.ODataModel(sUrl, true);
	sap.ui.getCore().setModel(oModel);
}
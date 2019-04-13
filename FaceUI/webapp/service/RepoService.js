sap.ui.define([
	"./Service"
], function (Service) {
	"use strict";

	var RepoService = Service.extend("be.wl.ml.FaceUI.service.RepoService", {
		constructor: function () {},
		uploadFile: function (file, name) {
			var form = new FormData();
			form.append("datafile", file, name);
			form.append("cmisaction", "createDocument");
			form.append("propertyId[0]", "cmis:objectTypeId");
			form.append("propertyValue[0]", "cmis:document");
			form.append("propertyId[1]", "cmis:name");
			form.append("propertyValue[1]", name);
			if (form.fd) {
				form = form.fd;
			}
			return this.http("cmisproxysap/cmis/json/0d1793f590788bc65bc9b3c5/root").post(false,form);
		},
		deleteFile: function ( name) {
			var form = new FormData();
			form.append("cmisaction","delete");// "deleteObject");
			// form.append("propertyId[0]", "cmis:objectTypeId");
			// form.append("propertyValue[0]", "cmis:document");
			// form.append("propertyId[1]", "cmis:name");
			// form.append("propertyValue[1]", name);
			if (form.fd) {
				form = form.fd;
			}
			return this.http("cmisproxysap/cmis/json/0d1793f590788bc65bc9b3c5/root/"+name).post(false,form);
		}
	});
	return new RepoService();
});
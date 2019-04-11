package be.wl.ml.face.handlers.facerecognitionservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpEntityUtil;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.services.scp.machinelearning.CloudFoundryLeonardoMlServiceType;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlFoundation;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlService;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlServiceType;

import org.apache.axiom.blob.Blob;
import org.apache.axiom.mime.MultipartWriter;
import org.apache.axiom.mime.MultipartWriterFactory;
import org.apache.axis2.builder.MultipartFormDataBuilder;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;

// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
// import java.util.function.Function;

import com.google.gson.Gson;
import com.sap.cloud.sdk.service.prov.api.DataSourceHandler;
import com.sap.cloud.sdk.service.prov.api.EntityData;
import com.sap.cloud.sdk.service.prov.api.ExtensionHelper;
import com.sap.cloud.sdk.service.prov.api.annotations.Action;
import com.sap.cloud.sdk.service.prov.api.annotations.Function;
import com.sap.cloud.sdk.service.prov.api.exception.DatasourceException;
import com.sap.cloud.sdk.service.prov.api.request.OperationRequest;
import com.sap.cloud.sdk.service.prov.api.response.OperationResponse;
import com.sap.cloud.sdk.services.scp.machinelearning.CloudFoundryLeonardoMlServiceType;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlFoundation;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlService;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlServiceType;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpEntityUtil;

//import com.sap.cloud.sdk.service.prov.api.request.QueryRequest;
//import com.sap.cloud.sdk.service.prov.api.response.QueryResponse;
//import com.sap.cloud.sdk.service.prov.api.operations.Read;
//import com.sap.cloud.sdk.service.prov.api.request.ReadRequest;
//import com.sap.cloud.sdk.service.prov.api.response.ReadResponse;
//import com.sap.cloud.sdk.service.prov.api.operations.Update;
//import com.sap.cloud.sdk.service.prov.api.request.UpdateRequest;
//import com.sap.cloud.sdk.service.prov.api.response.UpdateResponse;
//import com.sap.cloud.sdk.service.prov.api.operations.Create;
//import com.sap.cloud.sdk.service.prov.api.request.CreateRequest;
//import com.sap.cloud.sdk.service.prov.api.response.CreateResponse;
//import com.sap.cloud.sdk.service.prov.api.operations.Delete;
//import com.sap.cloud.sdk.service.prov.api.request.DeleteRequest;
//import com.sap.cloud.sdk.service.prov.api.response.DeleteResponse;
import com.sap.cloud.sdk.service.prov.api.response.ErrorResponse;
//import java.util.List;
//import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import be.wl.ml.facerecognition.jpa.be.wl.ml.facerecognition.Faces;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/***
 * Handler class for entity "Face" of service "FaceRecognitionService". This
 * handler registers custom handlers for the entity OData operations. For more
 * information, see:
 * https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/6fe3070250ea45b88c35cda209e8324b.html
 */
public class FaceCustomHandler {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final FaceHandlerHelper faceHelper = new FaceHandlerHelper();

	// @Query(entity = "Face", serviceName = "FaceRecognitionService")
	// public QueryResponse queryFace(QueryRequest req) {
	// //TODO: add your custom logic...
	//
	// //List<Object> resultItems = new ArrayList<Object>();
	// //return QueryResponse.setSuccess().setData(resultItems).response(); //use
	// this API to return items.
	// ErrorResponse errorResponse = ErrorResponse.getBuilder()
	// .setMessage("Unimplemented Query Operation")
	// .setStatusCode(500)
	// .response();
	// return QueryResponse.setError(errorResponse);
	// }

	// @Read(entity = "Face", serviceName = "FaceRecognitionService")
	// public ReadResponse readFace(ReadRequest req) {
	// //TODO: add your custom logic...
	//
	// //Object data = new Object();
	// //return ReadResponse.setSuccess().setData(data).response(); //use this API
	// to return an item.
	// ErrorResponse errorResponse = ErrorResponse.getBuilder()
	// .setMessage("Unimplemented Read Operation")
	// .setStatusCode(500)
	// .response();
	// return ReadResponse.setError(errorResponse);
	// }

	// @Update(entity = "Face", serviceName = "FaceRecognitionService")
	// public UpdateResponse updateFace(UpdateRequest req) {
	// //TODO: add your custom logic...
	//
	// //return UpdateResponse.setSuccess().response(); //use this API if the item
	// is successfully modified.
	// ErrorResponse errorResponse = ErrorResponse.getBuilder()
	// .setMessage("Unimplemented Update Operation")
	// .setStatusCode(500)
	// .response();
	// return UpdateResponse.setError(errorResponse);
	// }

	// @Create(entity = "Face", serviceName = "FaceRecognitionService")
	// public CreateResponse createFace(CreateRequest req) {
	// //TODO: add your custom logic...
	//
	// //return CreateResponse.setSuccess().response(); //use this API if the item
	// is successfully created.
	// ErrorResponse errorResponse = ErrorResponse.getBuilder()
	// .setMessage("Unimplemented Create Operation")
	// .setStatusCode(500)
	// .response();
	// return CreateResponse.setError(errorResponse);
	// }

	// @Delete(entity = "Face", serviceName = "FaceRecognitionService")
	// public DeleteResponse deleteFace(DeleteRequest req) {
	// //TODO: add your custom logic...
	//
	// //return DeleteResponse.setSuccess().response(); //use this API if the item
	// is successfully deleted.
	// ErrorResponse errorResponse = ErrorResponse.getBuilder()
	// .setMessage("Unimplemented Delete Operation")
	// .setStatusCode(500)
	// .response();
	// return DeleteResponse.setError(errorResponse);
	// }

	@Action(Name = "findFaceByImage", serviceName = "FaceRecognitionService")
	public OperationResponse findFaceByImage(OperationRequest functionRequest, ExtensionHelper extensionHelper)
			throws NamingException {
		Map<String, Object> parameters = functionRequest.getParameters();
		DataSourceHandler handler = extensionHelper.getHandler();
		String image = (String) parameters.get("Image");
		
		// FaceHandlerHelper faceHelper = new FaceHandlerHelper();
		try {
			String facefeatures = faceHelper.getFaceVector(image);
			String matchValue = faceHelper.findFaceVector(facefeatures.substring(1, facefeatures.length() - 1));

			Integer id = Integer.parseInt(matchValue);
			EntityManager em = (EntityManager) (new InitialContext()).lookup("java:comp/env/jpa/default/pc");
			Faces f = em.find(Faces.class, id);

			EntityData entityd = EntityData.getBuilder().addElement("ID", f.getID())
					.addElement("Firstname", f.getFirstname()).addElement("Lastname", f.getLastname())
					.addElement("Vectors", f.getVectors()).addElement("Image", f.getImage()).buildEntityData("Face");
			OperationResponse response = OperationResponse.setSuccess().setEntityData(Arrays.asList(entityd))
					.response();

			return response;
		} catch (Exception e) {
			logger.error("Failure: " + e.getMessage(), e);
			ErrorResponse errorResponse = ErrorResponse.getBuilder().setMessage("Error: " + e.getMessage())
					.setStatusCode(500).response();
			return OperationResponse.setError(errorResponse);
		}
	}

	@Action(Name = "findFaceByVector", serviceName = "FaceRecognitionService")
	public OperationResponse findFaceByVector(OperationRequest functionRequest, ExtensionHelper extensionHelper)
			throws NamingException {
		Map<String, Object> parameters = functionRequest.getParameters();
		DataSourceHandler handler = extensionHelper.getHandler();
		// FaceHandlerHelper faceHelper = new FaceHandlerHelper();
		try {
			String aVector = String.valueOf(parameters.get("NewVector"));
			String matchValue = faceHelper.findFaceVector(aVector);

			Integer id = Integer.parseInt(matchValue);
			EntityManager em = (EntityManager) (new InitialContext()).lookup("java:comp/env/jpa/default/pc");
			Faces f = em.find(Faces.class, id);

			EntityData entityd = EntityData.getBuilder().addElement("ID", f.getID())
					.addElement("Firstname", f.getFirstname()).addElement("Lastname", f.getLastname())
					.addElement("Vectors", f.getVectors()).addElement("Image", f.getImage()).buildEntityData("Face");
			OperationResponse response = OperationResponse.setSuccess().setEntityData(Arrays.asList(entityd))
					.response();

			return response;
		} catch (Exception e) {
			logger.error("Failure: " + e.getMessage(), e);
			ErrorResponse errorResponse = ErrorResponse.getBuilder().setMessage("Error: " + e.getMessage())
					.setStatusCode(500).response();
			return OperationResponse.setError(errorResponse);
		}
	}
}
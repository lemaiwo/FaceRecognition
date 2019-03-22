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
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;

// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
// import java.util.function.Function;

import com.google.gson.Gson;
import com.sap.cloud.sdk.service.prov.api.DataSourceHandler;
import com.sap.cloud.sdk.service.prov.api.EntityData;
import com.sap.cloud.sdk.service.prov.api.ExtensionHelper;
import com.sap.cloud.sdk.service.prov.api.annotations.Function;
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
	@Function(Name = "getAllVectors", serviceName = "FaceRecognitionService")
	public OperationResponse getAllVectors(OperationRequest functionRequest, ExtensionHelper extensionHelper)
			throws NamingException {
		Map<String, Object> parameters = functionRequest.getParameters();
		DataSourceHandler handler = extensionHelper.getHandler();

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("ID", "1");// String.valueOf(parameters.get("NewVector")));
		String translationValue = "";
		try {
			LeonardoMlService mlService = LeonardoMlFoundation.create(CloudFoundryLeonardoMlServiceType.TRIAL_BETA,
					LeonardoMlServiceType.TRANSLATION);
					LeonardoMlServiceType.

			// construct { units: [ input ],
			// sourceLanguage: "en", targetLanguages: [ "de" ] }
			List<Object> units = new ArrayList<>();
			units.add(Collections.singletonMap("value", String.valueOf(parameters.get("NewVector"))));

			Map<String, Object> entity = new HashMap<>();
			entity.put("units", units);
			entity.put("sourceLanguage", "en");
			entity.put("targetLanguages", Collections.singletonList("de"));

			HttpPost postRequest = new HttpPost();
			postRequest.setEntity(new StringEntity(new Gson().toJson(entity), ContentType.APPLICATION_JSON));

			postRequest.setHeader("Accept", ContentType.APPLICATION_JSON.getMimeType());

			translationValue = mlService.invoke(postRequest, new java.util.function.Function<HttpResponse, String>() {
				@Override
				public String apply(HttpResponse httpResponse) {
					try {
						// retrieve entity content (requested json with Accept header, so should be
						// text)
						final String responseBody = HttpEntityUtil.getResponseBody(httpResponse);
						logger.debug(responseBody);

						final Map<String, Object> responseObject = new Gson().fromJson(responseBody, Map.class);
						final List<Object> respUnits = (List<Object>) responseObject.get("units");
						final Map<String, Object> respUnit = (Map<String, Object>) respUnits.get(0);
						final List<Map<String, Object>> translations = (List<Map<String, Object>>) respUnit
								.get("translations");
						final Map<String, Object> translation = translations.get(0);
						return (String) translation.get("value");
					} catch (Exception e) {
						throw new RuntimeException("Failed to retrieve response: " + e.getMessage(), e);
					}
				}
			});
		} catch (Exception e) {
			logger.error("Failure: " + e.getMessage(), e);

			// response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			// response.getWriter().println("Error: " + e.getMessage());
			ErrorResponse errorResponse = ErrorResponse.getBuilder().setMessage("Error: " + e.getMessage())
					.setStatusCode(500).response();
			return OperationResponse.setError(errorResponse);
		}
		EntityManager em = (EntityManager) (new InitialContext()).lookup("java:comp/env/jpa/default/pc");
		List<Faces> faces = em.createQuery("SELECT c FROM Faces c ").getResultList();
		Integer id = 1;
		Faces f = em.find(Faces.class, id);
		// List<Faces> faces =
		// em.createNamedQuery("Faces.findAllUnordered").getResultList();
		EntityData entityData = null;
		// try {
		// List<String> lSelect = new ArrayList<String>();
		// lSelect.add("ID");
		// lSelect.add("Firstname");
		// lSelect.add("Lastname");
		// lSelect.add("Vectors");
		// entityData = handler.executeRead("Faces", keys, lSelect);

		// functionRequest.getEntityMetadata().getFlattenedElementNames());
		// Boolean status = entityData.getElementValue("Vectors").toString().isEmpty();
		// OperationResponse response =
		// OperationResponse.setSuccess().setPrimitiveData(Arrays.asList(status))
		// .response();
		List<EntityData> result = new ArrayList<EntityData>();
		for (Faces f : faces) {
		EntityData entityd = EntityData.getBuilder().addElement("ID", f.getID())
				.addElement("Firstname", f.getFirstname()).addElement("Lastname", f.getLastname())
				.addElement("Vectors", translationValue// f.getVectors()
				).buildEntityData("Face");
		result.add(entity);
		}
		OperationResponse response = OperationResponse.setSuccess().setEntityData(result).response();//Arrays.asList(entityd)).response();
		return response;
		// } catch (DatasourceException e) {
		// logger.error("Error accessing the data", e);
		// return null;
		// }
	}
}
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
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;

// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
	@Function(Name = "getVectorById", serviceName = "FaceRecognitionService")
	public OperationResponse getVectorById(OperationRequest functionRequest, ExtensionHelper extensionHelper)
			throws NamingException {
		Map<String, Object> parameters = functionRequest.getParameters();
		DataSourceHandler handler = extensionHelper.getHandler();

		List<String> properties = new ArrayList();
		properties.add("Approved");

		EntityData entityData = null;
		try {
			// List<String> lSelect = new ArrayList<String>();
			// lSelect.add("ID");
			// lSelect.add("Firstname");
			// lSelect.add("Lastname");
			// lSelect.add("Vectors");
			// entityData = handler.executeRead("Faces", keys, lSelect);

			EntityManager em = (EntityManager) (new InitialContext()).lookup("java:comp/env/jpa/default/pc");
			Faces f = em.find(Faces.class, (Integer) parameters.get("id"));
			EntityData entityd = EntityData.getBuilder().addElement("ID", f.getID())
					.addElement("Firstname", f.getFirstname()).addElement("Lastname", f.getLastname())
					.addElement("Vectors", f.getVectors()).buildEntityData("Face");
			OperationResponse response = OperationResponse.setSuccess().setPrimitiveData(Arrays.asList(entityd))
					.response();

			return response;
		} catch (Exception e) {
			logger.error("Error accessing the data", e);
			return null;
		}
	}

	@Action(Name = "compareVectors", serviceName = "FaceRecognitionService")
	public OperationResponse getAllVectors(OperationRequest functionRequest, ExtensionHelper extensionHelper)
			throws NamingException {
		Map<String, Object> parameters = functionRequest.getParameters();
		DataSourceHandler handler = extensionHelper.getHandler();
		String image = (String) parameters.get("Image");
		try {
			byte[] decodedByte = Base64.getDecoder().decode(image);
		} catch (Exception ex) {

		}
		String FILEPATH = "";
		File file = new File(FILEPATH);
		// java.sql.Blob b = new SerialBlob(decodedByte);
		try {
			OutputStream os = new FileOutputStream(file);
			os.write(decodedByte);
			os.close();
		} catch (Exception e) {
			logger.error("Failure: " + e.getMessage(), e);
			ErrorResponse errorResponse = ErrorResponse.getBuilder().setMessage("Error: " + e.getMessage())
					.setStatusCode(500).response();
			return OperationResponse.setError(errorResponse);
		}
		HttpPost post = new HttpPost();
		MultipartEntityBuilder builderff = MultipartEntityBuilder.create();
		builderff.addBinaryBody("files", file, ContentType.APPLICATION_OCTET_STREAM, "file.ext");
		// FormBodyPart bodyPart = FormBodyPartBuilder.create().setName("any_name")
		// .addField("Content-Disposition", "form-data; name=\"categoryFile\";
		// filename=\"image.jpg\"")
		// .setBody(new StringBody(b, ContentType.MULTIPART_FORM_DATA)).build();

		// MultipartEntityBuilder builderff =
		// MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
		// .addPart(bodyPart);

		HttpEntity entityf = builderff.build();
		post.setEntity(entityf);
		post.setHeader("Content-Type", "multipart/form-data");

		LeonardoMlService mlFaceService = LeonardoMlFoundation.create(CloudFoundryLeonardoMlServiceType.TRIAL_BETA,
				LeonardoMlServiceType.FACE_FEATURE_EXTRACTION);
		try {
			String facefeatures = mlFaceService.invoke(post, new java.util.function.Function<HttpResponse, String>() {
				@Override
				public String apply(HttpResponse httpResponse) {
					try {
						// retrieve entity content (requested json with Accept header, so should be
						// text)
						final String responseBody = HttpEntityUtil.getResponseBody(httpResponse);
						logger.debug(responseBody);

						final Map<String, Object> responseObject = new Gson().fromJson(responseBody, Map.class);
						final List<Object> respUnits = (List<Object>) responseObject.get("predictions");// .get("units");
						final Map<String, Object> respUnit = (Map<String, Object>) respUnits.get(0);
						final List<Map<String, Object>> translations = (List<Map<String, Object>>) respUnit
								.get("similarVectors");// .get("translations");
						final Map<String, Object> translation = translations.get(0);
						final Double result = (Double) translation.get("id");
						return (String) Integer.toString(result.intValue());// get("value");
					} catch (Exception e) {
						throw new RuntimeException("Failed to retrieve response: " + e.getMessage(), e);
					}
				}
			});
		} catch (Exception e) {
			logger.error("Failure: " + e.getMessage(), e);
			ErrorResponse errorResponse = ErrorResponse.getBuilder().setMessage("Error: " + e.getMessage())
					.setStatusCode(500).response();
			return OperationResponse.setError(errorResponse);
		}
		// Map<String, Object> keys = new HashMap<String, Object>();
		// convert to float
		String[] aVector = String.valueOf(parameters.get("NewVector")).split(",");

		Float[] aVectorfloats = Arrays.stream(aVector).map(Float::valueOf).toArray(Float[]::new);
		Map<String, Object> first = new HashMap<>();
		first.put("id", 0);
		first.put("vector", aVectorfloats);
		List<Object> firstVectors = new ArrayList<>();
		firstVectors.add(first);
		// keys.put("ID", "1");// String.valueOf(parameters.get("NewVector")));
		String matchValue = "";
		EntityManager em = (EntityManager) (new InitialContext()).lookup("java:comp/env/jpa/default/pc");
		// try {

		LeonardoMlService mlService = LeonardoMlFoundation.create(CloudFoundryLeonardoMlServiceType.TRIAL_BETA,
				LeonardoMlServiceType.SIMILARITY_SCORING);
		// LeonardoMlServiceType.

		// List<Faces>
		List<Faces> faces = em.createQuery("SELECT c FROM Faces c ").getResultList();

		List<Object> sercondVectors = new ArrayList<>();
		Map<String, Object> face;
		for (Faces f : faces) {
			face = new HashMap<>();
			face.put("id", f.getID());
			aVector = String.valueOf(f.getVectors().substring(1, f.getVectors().length() - 1)).split(",");
			aVectorfloats = Arrays.stream(aVector).map(Float::valueOf).toArray(Float[]::new);
			face.put("vector", aVectorfloats);// text.substring(0,
												// text.length() - 1);
			sercondVectors.add(face);
		}

		Map<String, Object> entity = new HashMap<>();
		entity.put("0", Collections.singletonList(first));// firstVectors);
		entity.put("1", sercondVectors);
		String data1 = new Gson().toJson(entity);
		logger.error(data1);
		StringEntity json1 = new StringEntity(data1, ContentType.APPLICATION_JSON);

		entity = new HashMap<>();
		entity.put("numSimilarVectors", 1);
		String data2 = new Gson().toJson(entity);
		// logger.error(data2);
		StringEntity json2 = new StringEntity(data2, ContentType.APPLICATION_JSON);

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("texts", data1, ContentType.APPLICATION_JSON);
		builder.addTextBody("options", data2, ContentType.APPLICATION_JSON);
		HttpEntity multipart = builder.build();

		HttpPost postRequest = new HttpPost();
		postRequest.setEntity(multipart);

		// postRequest.setHeader("Accept", ContentType.APPLICATION_JSON.getMimeType());
		postRequest.setHeader("Accept", ContentType.MULTIPART_FORM_DATA.getMimeType());
		try {
			matchValue = mlService.invoke(postRequest, new java.util.function.Function<HttpResponse, String>() {
				@Override
				public String apply(HttpResponse httpResponse) {
					try {
						// retrieve entity content (requested json with Accept header, so should be
						// text)
						final String responseBody = HttpEntityUtil.getResponseBody(httpResponse);
						logger.debug(responseBody);

						final Map<String, Object> responseObject = new Gson().fromJson(responseBody, Map.class);
						final List<Object> respUnits = (List<Object>) responseObject.get("predictions");// .get("units");
						final Map<String, Object> respUnit = (Map<String, Object>) respUnits.get(0);
						final List<Map<String, Object>> translations = (List<Map<String, Object>>) respUnit
								.get("similarVectors");// .get("translations");
						final Map<String, Object> translation = translations.get(0);
						final Double result = (Double) translation.get("id");
						return (String) Integer.toString(result.intValue());// get("value");
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
		Integer id = Integer.parseInt(matchValue);
		Faces f = em.find(Faces.class, id);
		// List<Faces> faces =
		// em.createNamedQuery("Faces.findAllUnordered").getResultList();
		// EntityData entityData = null;
		try {

			// functionRequest.getEntityMetadata().getFlattenedElementNames());
			// Boolean status = entityData.getElementValue("Vectors").toString().isEmpty();
			// OperationResponse response =
			// OperationResponse.setSuccess().setPrimitiveData(Arrays.asList(status))
			// .response();
			// List<EntityData> result = new ArrayList<EntityData>();
			// for (Faces f : faces) {

			EntityData entityd = EntityData.getBuilder().addElement("ID", f.getID())
					.addElement("Firstname", f.getFirstname()).addElement("Lastname", f.getLastname())
					.addElement("Vectors", f.getVectors()).buildEntityData("Face");
			// result.add(entityd);
			// }
			// return OperationResponse.setSuccess().setData(faces).response();
			OperationResponse response = OperationResponse.setSuccess().setEntityData(Arrays.asList(entityd))
					.response();

			return response;
		} catch (Exception e) {
			logger.error("Error accessing the data", e);
			return null;
		}
	}
}
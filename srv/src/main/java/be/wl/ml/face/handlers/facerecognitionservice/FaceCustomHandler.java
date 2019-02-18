package be.wl.ml.face.handlers.facerecognitionservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryBuilder;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryResult;
import com.sap.cloud.sdk.service.prov.api.DataSourceHandler;
import com.sap.cloud.sdk.service.prov.api.EntityData;
import com.sap.cloud.sdk.service.prov.api.ExtensionHelper;
import com.sap.cloud.sdk.service.prov.api.annotations.Function;
import com.sap.cloud.sdk.service.prov.api.exception.DatasourceException;
import com.sap.cloud.sdk.service.prov.api.request.OperationRequest;
import com.sap.cloud.sdk.service.prov.api.response.OperationResponse;
import com.sap.cloud.sdk.service.prov.rt.cds.domain.QueryHelper;

import org.apache.olingo.odata2.api.exception.ODataException;
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
//import com.sap.cloud.sdk.service.prov.api.response.ErrorResponse;
//import java.util.List;
//import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import be.wl.ml.facerecognition.jpa.be.wl.ml.facerecognition.Faces;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;

/***
 * Handler class for entity "Face" of service "FaceRecognitionService". This
 * handler registers custom handlers for the entity OData operations. For more
 * information, see:
 * https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/6fe3070250ea45b88c35cda209e8324b.html
 */
public class FaceCustomHandler {

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
		keys.put("ID", "1");//String.valueOf(parameters.get("NewVector")));

		EntityManager em = (EntityManager) (new InitialContext()).lookup("java:comp/env/jpa/default/pc");
		//List<Faces> faces = em.createQuery("SELECT c FROM Faces c ").getResultList();
		// Integer id = 1;
		// Faces f = em.find(Faces.class, id);
		List<Faces> faces = em.createNamedQuery("Faces.findAllUnordered")
                .getResultList();
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
				EntityData entity = EntityData.getBuilder().addElement("ID", f.getID())
						.addElement("Firstname", f.getFirstname()).addElement("Lastname", f.getLastname())
						.addElement("Vectors", f.getVectors()).buildEntityData("Face");
				result.add(entity);
			}
			OperationResponse response = OperationResponse.setSuccess().setEntityData(result).response();
			return response;
		// } catch (DatasourceException e) {
		// 	logger.error("Error accessing the data", e);
		// 	return null;
		// }
	}
}
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
import com.sap.cloud.sdk.services.scp.machinelearning.CloudFoundryLeonardoMlServiceType;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlFoundation;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlService;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlServiceType;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpEntityUtil;
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

public class FaceHandlerHelper {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public FaceHandlerHelper() {
		// this.logger = logger;
	}

	private byte[] base64ToBytes(String image) {
		String partSeparator = ",";
		if (image.contains(partSeparator)) {
			String encodedImg = image.split(partSeparator)[1];
			return Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));
		}
		return new byte[0];
	}

	private HttpPost getFilePost(String image) {
		String boundary = "-------------" + System.currentTimeMillis();
		MultipartEntityBuilder builderff = MultipartEntityBuilder.create();
		builderff.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builderff.setBoundary(boundary);
		builderff.addBinaryBody("files", this.base64ToBytes(image), ContentType.create("image/jpeg"), "file.jpg");
		HttpPost post = new HttpPost();
		post.setHeader("Content-type", "multipart/form-data; boundary=" + boundary);
		HttpEntity entityf = builderff.build();
		post.setEntity(entityf);
		return post;
	}

	private HttpPost getSimilartyPost(String facefeatures) throws NamingException {
		String[] aVector = facefeatures.split(",");
		Float[] aVectorfloats = Arrays.stream(aVector).map(Float::valueOf).toArray(Float[]::new);
		Map<String, Object> first = new HashMap<>();
		first.put("id", 0);
		first.put("vector", aVectorfloats);
		List<Object> firstVectors = new ArrayList<>();
		firstVectors.add(first);

		Map<String, Object> entity = new HashMap<>();
		entity.put("0", Collections.singletonList(first));// firstVectors);
		entity.put("1", this.getFaces());
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
		return postRequest;
	}

	private List<Object> getFaces() throws NamingException {
		EntityManager em = (EntityManager) (new InitialContext()).lookup("java:comp/env/jpa/default/pc");
		// List<Faces>
		List<Faces> faces = em.createQuery("SELECT c FROM Faces c ").getResultList();

		List<Object> sercondVectors = new ArrayList<>();
		Map<String, Object> face;
		Float[] aVectorfloats;
		String[] aVector;
		for (Faces f : faces) {
			face = new HashMap<>();
			face.put("id", f.getID());
			aVector = String.valueOf(f.getVectors().substring(1, f.getVectors().length() - 1)).split(",");
			aVectorfloats = Arrays.stream(aVector).map(Float::valueOf).toArray(Float[]::new);
			face.put("vector", aVectorfloats);// text.substring(0,
												// text.length() - 1);
			sercondVectors.add(face);
		}
		return sercondVectors;
	}

	public String getFaceVector(String image) throws NamingException {
		String facefeatures = "";
		LeonardoMlService mlFaceService = LeonardoMlFoundation.create(CloudFoundryLeonardoMlServiceType.TRIAL_BETA,
				LeonardoMlServiceType.FACE_FEATURE_EXTRACTION);
		return mlFaceService.invoke(this.getFilePost(image), new java.util.function.Function<HttpResponse, String>() {
			@Override
			public String apply(HttpResponse httpResponse) {
				try {
					final String responseBody = HttpEntityUtil.getResponseBody(httpResponse);
					final Map<String, Object> responseObject = new Gson().fromJson(responseBody, Map.class);
					final List<Object> predictions = (List<Object>) responseObject.get("predictions");// .get("units");
					final Map<String, Object> prediction = (Map<String, Object>) predictions.get(0);
					final List<Map<String, Object>> faces = (List<Map<String, Object>>) prediction.get("faces");// .get("translations");
					final Map<String, Object> face = faces.get(0);
					final ArrayList<Float> feature = (ArrayList<Float>) face.get("face_feature");
					return (String) feature.toString();
				} catch (Exception e) {
					throw new RuntimeException("Failed to retrieve response: " + e.getMessage(), e);
				}
			}
		});
	}

	public String findFaceVector(String facefeatures) throws NamingException {
		String matchValue = "";

		LeonardoMlService mlService = LeonardoMlFoundation.create(CloudFoundryLeonardoMlServiceType.TRIAL_BETA,
				LeonardoMlServiceType.SIMILARITY_SCORING);
		return mlService.invoke(this.getSimilartyPost(facefeatures),
				new java.util.function.Function<HttpResponse, String>() {
					@Override
					public String apply(HttpResponse httpResponse) {
						try {
							final String responseBody = HttpEntityUtil.getResponseBody(httpResponse);
							final Map<String, Object> responseObject = new Gson().fromJson(responseBody, Map.class);
							final List<Object> respUnits = (List<Object>) responseObject.get("predictions");// .get("units");
							final Map<String, Object> respUnit = (Map<String, Object>) respUnits.get(0);
							final List<Map<String, Object>> translations = (List<Map<String, Object>>) respUnit
									.get("similarVectors");// .get("translations");
							final Map<String, Object> translation = translations.get(0);
							final Double result = (Double) translation.get("id");
							return (String) Integer.toString(result.intValue());
						} catch (Exception e) {
							throw new RuntimeException("Failed to retrieve response: " + e.getMessage(), e);
						}
					}
				});
	}
}

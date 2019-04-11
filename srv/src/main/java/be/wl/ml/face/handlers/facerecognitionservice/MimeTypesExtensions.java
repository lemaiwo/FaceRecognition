package be.wl.ml.face.handlers.facerecognitionservice;
import java.util.HashMap;

public class MimeTypesExtensions {

    static HashMap<String, String> TheHashMap = new HashMap<String, String>();

    public MimeTypesExtensions() {
        super();
    }

    static {
        CreateHashMap();
    }
    
    private static void CreateHashMap() {

        // This list is not complete... 
        // I got it from https://www.freeformatter.com/mime-types-list.html
        TheHashMap.put("image/bmp", "bmp");
        TheHashMap.put("image/jpeg", "jpg");
        TheHashMap.put("image/x-citrix-jpeg", "jpg");
        TheHashMap.put("image/png", "png");
        TheHashMap.put("image/x-citrix-png", "png");
        TheHashMap.put("image/x-png", "png");
    }

    public static String getExtensionFromMimeType(String TheMimeType) {

        String TheExtension = "Extension Not Found";
        for(String key: TheHashMap.keySet()){
            if(key.toLowerCase().equals(TheMimeType.toLowerCase())) {
                TheExtension = TheHashMap.get(key);
                return TheExtension;
            }
        }

        throw new RuntimeException(TheExtension);
    }

    public static String getMimeTypeFromExtension(String TheExtension) {

        StringBuilder TheMimeTypes = new StringBuilder();
        for(String key: TheHashMap.keySet()){
            if(TheHashMap.get(key.toLowerCase()).equals(TheExtension.toLowerCase())) {
                TheMimeTypes.append(key.concat("\n"));
            }
        }

        if(TheMimeTypes.length() == 0)
        	throw new RuntimeException(TheExtension);

        return TheMimeTypes.toString();
    }

    public static String getMimeTypesAndExtensions(){

        StringBuilder TheMimeTypesAndExtensions = new StringBuilder();
        for(String key: TheHashMap.keySet()){
            TheMimeTypesAndExtensions.append(TheHashMap.get(key).concat("   ----->   ").concat(key).concat("\n"));
        }

        return TheMimeTypesAndExtensions.toString();
    }
}
using be.wl.ml.facerecognition from '../db/data-model';

service FaceRecognitionService {
  entity Face as projection on facerecognition.Faces;
  
  function getVectorById(id:Integer) returns Face;
  
  action compareVectors(NewVector:String,Image:String) returns Face; //function for get - action for post
  action findFaceByImage(Image:String) returns Face; 
  action findFaceByVector(NewVector:String) returns Face; 
  
}

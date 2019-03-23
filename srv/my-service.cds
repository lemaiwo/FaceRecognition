using be.wl.ml.facerecognition from '../db/data-model';

service FaceRecognitionService {
  entity Face as projection on facerecognition.Faces;
  action compareVectors(NewVector:String);//function for get - action for post
}

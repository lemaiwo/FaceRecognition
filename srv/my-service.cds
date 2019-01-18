using be.wl.ml.facerecognition from '../db/data-model';

service FaceRecognitionService {
  entity Face as projection on facerecognition.Faces;
}

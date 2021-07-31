package br.com.markswell.whoami.image.recognition;

import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class ImageRecognition {

    public void capture() throws FrameGrabber.Exception, URISyntaxException {
        KeyEvent tecla = null;
        OpenCVFrameConverter.ToMat convertMat = new OpenCVFrameConverter.ToMat();
        OpenCVFrameGrabber camera = new OpenCVFrameGrabber(0);
        camera.start();
        URL resource = getClass().getResource("/haarcascade_frontalface_alt.xml");
        File file = new File(resource.toURI());
        CascadeClassifier detectFace = new CascadeClassifier(file.getAbsolutePath());

        CanvasFrame cframe = new CanvasFrame("Capiture image", CanvasFrame.getDefaultGamma() / camera.getGamma());
        Frame frameCapture = null;
        Mat colorImage = new Mat();
        while((frameCapture = camera.grab()) != null) {
            colorImage = convertMat.convert(frameCapture);
            Mat grayImage = new Mat();
            cvtColor(colorImage, grayImage, COLOR_BGRA2GRAY);
            RectVector detectedFaces = new RectVector();
            detectFace.detectMultiScale(grayImage, detectedFaces, 1.1, 1, 0, new Size(150, 150), new Size(500, 500));
            for(int i = 0; i < detectedFaces.size(); i++ ) {
                Rect faceData = detectedFaces.get(0);
                rectangle(colorImage, faceData, new Scalar(255, 0, 0,0));

            }
            if(cframe.isVisible()) {
                cframe.showImage(frameCapture);
            }
        }
        cframe.dispose();
        camera.stop();
    }


}
